package com.fiap.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviaEmail
{

    public void enviaEmail( String mensagem, String to )
    {

	Properties props = new Properties();
	props.put( "mail.smtp.host", "smtp.gmail.com" );
	props.put( "mail.smtp.socketFactory.port", "465" );
	props.put( "mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory" );
	props.put( "mail.smtp.auth", "true" );
	props.put( "mail.smtp.port", "465" );

	Session session = Session.getDefaultInstance( props, new javax.mail.Authenticator()
	{
	    protected PasswordAuthentication getPasswordAuthentication( )
	    {
		return new PasswordAuthentication( "contato@williansmartins.com", "senha" );
	    }
	} );

	try
	{

	    Message message = new MimeMessage( session );
	    message.setFrom( new InternetAddress( to ) );

	    Address[] toUser = InternetAddress.parse( to );

	    message.setRecipients( Message.RecipientType.TO, toUser );
	    message.setSubject( "Confirmacao" );
	    message.setText( mensagem );
	    Transport.send( message );

	} catch ( MessagingException e )
	{
	    throw new RuntimeException( e );
	}
    }

}
