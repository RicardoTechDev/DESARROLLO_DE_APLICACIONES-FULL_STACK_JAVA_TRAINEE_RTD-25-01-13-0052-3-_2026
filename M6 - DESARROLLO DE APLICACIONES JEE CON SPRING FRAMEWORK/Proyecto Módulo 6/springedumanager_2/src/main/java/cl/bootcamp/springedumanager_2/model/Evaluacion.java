package cl.bootcamp.springedumanager_2.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/*
 * =========================================================
 * EVALUACION
 * =========================================================
 *
 * @Entity
 *
 * Indica que esta clase será administrada
 * por JPA y que sus objetos podrán ser
 * almacenados en la base de datos.
 *
 *
 * IMPORTANTE:
 *
 * Una Evaluacion representa una actividad
 * evaluativa correspondiente a un curso.
 *
 * Ejemplos:
 *
 * Curso Java
 *
 *      Prueba 1      → 30%
 *      Proyecto      → 40%
 *      Examen Final  → 30%
 *
 *
 * La nota obtenida por cada estudiante
 * NO estará dentro de esta entidad.
 *
 * Las notas serán almacenadas posteriormente
 * en la entidad:
 *
 * Calificacion
 */
@Entity


/*
 * Indicamos explícitamente
 * el nombre de la tabla.
 */
@Table(name = "evaluaciones")

public class Evaluacion {



    /*
     * =====================================================
     * ID
     * =====================================================
     *
     * @Id
     *
     * indica que este atributo
     * es la PRIMARY KEY.
     */
    @Id


    /*
     * GenerationType.IDENTITY
     *
     * permite utilizar el AUTO_INCREMENT
     * de MySQL.
     */
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int id;



    /*
     * =====================================================
     * NOMBRE
     * =====================================================
     *
     * Nombre de la evaluación.
     *
     * Ejemplos:
     *
     * Prueba 1
     * Proyecto Final
     * Examen
     */
    @Column(
        nullable = false,
        length = 100
    )
    private String nombre;



    /*
     * =====================================================
     * PONDERACIÓN
     * =====================================================
     *
     * Representa el porcentaje que tendrá
     * esta evaluación dentro del curso.
     *
     * Ejemplo:
     *
     * 30
     *
     * significa:
     *
     * 30%
     */
    @Column(
        nullable = false
    )
    private double ponderacion;



    /*
     * =====================================================
     * RELACIÓN CON CURSO
     * =====================================================
     *
     * @ManyToOne
     *
     * Muchas evaluaciones pueden pertenecer
     * a un mismo curso.
     *
     *
     * Ejemplo:
     *
     *                 Curso Java
     *                     │
     *            ┌────────┼─────────┐
     *            │        │         │
     *         Prueba   Proyecto   Examen
     *
     *
     * Por eso la relación es:
     *
     * Muchas Evaluaciones
     *          ↓
     * Un Curso
     */
    @ManyToOne


    /*
     * @JoinColumn
     *
     * indica cuál será la columna
     * que almacenará la FOREIGN KEY
     * dentro de la tabla evaluaciones.
     *
     *
     * MySQL tendrá:
     *
     * curso_id
     */
    @JoinColumn(
        name = "curso_id",
        nullable = false
    )
    private Curso curso;



    /*
     * Constructor vacío requerido por JPA.
     */
    public Evaluacion() {
    }



    /*
     * Constructor opcional.
     */
    public Evaluacion(

            int id,
            String nombre,
            double ponderacion,
            Curso curso) {


        this.id = id;
        this.nombre = nombre;
        this.ponderacion = ponderacion;
        this.curso = curso;

    }



    /*
     * =====================================================
     * GETTERS Y SETTERS
     * =====================================================
     */

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(
            String nombre) {

        this.nombre = nombre;
    }


    public double getPonderacion() {
        return ponderacion;
    }


    public void setPonderacion(
            double ponderacion) {

        this.ponderacion = ponderacion;
    }


    public Curso getCurso() {
        return curso;
    }


    public void setCurso(
            Curso curso) {

        this.curso = curso;
    }

}