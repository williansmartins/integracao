package com.fiap.test;

import javax.xml.bind.JAXBException;

import com.fiap.service.QueueConsumidor;

public class TesteConsumidor implements Runnable
{

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

    public static void main( String[] args )
    {
	Thread t = new Thread( new QueueConsumidor() );
	t.start();
    }
}
