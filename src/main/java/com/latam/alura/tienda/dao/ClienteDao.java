package com.latam.alura.tienda.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.modelo.Cliente;

public class ClienteDao {
	
	private EntityManager em;
	
	public ClienteDao(EntityManager em) {
		this.em = em;
	}
	
	//Realizamos la persistencia,en este caso insertamos un registro a la tabla
	public void guardar(Cliente Cliente) {
		this.em.persist(Cliente);
	}
	
	/*
	  cuando usamos .class después del nombre de la clase, estamos obteniendo un objeto especial que proporciona 
	  información sobre la clase. En el contexto de tu método y JPA, esto ayuda a indicar qué tipo de objeto 
	  estamos buscando en la base de datos.
	 */
	public Cliente consultarPorId(Long id) {
		return em.find(Cliente.class, id);
	}
	
	public List<Cliente> consultarTodos() {
		String jpql = "SELECT P FROM Cliente AS P";
		return em.createQuery(jpql,Cliente.class).getResultList();
	}
	
	public List<Cliente> consultarPorNombre(String nombre) {
		String jpql = " SELECT P FROM Cliente AS P WHERE P.nombre=:nombre ";
		return em.createQuery(jpql,Cliente.class).setParameter("nombre",nombre).getResultList();
	}
	
	public List<Cliente> consultarPorNombreDeCategoria(String nombre) {
		//Acuerdate que jpql esun lenguaje de consulta orientado a objetos utilizado en Java 
		//para interactuar con bases de datos a través de JPA (Java Persistence API). JPQL te permite 
		//	definir consultas en términos de tus clases de entidad y sus atributos, en lugar de usar SQL directamente.
		//JPQL trabaja con entidades y objetos en lugar de tablas y filas. 
		//Las consultas JPQL se traducen automáticamente a SQL específico de la base de datos que estés utilizando, 
		//lo que te permite cambiar de base de datos sin modificar las consultas.
		/*
		 unque JPQL se basa en SQL, tiene una sintaxis ligeramente diferente para manejar las particularidades de trabajar 
		 con objetos. Puedes realizar consultas SELECT, INSERT, UPDATE y DELETE similares a SQL, pero en lugar de operar 
		 en tablas y columnas, operas en entidades y atributos.
		 */
		String jpql = " SELECT P FROM Cliente AS P WHERE P.categoria.nombre=:nombre ";
		return em.createQuery(jpql,Cliente.class).setParameter("nombre",nombre).getResultList();
	}
	
	public BigDecimal consultarPrecioPorNombreDeCliente(String nombre) {
		String jpql = " SELECT P.precio FROM Cliente AS P WHERE P.nombre=:nombre ";
		// asignar el tipo que nos va a retornar.Que sería BigDecimal.class
		//Aquí ya no vamos a tener una lista de resultado, sino un resultado único, y con eso queda finalizada nuestra consulta.
		//Por eso uso el getSingleResult
		return em.createQuery(jpql,BigDecimal.class).setParameter("nombre",nombre).getSingleResult();
	}
	
}
