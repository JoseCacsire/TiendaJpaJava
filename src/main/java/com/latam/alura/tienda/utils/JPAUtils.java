package com.latam.alura.tienda.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {
	//Esta instancia se utiliza para crear y administrar instancias de EntityManager, 
	//que son las que realmente se utilizan para interactuar con la base de datos.
	private static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("tienda");
	
	public static EntityManager getEntityManager() {
		//Para interactuar con la base de datos
		return FACTORY.createEntityManager();
	}
	
}
