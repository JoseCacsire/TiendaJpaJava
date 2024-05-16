package com.latam.alura.tienda.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria {

	//	Voy a comentar esto por que voy a crear una llave que sea compuesta con el nombre
	//	y la contraseña.Para eso se va a generar una clase que se llamara categoriaId que estara dentro del paquete MODELO
	//	@Id
	//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//	private Long id;
	//	private String nombre;
	//

	//Entonces, vamos a definir nuestra categoriaId como llave primaria a través de la notación @EmbeddedId.
	//asignando una clave compuesta con la anotación @EmbeddedId.
	/*
	@EmbeddedId que nos permite indicarle a nuestra entidad que está siendo inyectada una clase que no es una entidad, sino
	una clase que compone el ID de esa entidad formado por multiples parametros.
	*/

	@EmbeddedId
	private CategoriaId categoriaId;

	public Categoria() {
	}

	public Categoria(String nombre) {
		this.categoriaId = new CategoriaId(nombre,"456");
	}

	public String getNombre() {
		return categoriaId.getNombre();
	}

	public void setNombre(String nombre) {
		this.categoriaId.setNombre(nombre);
	}

	@Override
	public String toString() {
		return "Categoria [categoriaId=" + categoriaId + "]";
	}
	
	/*
	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + "]";
	}
	*/
	
	
}
