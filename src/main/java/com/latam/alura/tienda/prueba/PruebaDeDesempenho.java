package com.latam.alura.tienda.prueba;

import java.io.FileNotFoundException;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.dao.PedidoDao;
import com.latam.alura.tienda.modelo.Pedido;
import com.latam.alura.tienda.utils.JPAUtils;

public class PruebaDeDesempenho {
	public static void main(String[] args) throws FileNotFoundException {
		LoadRecords.cargarRegistros();
		
		EntityManager em = JPAUtils.getEntityManager();
		
		PedidoDao pedidoDao = new PedidoDao(em);
		//Ejecutando la consulta planeada
		Pedido pedidoConCliente = pedidoDao.consultarPedidoConCliente(3l);
		
//		Pedido pedido = em.find(Pedido.class, 3l);
		
		em.close();
		
//		System.out.println(pedido.getFecha());
//		System.out.println(pedido.getItems().size());
//		System.out.println(pedido.getCliente().getNombre());
		System.out.println(pedidoConCliente.getCliente());
		
		
	}
}