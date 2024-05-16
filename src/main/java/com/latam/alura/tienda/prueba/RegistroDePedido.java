package com.latam.alura.tienda.prueba;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.dao.CategoriaDao;
import com.latam.alura.tienda.dao.ClienteDao;
import com.latam.alura.tienda.dao.PedidoDao;
import com.latam.alura.tienda.dao.ProductoDao;
import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.Cliente;
import com.latam.alura.tienda.modelo.ItemsPedido;
import com.latam.alura.tienda.modelo.Pedido;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utils.JPAUtils;
import com.latam.alura.tienda.vo.RelatorioDeVenta;

public class RegistroDePedido {

	public static void main(String[] args) {
		
		regitrarProducto();
		EntityManager em = JPAUtils.getEntityManager();
		ProductoDao productoDao = new ProductoDao(em);
		Producto producto = productoDao.consultaPorId(1l);
		
		ClienteDao clienteDao = new ClienteDao(em);
		PedidoDao pedidoDao = new PedidoDao(em);
		
		Cliente cliente = new Cliente("Juan","123456");
		System.out.println("nombre");
		System.out.println(cliente);
		Pedido pedido = new Pedido(cliente);
		pedido.agregarItems(new ItemsPedido(5,producto,pedido));
		
		em.getTransaction().begin();
		
		clienteDao.guardar(cliente);
		pedidoDao.guardar(pedido);
		
		em.getTransaction().commit();
		
		
		//BigDecimal valorTotal = pedidoDao.valorTotalVendido();
		//System.out.println("Este es el valor total: "+valorTotal);
		
		/*List<Object[]> relatorio = pedidoDao.relatorioDeVentas();
		
		//Lo recorro asi ya que son arreglos lo que tiene ese Object[]
		//Ya q estoy trabajndo con una tabla que me arrojara la consulta
		//y cada obj representa una fila.
		for (Object[] obj : relatorio) {
			System.out.println(obj[0]);
			System.out.println(obj[1]);
			System.out.println(obj[2]);			
		}*/
		
		List<RelatorioDeVenta> relatorio = pedidoDao.relatorioDeVentasVO();
		/*
		  Esto significa que estamos haciendo referencia al método println de la clase System para imprimir cada elemento. 
		  La notación :: se utiliza para hacer esta referencia al método. 
		  es igual a esto:
		  relatorio.forEach(relatorioDeVenta -> System.out.println(relatorioDeVenta));
		 */
		relatorio.forEach(System.out::println);
		//lo de arriba es igual a lo de abajo (forEach)
		relatorio.forEach(relatorioDeVenta -> System.out.println(relatorioDeVenta));
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
