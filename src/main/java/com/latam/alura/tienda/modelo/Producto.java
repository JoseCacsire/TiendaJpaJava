package com.latam.alura.tienda.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;
//Para que haga el mapeaiento de la clase que es una entidad en la base de datos
@Entity
//Si el nombre de mi clase difiere con el nombre de la tabla de la BD usar
//el name = "productos"
@Table(name = "productos")
/*NamedQuery --> Son consultas predefinidas y nombradas que se declaran en la entidad o en el archivo
de metadatos de persistencia.Sirve para reutilizarlos en tus metodos creados en DAO.Con esto ya no es necesario crear
el jpql en tus metodos sino llamarlos por el namedQuery.Osea si puedes crear los jpql,pero esta es una opcion
mas comoda para definirla una vez y llamarla en tu DAO.
Por ejemplo, si tienes una entidad 'Usuario' y a menudo necesitas buscar usuarios por su apellido, podrías definir 
una NamedQuery en la clase 'Usuario' que haga exactamente eso. Luego, en tu DAO, simplemente llamarías a esa consulta 
por su nombre en lugar de escribir la consulta cada vez.
Estas consultas predefinidas pueden ser consultas SQL o consultas JPQL (Java Persistence Query Language). 
*/
@NamedQuery(name = "Producto.consultaDePrecio",query =" SELECT P.precio FROM Producto AS P WHERE P.nombre=:nombre ")
//Para conectar eso, esa entidad con la entidad producto, tenemos que utilizar una notación en la entidad producto que se llama @Inheritance.
//Lo que queremos hacer es unir la tabla hija con la del padre en la base de datos
//Pero si quieres que sean tablas separadas y no una.Usas el JOINED
//Si quieres que sea una unica tabla usas el SINGLE_TABLE
/*
 Entonces nosotros podemos escoger desempeño, utilizando la estrategia single table para tener valores más rápidos. 
 O podemos obtener un mayor orden y separar las entidades con la notación @JoinTable. 
 */
/*
  Explicacion detallada:
  La tabla Elctronico tendrá una clave externa que hace referencia a la clave primaria de la tabla Producto, al igual que 
  la tabla Libroo. Esto permite que los registros de Electronico y Libro estén relacionados con Producto.
 */
@Inheritance(strategy = InheritanceType.JOINED)
public class Producto {
	
	//Indicando cual será nuestro primary key (pk) de la tabla
	@Id
	//Con esto la responsabilidad pasa a la base de datos no al usuario
	//Con GenerationType.IDENTITY es como usar el autoincrement en mysql o otra BD relacional
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String descripcion;
	private BigDecimal precio;
	private LocalDate FechaDeRegistro = LocalDate.now();
	//ManyToOne --> muchos productos están relacionados con una única categoría
	@ManyToOne(fetch = FetchType.LAZY)
	private Categoria categoria;
	
	
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Producto() {
	}

	public Producto(String nombre, String descripcion, BigDecimal precio, Categoria categoria) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.categoria = categoria;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		System.out.println("String");
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", FechaDeRegistro=" + FechaDeRegistro + ", categoria=" + categoria + "]";
	}

	
	
	/*
	 ¿Cuál es la mejor definición de una entidad en JPA?
	 Es una clase que hace el mapeamiento de una tabla del banco de datos o BD.
	 Una entidad JPA funciona como un espejo de una tabla en el banco de datos o BD. 
	 */
	
}
