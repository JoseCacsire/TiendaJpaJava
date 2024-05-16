/*
package com.latam.alura.tienda.prueba;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.latam.alura.tienda.dao.CategoriaDao;
import com.latam.alura.tienda.dao.ProductoDao;
import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utils.JPAUtils;

public class RegistroDeProducto {

	public static void main(String[] args) {

		// Estado Transient
		Categoria celulares = new Categoria("CELULARES");
		// Producto celular = new Producto("Nokia","Telefono usado",new
		// BigDecimal("1000"),celulares);

		// Como el JPAUtils tiene un metodo estatico no es necesario instanciarlo
		// Recuerda que atributos estaticos o metodos se llaman desde la clase
		// sin necesidad de instanciar la clase para crear un objeto
		// Manteneniendo las conexiones antes de los Dao,para evitar la duplicacion
		// de codigo como la conexion varias veces
		EntityManager em = JPAUtils.getEntityManager();

		// ProductoDao productoDao = new ProductoDao(em);
		// CategoriaDao categoriaDao = new CategoriaDao(em);

		// Estado Managed
		// con el begin indicamos que debe comenzar las transacciones
		em.getTransaction().begin();

		em.persist(celulares);

		System.out.println("pipa");
		celulares.setNombre("Libros");

		// Hacer efectivos los cambios pendientes en la base de datos
		// Esto sincroniza el contexto de persistencia con la base de datos.
		// Sincronizar los cambios con la base de datos usando flush()
		em.flush();
		// Limpiar el contexto de persistencia
		// Esto elimina cualquier objeto que esté siendo gestionado por el
		// EntityManager.
		// Limpiar el contexto de persistencia para desvincular las entidades administradas
		
		//Pasamos a estado detached
		em.clear();

		// La entidad vuelve a ser gestionada Managed
		// Hacer una fusión (merge) de la categoría
		// Volver a administrar (attach) la categoría "CELULARES" usando merge
		// Realizar un merge de la categoría "CELULARES" para actualizarla en el contexto de persistencia
		celulares = em.merge(celulares);

		System.out.println("pipi");
		celulares.setNombre("pepe");

		// Hacer efectivos los cambios pendientes en la base de datos
		// Sincronizar los cambios con la base de datos usando flush()
		em.flush();
		//Pasamos a estado detached
		em.clear();
		
		// La entidad vuelve a ser gestionada Managed
		celulares = em.merge(celulares);
		
		//Seguimos en estado managed
		em.remove(celulares);
		em.flush();
		
		//Estado "Detached"
		/*Recuerda que con el merge uedes reasociar una entidad "Detached" con el contexto de persistencia 
		Si deseas realizar cambios en la entidad y sincronizarla con la base de datos nuevamente.*/
		// Finalizar la transacción
		//em.getTransaction().commit();

		// Cerrar el EntityManager para liberar recursos
		//em.close();

		// categoriaDao.guardar(celulares);
		// productoDao.guardar(celular);

		// Realizamos el insert con el commit
		// em.getTransaction().commit();
		// em.close();
	//}

//}

