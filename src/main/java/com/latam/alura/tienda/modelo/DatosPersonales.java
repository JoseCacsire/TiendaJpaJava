package com.latam.alura.tienda.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

//indicarle a JPA que esta clase va a ser inyectada dentro de la clase cliente, que puede ser una clase 
//que puede ser inyectada dentro de la clase cliente.Para eso vamos a utilizar la notación @Embedable de JPA
@Embeddable
/*
 todas las clases embutidas sean llaves primarias o no deben implantar la interfaz Serializable ya que sirve para 
 indicarle a la API que van a haber datos transitando dentro de ella.
*/
public class DatosPersonales implements Serializable{
	private String nombre;
	private String dni;
	
	public DatosPersonales() {
	}
	
	public DatosPersonales(String nombre, String dni) {
		this.nombre = nombre;
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "DatosPersonales [nombre=" + nombre + ", dni=" + dni + "]";
	}
	
	
	
}
