package cl.bootcamp.springedumanager_2.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int id;


    @Column(
        nullable = false,
        length = 100
    )
    private String nombre;


    @Column(
        nullable = false,
        length = 255
    )
    private String descripcion;



    public Curso() {
    }


    public Curso(

            int id,
            String nombre,
            String descripcion) {


        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;

    }


    // GETTERS Y SETTERS

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


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(
            String descripcion) {

        this.descripcion = descripcion;
    }

}