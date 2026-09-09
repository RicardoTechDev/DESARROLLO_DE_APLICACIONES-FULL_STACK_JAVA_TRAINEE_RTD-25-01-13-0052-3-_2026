package cl.bootcamp.springedumanager_2.model;


import jakarta.persistence.Column;
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
 * CALIFICACION
 * =========================================================
 *
 * Esta entidad representa la NOTA
 * obtenida por un estudiante
 * en una evaluación determinada.
 *
 *
 * Ejemplo:
 *
 * Curso:
 * Java
 *
 * Evaluación:
 * Prueba 1
 *
 * Estudiante:
 * Juan Pérez
 *
 * Nota:
 * 6.2
 *
 *
 * Para saber qué estudiante recibió
 * la nota utilizaremos la Inscripcion.
 *
 *
 * Relación:
 *
 * Calificacion
 *      │
 *      ├── Evaluacion
 *      │
 *      └── Inscripcion
 *              │
 *              ├── Estudiante
 *              └── Curso
 */
@Entity


/*
 * Una combinación:
 *
 * evaluacion_id
 * +
 * inscripcion_id
 *
 * solamente puede existir una vez.
 *
 *
 * Esto evita tener:
 *
 * Juan
 * Prueba 1
 * 6.2
 *
 * y nuevamente:
 *
 * Juan
 * Prueba 1
 * 5.8
 *
 *
 * Si modificamos una nota,
 * actualizaremos el mismo registro.
 */
@Table(

    name = "calificaciones",

    uniqueConstraints = {

        @UniqueConstraint(

            name = "uk_calificacion_evaluacion_inscripcion",

            columnNames = {
                "evaluacion_id",
                "inscripcion_id"
            }

        )

    }

)
public class Calificacion {



    /*
     * =====================================================
     * ID
     * =====================================================
     */
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int id;



    /*
     * =====================================================
     * EVALUACIÓN
     * =====================================================
     *
     * Muchas calificaciones pueden
     * pertenecer a una misma evaluación.
     *
     *
     * Ejemplo:
     *
     * Prueba 1
     *
     * Juan  → 6.2
     * Ana   → 5.8
     * Pedro → 6.5
     */
    @ManyToOne
    @JoinColumn(
        name = "evaluacion_id",
        nullable = false
    )
    private Evaluacion evaluacion;



    /*
     * =====================================================
     * INSCRIPCIÓN
     * =====================================================
     *
     * Utilizamos Inscripcion porque
     * representa al estudiante
     * dentro de determinado curso.
     *
     *
     * Inscripcion
     *      │
     *      ├── Estudiante
     *      └── Curso
     */
    @ManyToOne
    @JoinColumn(
        name = "inscripcion_id",
        nullable = false
    )
    private Inscripcion inscripcion;



    /*
     * =====================================================
     * NOTA
     * =====================================================
     *
     * Utilizamos Double y NO double.
     *
     *
     * ¿Por qué?
     *
     * Double permite:
     *
     * null
     *
     * Esto nos permite representar:
     *
     * "todavía no tiene nota".
     *
     *
     * En Chile trabajaremos
     * con notas desde:
     *
     * 1.0
     *
     * hasta:
     *
     * 7.0
     */
    @Column
    private Double nota;



    /*
     * Constructor vacío requerido por JPA.
     */
    public Calificacion() {

    }



    /*
     * Constructor opcional.
     */
    public Calificacion(

            int id,

            Evaluacion evaluacion,

            Inscripcion inscripcion,

            Double nota) {


        this.id = id;

        this.evaluacion = evaluacion;

        this.inscripcion = inscripcion;

        this.nota = nota;

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



    public Evaluacion getEvaluacion() {

        return evaluacion;

    }


    public void setEvaluacion(
            Evaluacion evaluacion) {

        this.evaluacion = evaluacion;

    }



    public Inscripcion getInscripcion() {

        return inscripcion;

    }


    public void setInscripcion(
            Inscripcion inscripcion) {

        this.inscripcion = inscripcion;

    }



    public Double getNota() {

        return nota;

    }


    public void setNota(
            Double nota) {

        this.nota = nota;

    }

}