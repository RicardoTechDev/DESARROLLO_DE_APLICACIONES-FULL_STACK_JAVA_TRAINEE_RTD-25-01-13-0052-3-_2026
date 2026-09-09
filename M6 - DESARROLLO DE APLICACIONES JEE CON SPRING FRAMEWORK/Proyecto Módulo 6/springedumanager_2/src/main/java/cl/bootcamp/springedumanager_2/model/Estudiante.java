package cl.bootcamp.springedumanager_2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
 * @Entity
 * Indica que esta clase representa
 * una entidad que será administrada
 * mediiante JPA 
 * */

@Entity


/*
 * @Table(name = "estudiantes" )
 * 
 *  indicamos explicitamente el nombre
 *  de la tabla en MySQL
 * */
@Table(name = "estudiantes" )
public class Estudiante {
	
	//Indica que esta propiedad representa la clave primaria o Primary Key 
	@Id
	//IDENTITY --> utilizará el AUTO_INCREMENT de MySql
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable= false, length= 150)
	private String nombre;
	
	@Column(nullable= false, length= 150, unique=true)
	private String email;
	
	//JPA necesita el constructor vacío
	public Estudiante() {
		
	}

	public Estudiante(int id, String nombre, String email) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
