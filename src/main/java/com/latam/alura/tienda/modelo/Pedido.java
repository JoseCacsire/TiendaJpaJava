package com.latam.alura.tienda.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate fecha = LocalDate.now();
	private BigDecimal valorToral =  new BigDecimal(0);
	
	//Un cliente tiene muchos pedidos (1-N) Relacion muchos a uno
	//ManyToOne --> Muchos pedidos tiene un cliente.Con el ToOne Hace un join automatico con 
	//todos los atributos de esa entidad que se esta relacionando.Para eso hay q pasarlo de tipo
	//Lazy para que no me haga ese join automaticamente.Ya de tipo lazy nos permite llamar 
	//los elemento del cliente unicamente cuando sean solicitados
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	

	/*
	 * CascadeType.ALL.Uso esto para no crear el dao de itemsPedido
	 Cuando se utiliza CascadeType.ALL, significa que cualquier operación de persistencia o 
	 borrado realizada en la entidad actual se propagará automáticamente a la otra entidad 
	 relacionada. Es decir, si se persiste la entidad actual, las entidades relacionadas 
	 también se persistirán. Si se elimina la entidad actual, las entidades relacionadas 
	 también se eliminarán.
	 */
	//Con el ToMany no hace esa relacion con los atributos de esa entidad.Ya q por defaul es de 
	//tipo lazy osea que solo lo traera de vuelta en el select haciendo un join cuando sea solicitado.
	//En cambio el ToOne es de tipo eager osea que automaticamente de hace el join en el select.Esa otra
	//diferencia del ToMany con el ToOne
	@OneToMany(mappedBy = "pedido",cascade = CascadeType.ALL)	
	private List<ItemsPedido> items =  new ArrayList<>();
	
	public Pedido() {
	}

	public Pedido(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void agregarItems(ItemsPedido item) {
		item.setPedido(this);
		this.items.add(item);
		this.valorToral = this.valorToral.add(item.getValor());
	}

	public Long getId() {
		return id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getValorToral() {
		return valorToral;
	}

	public void setValorToral(BigDecimal valorToral) {
		this.valorToral = valorToral;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<ItemsPedido> getItems() {
		return items;
	}
	
	public void setItems(List<ItemsPedido> items) {
		this.items = items;
	}
}
