package com.latam.alura.tienda.prueba;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;

import com.latam.alura.tienda.dao.CategoriaDao;
import com.latam.alura.tienda.dao.ProductoDao;
import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.CategoriaId;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utils.JPAUtils;

public class RegistroDeProducto1 {

	public static void main(String[] args) {
		
		regitrarProducto();
		EntityManager em = JPAUtils.getEntityManager();
		ProductoDao productoDao = new ProductoDao(em);
		Producto producto = productoDao.consultaPorId(1l);
//		System.out.println(producto.getPrecio());
		
		List<Producto> productos = productoDao.consultarTodos();
		productos.forEach(prod -> System.out.println(prod.getDescripcion()));
		
//		List<Producto> productos1 = productoDao.consultarPorNombre("Pepe");
//		productos1.forEach(prod -> System.out.println(prod.getDescripcion()));
		
//		List<Producto> productos2 = productoDao.consultarPorNombreDeCategoria2("Celulares");
//		productos2.forEach(prod -> System.out.println(prod.getCategoria()));
		
		Producto producto2 = productoDao.consultaPorId(1l);
		System.out.println(producto2);
		
//		BigDecimal precio = productoDao.consultarPrecioPorNombreDeProducto("Pepe");
//		System.out.println(precio);
		
		//Esto se usa antes de usar el @EmbeddedId en la calse Categoria.Para que te funcione tendrías que descomentar lo que esta arriba de esa notaion en esa clase mencionada.
		//Acá el Id que nosotros colocamos, que es de tipo long, vamos a pasar el número 1, haciendo referencia a que es el primer elemento en la base de datos.
		/*Categoria find = em.find(Categoria.class, 1l);
		System.out.println(find.getNombre());*/
		
//		Categoria find = em.find(Categoria.class, new CategoriaId("Celulares","456"));
		
//		System.out.println(find.getNombre());
	}

	private static void regitrarProducto() {
		
		Categoria celulares = new Categoria("Celulares");
		
		Producto celular = new Producto("Pepe","MALO",new BigDecimal("1000"),celulares);
		
		EntityManager em = JPAUtils.getEntityManager();
		
		ProductoDao productoDao = new ProductoDao(em);
		
		CategoriaDao categoriaDao = new CategoriaDao(em);
		
		em.getTransaction().begin();
		
		categoriaDao.guardar(celulares);
		
		productoDao.guardar(celular);
		
		em.getTransaction().commit();
		em.close();
		
		
	}

}
