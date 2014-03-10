package com.fiap.jaxb;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GeraXML
{

    public String geraXML( Cliente fornecedor ) throws JAXBException
    {

	JAXBContext jc = JAXBContext.newInstance( Cliente.class );

	Marshaller marshaller = jc.createMarshaller();
	marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
	StringWriter sw = new StringWriter();
	marshaller.marshal( fornecedor, sw );
	return sw.toString();
    }

}
