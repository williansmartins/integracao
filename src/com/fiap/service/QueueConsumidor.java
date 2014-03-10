package com.fiap.service;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;

import com.fiap.email.EnviaEmail;
import com.fiap.jaxb.Cliente;
import com.fiap.jaxb.LeXML;

public class QueueConsumidor implements Runnable
{
    private static final String NOME_FILA = "queue/FilaFIAP";

    Context jndiContext;
    QueueConnectionFactory queueConnectionFactory;
    QueueConnection queueConnection;
    QueueSession queueSession;
    Queue queue;
    QueueReceiver queueReceiver;
    TextMessage message;
    QueueSender replySender;

    private static InitialContext getInitial( ) throws Exception
    {
	Properties props = new Properties();
	props.setProperty( "java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory" );
	props.setProperty( "java.naming.provider.url", "localhost:1099" );
	props.setProperty( "java.naming.factory.url.pkgs", "org.jboss.naming" );
	InitialContext context = new InitialContext( props );
	return context;
    }

    public void iniciaProcesso( ) throws JAXBException
    {
	try
	{
	    System.out.println( "Obtendo InitialContext..." );
	    jndiContext = getInitial();
	} catch ( Exception e )
	{
	    System.out.println( "Erro ao criar o JNDI: " + e.toString() );
	    System.exit( 1 );
	}

	try
	{

	    System.out.println( "Obtendo Queue Connection Factory" );
	    queueConnectionFactory = (QueueConnectionFactory) jndiContext
		    .lookup( "ConnectionFactory" );

	    System.out.println( "Obtendo Queue" );
	    queue = (Queue) jndiContext.lookup( NOME_FILA );

	} catch ( NamingException e )
	{
	    System.out.println( "problemas no JNDI API lookup : " + e.toString() );
	    System.exit( 1 );
	}

	try
	{
	    System.out.println( "Criando conexao com Queue Connection Factory..." );
	    queueConnection = queueConnectionFactory.createQueueConnection();

	    System.out.println( "Criando conexao com Queue..." );
	    queueSession = queueConnection.createQueueSession( false, Session.AUTO_ACKNOWLEDGE );

	    System.out.println( "Criando o Receiver" );
	    queueReceiver = queueSession.createReceiver( queue );

	    System.out.println( "Iniciando a conex�o..." );
	    queueConnection.start();
	    while ( true )
	    {
		Message m = queueReceiver.receive( 1 );

		if ( m != null )
		{
		    if ( m instanceof TextMessage )
		    {
			message = (TextMessage) m;
			LeXML leXML = new LeXML();
			System.out.println( "A mensagem recebida: " );
			leXML.leXML( message.getText() );

			Queue tempQueue = (Queue) message.getJMSReplyTo();

			replySender = queueSession.createSender( tempQueue );

			TextMessage resposta = queueSession.createTextMessage();
			resposta.setText( aprovaCompra( message.getText() ) );

			resposta.setJMSCorrelationID( message.getJMSMessageID() );

			replySender.send( resposta );
			System.out.println( "Resposta enviada a fila de resposta." );

			if ( "Fim".equalsIgnoreCase( message.getText() ) )
			{
			    break;
			}
		    } else
		    {
			break;
		    }
		}
	    }
	} catch ( JMSException e )
	{
	    System.out.println( "Exception : " + e.toString() );
	} finally
	{

	    System.out.println( "Fechando conexoes" );
	    if ( queueConnection != null )
	    {
		try
		{
		    queueConnection.close();
		} catch ( JMSException e )
		{
		    System.out.println("Erro de JMS " + e.getMessage());
		}
	    }
	}

    }

    @Override
    public void run( )
    {
	try
	{
	    QueueConsumidor queueConsumidor = new QueueConsumidor();
	    queueConsumidor.iniciaProcesso();
	} catch ( JAXBException e )
	{
	    e.printStackTrace();
	}

    }

    public String aprovaCompra( String xml ) throws JAXBException
    {

	double saldoCliente = 1000;

	String msgResposta = "";
	LeXML leXML = new LeXML();
	Cliente clienteSolicitacaoCompra = leXML.leXMLtransformaCliente( xml );

	if ( clienteSolicitacaoCompra.getValorCompra() > saldoCliente )
	{
	    msgResposta = "Saldo insuficiente";
	} else
	{
	    msgResposta = "Aprovacao";
	}

	enviaEmail( msgResposta, clienteSolicitacaoCompra.getEmail() );

	return msgResposta;
    }

    public void enviaEmail( String mensagem, String to )
    {
	EnviaEmail email = new EnviaEmail();
	email.enviaEmail( mensagem, to );
    }
}
