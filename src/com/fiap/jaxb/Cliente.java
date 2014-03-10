package com.fiap.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cliente
{

    private String codigo;
    private String nome;
    private String endereco;
    private String numeroCartao;
    private double valorCompra;
    private double saldo;
    private String email;

    public Cliente()
    {

    }

    public Cliente( String codigo, String nome, String endereco, String numeroCartao,
	    double valorCompra, String email )
    {
	super();
	this.codigo = codigo;
	this.nome = nome;
	this.endereco = endereco;
	this.numeroCartao = numeroCartao;
	this.valorCompra = valorCompra;
	this.email = email;
    }

    public String getCodigo( )
    {
	return codigo;
    }

    @XmlElement
    public void setCodigo( String codigo )
    {
	this.codigo = codigo;
    }

    public String getNome( )
    {
	return nome;
    }

    @XmlElement
    public void setNome( String nome )
    {
	this.nome = nome;
    }

    public String getEndereco( )
    {
	return endereco;
    }

    @XmlElement
    public void setEndereco( String endereco )
    {
	this.endereco = endereco;
    }

    public String getNumeroCartao( )
    {
	return numeroCartao;
    }

    @XmlElement
    public void setNumeroCartao( String numeroCartao )
    {
	this.numeroCartao = numeroCartao;
    }

    public String getEmail( )
    {
	return email;
    }

    @XmlElement
    public void setEmail( String email )
    {
	this.email = email;
    }

    public double getValorCompra( )
    {
	return valorCompra;
    }

    @XmlElement
    public void setValorCompra( double valorCompra )
    {
	this.valorCompra = valorCompra;
    }

    public double getSaldo( )
    {
	return saldo;
    }

    @XmlElement
    public void setSaldo( double saldo )
    {
	this.saldo = saldo;
    }

    @Override
    public String toString( )
    {
	return "Cliente [codigo=" + codigo + ", nome=" + nome + ", endereco=" + endereco
		+ ", numeroCartao=" + numeroCartao + ", valorCompra=" + valorCompra + ", saldo="
		+ saldo + ", email=" + email + "]";
    }

}
