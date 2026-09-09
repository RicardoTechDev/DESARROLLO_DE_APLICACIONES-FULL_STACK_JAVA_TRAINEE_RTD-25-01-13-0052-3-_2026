package cl.bootcamp.springedumanager_2.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.bootcamp.springedumanager_2.model.Calificacion;


/*
 * =========================================================
 * CALIFICACION REPOSITORY
 * =========================================================
 *
 * Esta interfaz pertenece a la capa
 * de acceso a datos.
 *
 *
 * Al extender:
 *
 * JpaRepository<Calificacion, Integer>
 *
 * obtenemos automáticamente:
 *
 * findAll()
 * findById()
 * save()
 * delete()
 * deleteById()
 * existsById()
 */
@Repository
public interface CalificacionRepository
        extends JpaRepository<Calificacion, Integer> {



    /*
     * =====================================================
     * BUSCAR NOTA DE UNA INSCRIPCIÓN EN UNA EVALUACIÓN
     * =====================================================
     *
     * Pregunta:
     *
     * ¿Existe una calificación
     * para esta evaluación
     * y esta inscripción?
     *
     *
     * Ejemplo:
     *
     * Evaluación:
     * Prueba 1
     *
     * Inscripción:
     * Juan → Java
     *
     *
     * Nos permitirá saber si debemos:
     *
     * INSERT
     *
     * o:
     *
     * UPDATE.
     */
    Optional<Calificacion>
    findByEvaluacion_IdAndInscripcion_Id(

            int evaluacionId,

            int inscripcionId

    );



    /*
     * =====================================================
     * LISTAR NOTAS DE UNA EVALUACIÓN
     * =====================================================
     *
     * Ejemplo:
     *
     * Prueba 1
     *
     *      ↓
     *
     * Juan  6.2
     * Ana   5.5
     * Pedro 6.8
     */
    List<Calificacion>
    findByEvaluacion_Id(

            int evaluacionId

    );



    /*
     * =====================================================
     * ¿UNA EVALUACIÓN TIENE CALIFICACIONES?
     * =====================================================
     *
     * Lo utilizaremos antes
     * de eliminar una evaluación.
     */
    boolean existsByEvaluacion_Id(

            int evaluacionId

    );



    /*
     * =====================================================
     * ¿UNA INSCRIPCIÓN TIENE CALIFICACIONES?
     * =====================================================
     *
     * Lo utilizaremos antes
     * de eliminar una inscripción.
     */
    boolean existsByInscripcion_Id(

            int inscripcionId

    );



    /*
     * =====================================================
     * ¿UN ESTUDIANTE TIENE CALIFICACIONES?
     * =====================================================
     *
     * Spring navega:
     *
     * Calificacion
     *      ↓
     * Inscripcion
     *      ↓
     * Estudiante
     *      ↓
     * id
     *
     *
     * Este era precisamente el método
     * que estaba provocando error anteriormente
     * porque todavía no habíamos creado
     * CalificacionRepository.
     */
    boolean existsByInscripcion_Estudiante_Id(

            int estudianteId

    );



    /*
     * =====================================================
     * LISTAR NOTAS DE UN ESTUDIANTE
     * =====================================================
     *
     * Posteriormente será útil
     * para el portal del estudiante.
     *
     *
     * Ejemplo:
     *
     * Juan Pérez
     *
     * Java
     * Prueba 1    6.2
     * Proyecto    5.8
     *
     * Python
     * Prueba 1    6.5
     */
    List<Calificacion>
    findByInscripcion_Estudiante_Id(

            int estudianteId

    );

}