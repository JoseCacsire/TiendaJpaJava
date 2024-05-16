package com.latam.alura.tienda.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.latam.alura.tienda.modelo.Producto;

public class ProductoDao {
	
	private EntityManager em;
	
	public ProductoDao(EntityManager em) {
		this.em = em;
	}
	
	//Realizamos la persistencia,en este caso insertamos un registro a la tabla
	public void guardar(Producto producto) {
		this.em.persist(producto);
	}
	
	/*
	  cuando usamos .class después del nombre de la clase, estamos obteniendo un objeto especial que proporciona 
	  información sobre la clase. En el contexto de tu método y JPA, esto ayuda a indicar qué tipo de objeto 
	  estamos buscando en la base de datos.
	 */
	public Producto consultaPorId(Long id) {
		return em.find(Producto.class, id);
	}
	
	public List<Producto> consultarTodos() {
		String jpql = "SELECT P FROM Producto AS P";
		return em.createQuery(jpql,Producto.class).getResultList();
	}
	
	public List<Producto> consultarPorNombre(String nombre) {
		String jpql = " SELECT P FROM Producto AS P WHERE P.nombre=:nombre ";
		return em.createQuery(jpql,Producto.class).setParameter("nombre",nombre).getResultList();
	}
	
	public List<Producto> consultarPorNombreDeCategoria1(String nombre) {
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
		String jpql = " SELECT P FROM Producto AS P WHERE P.categoria.nombre=:nombre ";
		return em.createQuery(jpql,Producto.class).setParameter("nombre",nombre).getResultList();
	}
	
	public List<Producto> consultarPorNombreDeCategoria2(String nombre) {
	    String jpql = "SELECT p FROM Producto p WHERE p.categoria.categoriaId.nombre = :nombre ";
	    return em.createQuery(jpql, Producto.class)
	            .setParameter("nombre", nombre)
	            .getResultList();
	}

	
	
	public BigDecimal consultarPrecioPorNombreDeProducto(String nombre) {
		// asignar el tipo que nos va a retornar.Que sería BigDecimal.class
		//Aquí ya no vamos a tener una lista de resultado, sino un resultado único, y con eso queda finalizada nuestra consulta.
		//Por eso uso el getSingleResult
		return em.createNamedQuery("Producto.consultaDePrecio",BigDecimal.class).setParameter("nombre",nombre).getSingleResult();
	}
	/*
	 Este método consultarPorParametros construye una consulta JPQL (Query de Lenguaje de Consulta de Objetos Java) de manera
	  dinámica en función de los parámetros proporcionados (nombre, precio, fecha). Luego, crea una consulta TypedQuery a 
	  partir de la consulta JPQL y establece los parámetros en la consulta si se proporcionaron. Finalmente, ejecuta la 
	  consulta y devuelve la lista de resultados. Las condiciones se agregan a la consulta solo si los parámetros son válidos, 
	  de lo contrario, se omiten de la consulta. Esto permite construir consultas flexibles que se ajusten a los criterios de
	  búsqueda proporcionados. 
	 */
	public List<Producto> consultarPorParametros(String nombre, BigDecimal precio,LocalDate fecha){
		/*
		 Si necesitas una cadena que no cambie, usa String.
		 Si necesitas construir o manipular una cadena de manera eficiente, usa StringBuilder.
		 El StringBuilder es parecido a una lista,pero sería una lista de caracteres q es mutable
		 Ya q puedes usar metodos como el append,insert,delete,replace.Esto lo hace mas eficiente en rendimiento
		 En cambio el strin es inmutable y tendrias que concatenar osea usar el +=.
		 */
		//WHERE 1=1 es una técnica que simplifica la construcción de consultas dinámicas al proporcionar una base 
		//inicial que siempre es verdadera y permite agregar condiciones adicionales de manera sencilla.
		//Si deseas agregar una nueva condición, simplemente puedes concatenarla a la consulta existente sin preocuparte 
		//por si es la primera condición o no, lo que simplifica la lógica para construir consultas dinámicas.
		//RECUERDA --> Mediante los if agregas las condiciones
		StringBuilder jpql=new StringBuilder("SELECT p FROM Producto p WHERE 1=1 ");
		//!nombre.trim().isEmpty() significa que estamos verificando si la cadena de texto nombre, 
		//después de eliminar espacios en blanco al principio y al final con el .trim(), no está vacía
		if(nombre!=null && !nombre.trim().isEmpty()) {
			jpql.append("AND p.nombre=:nombre ");
		}
		if(precio!=null && !precio.equals(new BigDecimal(0))) {
			jpql.append("AND p.precio=:precio ");
		}
		if(fecha!=null) {
			jpql.append("AND p.fechaDeRegistro=:fecha");
		}
		
		TypedQuery<Producto> query = em.createQuery(jpql.toString(),Producto.class);
		
		if(nombre!=null && !nombre.trim().isEmpty()) {
			query.setParameter("nombre", nombre);
		}
		if(precio!=null && !precio.equals(new BigDecimal(0))) {
			query.setParameter("precio", precio);
		}
		if(fecha!=null) {
			query.setParameter("fechaDeRegistro", fecha);
		}

		return query.getResultList();		
	}
	
	//Esta es una segunda forma mas corta de hacer lo mismo que el metodo consultarPorParametros
	//Desventaja de Criteria API --> Criteria API hace que el código sea más difícil de entender.
	public List<Producto> consultarPorParametrosConApiCriteria(String nombre, BigDecimal precio,LocalDate fecha){
		
		//ese builder es el que nos va a permitir crear nuestra consulta.
		//builder se utiliza para construir condiciones de consulta
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Producto> query = builder.createQuery(Producto.class);
		// este from corresponde a nuestro from en nuestra consulta jpql
		//from es esencial para definir desde qué entidad se realizará la consulta y proporciona una base 
		//para construir la consulta de Criteria en JPA.
		Root<Producto> from = query.from(Producto.class);
		 // Inicializar un predicado (filtro) que se utilizará para agregar condiciones a la consulta
		//filtro se utiliza para agregar condiciones dinámicas basadas en los parámetros de entrada proporcionados
		Predicate filtro = builder.and();
		
		if(nombre!=null && !nombre.trim().isEmpty()) {
			//Entonces sí vemos que este from corresponde al from en nuestra consulta, así como el
			//and que corresponde a las condiciones.Y también tenemos la asignación de parámetros como nuestro SetParameter
			filtro = builder.and(filtro,builder.equal(from.get("nombre"),nombre));
		}		
		if(precio!=null && !precio.equals(new BigDecimal(0))) {
			filtro = builder.and(filtro,builder.equal(from.get("precio"),nombre));
		}
		if(fecha!=null) {
			filtro = builder.and(filtro,builder.equal(from.get("fecha"),nombre));
		}
		// Asignar el filtro a la consulta Criteria
		query = query.where(filtro);
		
		return em.createQuery(query).getResultList();
		/*
		  builder se utiliza para construir componentes de consulta, query se utiliza para definir la estructura 
		  general de la consulta y especificar el tipo de resultado esperado, y from se utiliza para especificar la 
		  entidad raíz desde la cual se realizará la consulta. Estos elementos se combinan para construir una consulta
		   de criterios en JPA de manera programática y flexible. 
		 */
	}
	
}
