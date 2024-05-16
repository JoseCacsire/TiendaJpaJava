package com.latam.alura.tienda.modelo;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// vamos a conectar esta clase que puede ser embutida con la clase cliente a través de la notación @Embedded
	/*
	 De esta forma, estamos indicando a JPA que esta clase, que esta clase de datos personales está siendo 
	 inyectada dentro del atributo datos personales. De esa forma, nosotros no creamos una nueva tabla, sino 
	 que simplemente estamos colocando los atributos de esa clase dentro de esta clase.
	 */
	@Embedded
	private DatosPersonales datosPersonales;
	
	public Cliente() {
	}

	public Cliente(String nombre, String dni) {
		this.datosPersonales = new DatosPersonales(nombre,dni);
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return this.datosPersonales.getNombre();
	}

	public void setNombre(String nombre) {
		this.datosPersonales.setNombre(nombre);;
	}

	public String getDni() {
		return this.datosPersonales.getDni();
	}

	public void setDni(String dni) {
		this.datosPersonales.setDni(dni);
	}
	/*
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", dni=" + dni + "]";
	}
	*/

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", datosPersonales=" + datosPersonales + "]";
	}
	
	
	
}
