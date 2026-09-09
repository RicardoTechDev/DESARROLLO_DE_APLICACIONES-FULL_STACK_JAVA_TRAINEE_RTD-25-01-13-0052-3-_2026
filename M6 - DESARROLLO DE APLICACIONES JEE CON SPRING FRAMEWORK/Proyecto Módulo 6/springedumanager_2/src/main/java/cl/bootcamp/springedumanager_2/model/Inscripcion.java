package cl.bootcamp.springedumanager_2.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;



/*
 * =========================================================
 * INSCRIPCION
 * =========================================================
 *
 * Esta entidad representa la inscripción
 * de un estudiante en un curso.
 *
 *
 * Ejemplo:
 *
 * Estudiante:
 *
 * Juan Pérez
 *
 * Curso:
 *
 * Java
 *
 * Resultado:
 *
 * Juan Pérez → Java
 *
 *
 * La inscripción permite establecer
 * formalmente qué estudiantes pertenecen
 * a cada curso.
 *
 *
 * Esto será especialmente importante
 * cuando posteriormente queramos:
 *
 * - recuperar los estudiantes de un curso;
 *
 * - crear evaluaciones para un curso;
 *
 * - ingresar calificaciones;
 *
 * - consultar las notas de un estudiante.
 */
@Entity



/*
 * @Table
 *
 * Indicamos el nombre de la tabla
 * que se creará en MySQL.
 *
 *
 * uniqueConstraints
 *
 * agrega una restricción para evitar
 * que el mismo estudiante pueda estar
 * inscrito dos veces en el mismo curso.
 *
 *
 * Ejemplo:
 *
 * Juan → Java
 *
 * ✅ permitido una vez.
 *
 *
 * Juan → Java
 *
 * ❌ no debería repetirse.
 *
 *
 * La combinación:
 *
 * estudiante_id + curso_id
 *
 * debe ser única.
 */
@Table(

    name = "inscripciones",

    uniqueConstraints = {

        @UniqueConstraint(

            name = "uk_inscripcion_estudiante_curso",

            columnNames = {
                "estudiante_id",
                "curso_id"
            }

        )

    }

)

public class Inscripcion {



    /*
     * =====================================================
     * ID
     * =====================================================
     *
     * Clave primaria.
     */
    @Id


    /*
     * MySQL será responsable
     * de generar automáticamente
     * el ID.
     */
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int id;



    /*
     * =====================================================
     * ESTUDIANTE
     * =====================================================
     *
     * Muchas inscripciones pueden
     * pertenecer al mismo estudiante.
     *
     *
     * Ejemplo:
     *
     * Juan → Java
     *
     * Juan → Python
     *
     * Juan → Spring Boot
     *
     *
     * Por eso utilizamos:
     *
     * @ManyToOne
     */
    @ManyToOne


    /*
     * @JoinColumn
     *
     * indica que en la tabla
     * inscripciones tendremos una columna:
     *
     * estudiante_id
     *
     * que funcionará como FOREIGN KEY.
     */
    @JoinColumn(

        name = "estudiante_id",

        nullable = false

    )
    private Estudiante estudiante;



    /*
     * =====================================================
     * CURSO
     * =====================================================
     *
     * Muchas inscripciones pueden
     * pertenecer al mismo curso.
     *
     *
     * Ejemplo:
     *
     * Java ← Juan
     *
     * Java ← Ana
     *
     * Java ← Pedro
     *
     *
     * Por eso también utilizamos:
     *
     * @ManyToOne
     */
    @ManyToOne


    /*
     * MySQL tendrá:
     *
     * curso_id
     *
     * como FOREIGN KEY.
     */
    @JoinColumn(

        name = "curso_id",

        nullable = false

    )
    private Curso curso;



    /*
     * Constructor vacío.
     *
     * JPA necesita poder crear
     * objetos sin parámetros.
     */
    public Inscripcion() {

    }



    /*
     * Constructor opcional.
     */
    public Inscripcion(

            int id,

            Estudiante estudiante,

            Curso curso) {


        this.id = id;

        this.estudiante = estudiante;

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


    public void setId(
            int id) {

        this.id = id;

    }



    public Estudiante getEstudiante() {

        return estudiante;

    }


    public void setEstudiante(
            Estudiante estudiante) {

        this.estudiante = estudiante;

    }



    public Curso getCurso() {

        return curso;

    }


    public void setCurso(
            Curso curso) {

        this.curso = curso;

    }

}