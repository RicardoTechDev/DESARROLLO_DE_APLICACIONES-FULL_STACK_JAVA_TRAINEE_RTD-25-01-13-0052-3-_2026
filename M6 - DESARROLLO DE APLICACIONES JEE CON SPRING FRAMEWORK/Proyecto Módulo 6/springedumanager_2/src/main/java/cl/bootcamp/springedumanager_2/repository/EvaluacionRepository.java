package cl.bootcamp.springedumanager_2.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.bootcamp.springedumanager_2.model.Evaluacion;



/*
 * =========================================================
 * EVALUACION REPOSITORY
 * =========================================================
 *
 * @Repository
 *
 * Marca esta interfaz como un componente
 * perteneciente a la capa de acceso a datos.
 *
 *
 * Nuestra arquitectura:
 *
 * Controller
 *      ↓
 * Service
 *      ↓
 * Repository
 *      ↓
 * JPA / Hibernate
 *      ↓
 * MySQL
 *
 *
 * Al extender:
 *
 * JpaRepository<Evaluacion, Integer>
 *
 * obtenemos automáticamente métodos como:
 *
 * findAll()
 * findById()
 * save()
 * deleteById()
 * existsById()
 *
 * sin tener que programarlos.
 */
@Repository
public interface EvaluacionRepository
        extends JpaRepository<Evaluacion, Integer> {



    /*
     * =====================================================
     * LISTAR EVALUACIONES DE UN CURSO
     * =====================================================
     *
     * Recupera todas las evaluaciones
     * correspondientes a determinado curso.
     *
     *
     * Spring interpreta:
     *
     * find
     *
     *      → buscar registros.
     *
     *
     * ByCurso_Id
     *
     *      → navegar desde:
     *
     *      Evaluacion
     *          ↓
     *      curso
     *          ↓
     *      id
     *
     *
     * OrderByNombreAsc
     *
     *      → ordenar los resultados
     *        por nombre de forma ascendente.
     *
     *
     * Ejemplo:
     *
     * Curso Java
     *
     * Examen
     * Proyecto
     * Prueba 1
     */
    List<Evaluacion>
    findByCurso_IdOrderByNombreAsc(
            int cursoId
    );



    /*
     * =====================================================
     * ¿EL CURSO TIENE EVALUACIONES?
     * =====================================================
     *
     * Este método será utilizado,
     * por ejemplo, cuando intentemos
     * eliminar un curso.
     *
     *
     * Pregunta:
     *
     * ¿Existe al menos una evaluación
     * correspondiente al curso indicado?
     *
     *
     * Spring navega:
     *
     * Evaluacion
     *      ↓
     * Curso
     *      ↓
     * id
     */
    boolean existsByCurso_Id(
            int cursoId
    );



    /*
     * =====================================================
     * VALIDAR NOMBRE AL CREAR EVALUACIÓN
     * =====================================================
     *
     * Queremos evitar que dentro
     * de un mismo curso existan:
     *
     * Prueba 1
     * Prueba 1
     *
     *
     * Pero perfectamente puede existir:
     *
     * Java
     *      → Prueba 1
     *
     * Python
     *      → Prueba 1
     *
     *
     * porque pertenecen a cursos diferentes.
     *
     *
     * Spring interpreta:
     *
     * exists
     *      → comprobar existencia.
     *
     * ByCurso_Id
     *      → curso seleccionado.
     *
     * And
     *      → segunda condición.
     *
     * NombreIgnoreCase
     *      → compara el nombre ignorando
     *        mayúsculas y minúsculas.
     */
    boolean existsByCurso_IdAndNombreIgnoreCase(

            int cursoId,
            String nombre

    );



    /*
     * =====================================================
     * VALIDAR NOMBRE AL EDITAR EVALUACIÓN
     * =====================================================
     *
     * Similar al caso anterior,
     * pero excluyendo la evaluación
     * que estamos editando.
     *
     *
     * Ejemplo:
     *
     * ID 1
     * Curso Java
     * Prueba 1
     *
     *
     * Si editamos esa misma evaluación
     * debemos permitir conservar:
     *
     * Prueba 1
     *
     *
     * Por eso preguntamos:
     *
     * ¿Existe otra evaluación llamada
     * Prueba 1 en Java cuyo ID
     * sea diferente de 1?
     */
    boolean existsByCurso_IdAndNombreIgnoreCaseAndIdNot(

            int cursoId,
            String nombre,
            int id

    );



    /*
     * =====================================================
     * SUMAR PONDERACIONES DE UN CURSO
     * =====================================================
     *
     * Aquí utilizamos @Query porque necesitamos
     * realizar una operación de agregación:
     *
     * SUM()
     *
     *
     * Queremos saber cuánto porcentaje
     * ya está utilizado por las evaluaciones
     * de determinado curso.
     *
     *
     * Ejemplo:
     *
     * Java
     *
     * Prueba 1   → 30%
     * Proyecto   → 40%
     *
     *
     * SUM:
     *
     * 70%
     *
     *
     * Entonces solamente podremos agregar
     * hasta un 30% adicional.
     *
     *
     * IMPORTANTE:
     *
     * Esto es JPQL.
     *
     * Por eso usamos:
     *
     * Evaluacion
     *
     * y:
     *
     * e.curso.id
     *
     * en lugar de trabajar directamente
     * con tablas físicas de MySQL.
     */
    @Query("""
        SELECT COALESCE(SUM(e.ponderacion), 0)
        FROM Evaluacion e
        WHERE e.curso.id = :cursoId
    """)
    Double sumarPonderacionPorCurso(

            @Param("cursoId")
            int cursoId

    );



    /*
     * =====================================================
     * SUMAR PONDERACIONES DURANTE UPDATE
     * =====================================================
     *
     * Cuando estamos editando una evaluación,
     * debemos excluir su ponderación actual.
     *
     *
     * Ejemplo:
     *
     * Java:
     *
     * ID 1 - Prueba      → 30%
     * ID 2 - Proyecto    → 40%
     * ID 3 - Examen      → 30%
     *
     *
     * Estamos editando:
     *
     * ID 1
     *
     *
     * Para calcular cuánto ocupan LAS OTRAS
     * evaluaciones debemos excluir:
     *
     * ID 1.
     *
     *
     * Entonces:
     *
     * 40 + 30
     *
     * = 70
     *
     *
     * Podemos cambiar Prueba hasta un máximo
     * de 30%.
     */
    @Query("""
        SELECT COALESCE(SUM(e.ponderacion), 0)
        FROM Evaluacion e
        WHERE e.curso.id = :cursoId
        AND e.id <> :evaluacionId
    """)
    Double sumarPonderacionPorCursoExcluyendoEvaluacion(

            @Param("cursoId")
            int cursoId,

            @Param("evaluacionId")
            int evaluacionId

    );

}