package com.fiap.test;

import javax.xml.bind.JAXBException;

import com.fiap.client.QueueProdutor;
import com.fiap.jaxb.Cliente;
import com.fiap.jaxb.GeraXML;

public class TesteProdutor
{

    public static void main( String[] args ) throws JAXBException
    {
	QueueProdutor produtor = new QueueProdutor();
	Cliente cliente = new Cliente( "01", "Willians Martins", "Rua Amapá, 345",
		"0000.0000.0000.0000", 123, "contato@williansmartins.com" );
	GeraXML xml = new GeraXML();
	String geraXML = xml.geraXML( cliente );
	String enviaMensagem = produtor.enviaMensagem( geraXML );
	System.out.println( enviaMensagem );
	System.exit( 0 );

    }
}
