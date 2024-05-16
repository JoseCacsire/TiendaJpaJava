package com.latam.alura.tienda.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.modelo.Pedido;
import com.latam.alura.tienda.vo.RelatorioDeVenta;

public class PedidoDao {
	
	private EntityManager em;
	
	public PedidoDao(EntityManager em) {
		this.em = em;
	}
	
	//Realizamos la persistencia,en este caso insertamos un registro a la tabla
	public void guardar(Pedido Pedido) {
		this.em.persist(Pedido);
	}
	
	/*
	  cuando usamos .class después del nombre de la clase, estamos obteniendo un objeto especial que proporciona 
	  información sobre la clase. En el contexto de tu método y JPA, esto ayuda a indicar qué tipo de objeto 
	  estamos buscando en la base de datos.
	 */
	public Pedido consultarPorId(Long id) {
		return em.find(Pedido.class, id);
	}
	
	public List<Pedido> consultarTodos() {
		String jpql = "SELECT P FROM Pedido AS P";
		return em.createQuery(jpql,Pedido.class).getResultList();
	}
	
	/*
	public List<Pedido> consultarPorNombre(String nombre) {
		String jpql = " SELECT P FROM Pedido AS P WHERE P.nombre=:nombre ";
		return em.createQuery(jpql,Pedido.class).setParameter("nombre",nombre).getResultList();
	}
	 * */
	
	/*
	public List<Pedido> consultarPorNombreDeCategoria(String nombre) {
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
		 
		String jpql = " SELECT P FROM Pedido AS P WHERE P.categoria.nombre=:nombre ";
		return em.createQuery(jpql,Pedido.class).setParameter("nombre",nombre).getResultList();
	}
    */
	
	/*public BigDecimal consultarPrecioPorNombreDePedido(String nombre) {
		String jpql = " SELECT P.precio FROM Pedido AS P WHERE P.nombre=:nombre ";
		// asignar el tipo que nos va a retornar.Que sería BigDecimal.class
		//Aquí ya no vamos a tener una lista de resultado, sino un resultado único, y con eso queda finalizada nuestra consulta.
		//Por eso uso el getSingleResult
		return em.createQuery(jpql,BigDecimal.class).setParameter("nombre",nombre).getSingleResult();
	}*/
	
	public BigDecimal valorTotalVendido() {
		String jpql = " SELECT SUM(p.valorToral) FROM Pedido p";
		return em.createQuery(jpql,BigDecimal.class).getSingleResult();
	}
	
	//Primera forma de hacer un RELATORIO DE VENTAS
	//Usar el Object[] por que tas trabajando con tablas y necesitas recorrerla por posicion
	//no llamando a sus atributos con get.Ya que esa cosulta te arrojara una tabla con filas
	//por ende debes usar object[] para recorrerar cada elemento de la fila mediante sus posiciones [0] [1] [2]
	//ya q solo tiene 3 columnas.
	public List<Object[]> relatorioDeVentas(){
		String jpql = "SELECT producto.nombre,"
				+ "SUM(item.cantidad), "
				+ "MAX(pedido.fecha) "
				+ "FROM Pedido pedido "
				+ "JOIN pedido.items item "
				+ "JOIN item.producto producto "
				+ "GROUP BY producto.nombre "
				+ "ORDER BY item.cantidad DESC";
		return em.createQuery(jpql,Object[].class).getResultList();
	}
	
	//Segunda forma de hacer un RELATORIO DE VENTAS,este es mas específico ya que sea crea una clase y se instancia.
	public List<RelatorioDeVenta> relatorioDeVentasVO() {
		String jpql = "SELECT new com.latam.alura.tienda.vo.RelatorioDeVenta(producto.nombre, "
				+ "SUM(item.cantidad), "
				+ "MAX(pedido.fecha)) "
				+ "FROM Pedido pedido "
				+ "JOIN pedido.items item "
				+ "JOIN item.producto producto "
				+ "GROUP BY producto.nombre "
				+ "ORDER BY item.cantidad DESC";
		return em.createQuery(jpql,RelatorioDeVenta.class).getResultList();
	}
	
	/*Haciendo una consulta planeada --> Son consultas, donde nosotros ya planificamos cuáles 
	son los elementos que vamos a obtener posteriormente y aún si nosotros nos encontramos con 
	el EntityManager que se encuentra cerrado, que la conexión se encuentra cerrada, que es lo 
	que nos da acceso a la base de datos, nosotros ya tenemos esos registros almacenado dentro 
	de nuestra variable pedido.
	*/
	public Pedido consultarPedidoConCliente(Long id) {
		/*Como cliente tiene una estrategia de carga lazy entonces no va a realizar un join con la entidad pedido
		 Soo hara un select de pedido y de ahi de cliente separadamente.En cambio con el join fetch se realiza un join
		 JOIN FETCH --> nos permite realizar un join con la entidad deseada, en este caso la entidad cliente.
		 */
		/*
		 En resumen, "JOIN FETCH" es un recurso valioso en consultas JPQL porque mejora el rendimiento, reduce la 
		 cantidad de consultas innecesarias, aumenta la claridad del código y proporciona un mayor control sobre la
		  carga de relaciones en tus consultas
		 */
		String jpql = "SELECT p FROM Pedido AS p JOIN FETCH p.cliente WHERE p.id=:id";
		return em.createQuery(jpql,Pedido.class).setParameter("id", id).getSingleResult();
	}
	
}
