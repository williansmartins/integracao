package com.fiap.client;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueRequestor;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueProdutor
{

    private static final String NOME_FILA = "queue/FilaFIAP";

    Context jndiContext;
    QueueConnectionFactory queueConnectionFactory;
    QueueConnection queueConnection;
    QueueSession queueSession;
    Queue queue;
    QueueSender queueSender;
    TextMessage message;

    private static InitialContext getInitial( ) throws Exception
    {
	Properties props = new Properties();
	props.setProperty( "java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory" );
	props.setProperty( "java.naming.provider.url", "localhost:1099" );
	props.setProperty( "java.naming.factory.url.pkgs", "org.jboss.naming" );
	InitialContext context = new InitialContext( props );
	return context;
    }

    public String enviaMensagem( String txtMensagem )
    {

	String resposta = "";
	try
	{
	    System.out.println( "InitialContext..." );
	    jndiContext = getInitial();
	} catch ( Exception e )
	{
	    System.out.println( "Erro ao criar o JNDI" + e.toString() );
	    System.exit( 1 );
	}

	try
	{
	    System.out.println( "Queue Connection Factory" );
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

	    System.out.println( "Criando conexao com " + "Queue Connection Factory..." );
	    queueConnection = queueConnectionFactory.createQueueConnection();

	    System.out.println( "Criando conexao com Queue..." );
	    queueSession = queueConnection.createQueueSession( false, Session.CLIENT_ACKNOWLEDGE );

	    System.out.println( "Criando o " + "QueueRequestor..." );
	    QueueRequestor queueRequestor = new QueueRequestor( queueSession, queue );

	    System.out.println( "Iniciando a conexao..." );
	    queueConnection.start();

	    System.out.println( "Criando Mensagem Text.." );
	    message = queueSession.createTextMessage();
	    message.setText( txtMensagem );

	    System.out.println( "Correlation ID pre " + message.getJMSMessageID() );

	    System.out.println( "Enviando a mensagem " + txtMensagem
		    + " e aguardando resposta..." );
	    TextMessage reply = (TextMessage) queueRequestor.request( message );

	    System.out.println( "Correlation ID pos " + message.getJMSMessageID() );
	    System.out.println( "Resposta :" + reply.getText() );

	    resposta = reply.getText();

	} catch ( JMSException e )
	{
	    System.out.println( "Exception : " + e.toString() );
	} finally
	{

	    System.out.println( "Fechando conexao " + "com a Queue..." );
	    if ( queueConnection != null )
	    {
		try
		{
		    System.out.println( "Fechando " + "conexao com o Queue Connection." );
		    queueConnection.close();
		} catch ( JMSException e )
		{
		    System.out.println( "Exception : " + e.toString() );
		}
	    }
	}
	return resposta;

    }
}
