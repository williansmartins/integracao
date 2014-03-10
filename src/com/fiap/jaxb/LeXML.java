package com.fiap.jaxb;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class LeXML
{

    public static void main( String[] args ) throws Exception
    {

	String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><fornecedor><codigo>01</codigo><endereco>Rua Trussu, 06</endereco><nome>Franklin</nome></fornecedor>";
	StringReader sr = new StringReader( xml );

	JAXBContext jc = JAXBContext.newInstance( Cliente.class );

	Unmarshaller unmarshaller = jc.createUnmarshaller();

	Cliente fornecedor = (Cliente) unmarshaller.unmarshal( sr );

	System.out.println( fornecedor );
    }

    public void leXML( String xml ) throws JAXBException
    {
	StringReader sr = new StringReader( xml );

	JAXBContext jc = JAXBContext.newInstance( Cliente.class );

	Unmarshaller unmarshaller = jc.createUnmarshaller();

	Cliente fornecedor = (Cliente) unmarshaller.unmarshal( sr );

	System.out.println( fornecedor );
    }

    public Cliente leXMLtransformaCliente( String xml ) throws JAXBException
    {
	StringReader sr = new StringReader( xml );

	JAXBContext jc = JAXBContext.newInstance( Cliente.class );

	Unmarshaller unmarshaller = jc.createUnmarshaller();

	Cliente cliente = (Cliente) unmarshaller.unmarshal( sr );

	return cliente;
    }

}
