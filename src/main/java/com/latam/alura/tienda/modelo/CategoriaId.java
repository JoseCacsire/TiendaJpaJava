package com.latam.alura.tienda.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
//@Embeddable para indicarle a JPA que este no es una nueva tabla, sino que es una clase que va a contener atributo a 
//ser inyectado en una entidad.Igual que hizimos con la clase DatosPersonales.java
@Embeddable
/*
 Todos los elementos que sean del tipo embeddable tienen que implementar la interfaz serializable. Ya que son elementos 
 que van a ser serializados en bits para transitar en nuestra aplicación.
 */
public class CategoriaId implements Serializable{
	private String nombre;
	private String password;

	public CategoriaId() {
	}
	
	public CategoriaId(String nombre, String password) {
		this.nombre = nombre;
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "CategoriaId [nombre=" + nombre + ", password=" + password + "]";
	}

	//para identificar que son elementos únicos, tenemos que implementar el método hashcode.
/*
	+Exactamente, una de las principales razones para proporcionar una implementación personalizada del método hashCode() en tus clases 
	es evitar errores y garantizar que objetos iguales según tu lógica de negocio se comporten como iguales 
	+Sin una implementación personalizada de hashCode(), los objetos se manejarán en función de su dirección de memoria, lo que puede 
	llevar a resultados incorrectos en casos donde los objetos deberían considerarse iguales debido a su contenido.
	+Genera el mismo código hash (basado en la dirección de memoria) para objetos con los mismos atributo osea iguales. 
	+Es necesario que tanto equals() como hashCode() estén correctamente implementados para garantizar la coherencia en las estructuras de datos basadas en hash. 
	+Cuando encuentras objetos que son iguales según la lógica definida en el método equals(), un buen diseño de implementación de hashCode()
	 debería generar el mismo código hash para esos objetos.Asi evitas duplicados.
*/
	@Override
	public int hashCode() {
		return Objects.hash(nombre, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaId other = (CategoriaId) obj;
		return Objects.equals(nombre, other.nombre) && Objects.equals(password, other.password);
	}
	
	
	
}	
