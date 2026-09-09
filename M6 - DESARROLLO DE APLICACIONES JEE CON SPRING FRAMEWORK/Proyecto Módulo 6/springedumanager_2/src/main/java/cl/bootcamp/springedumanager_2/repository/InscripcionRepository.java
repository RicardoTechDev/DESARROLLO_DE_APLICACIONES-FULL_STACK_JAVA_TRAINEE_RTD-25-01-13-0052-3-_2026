package cl.bootcamp.springedumanager_2.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.bootcamp.springedumanager_2.model.Inscripcion;



/*
 * =========================================================
 * INSCRIPCION REPOSITORY
 * =========================================================
 *
 * Esta interfaz pertenece a la capa
 * de acceso a datos.
 *
 *
 * Al extender:
 *
 * JpaRepository<Inscripcion, Integer>
 *
 * obtenemos automáticamente:
 *
 * findAll()
 *
 * findById()
 *
 * save()
 *
 * deleteById()
 *
 * existsById()
 *
 * entre otros.
 */
@Repository
public interface InscripcionRepository
        extends JpaRepository<Inscripcion, Integer> {



    /*
     * =====================================================
     * VALIDAR INSCRIPCIÓN DUPLICADA
     * =====================================================
     *
     * Permite preguntar:
     *
     * ¿Este estudiante ya está inscrito
     * en este curso?
     *
     *
     * Spring interpreta:
     *
     * exists
     *
     *      → verificar existencia.
     *
     *
     * ByEstudiante_Id
     *
     *      → navegar:
     *
     *      Inscripcion
     *          ↓
     *      estudiante
     *          ↓
     *      id
     *
     *
     * And
     *
     *      → agrega otra condición.
     *
     *
     * Curso_Id
     *
     *      → navegar:
     *
     *      Inscripcion
     *          ↓
     *      curso
     *          ↓
     *      id
     *
     *
     * Ejemplo:
     *
     * estudianteId = 1
     *
     * cursoId = 2
     *
     *
     * pregunta:
     *
     * ¿Existe una inscripción
     * estudiante 1 + curso 2?
     */
    boolean existsByEstudiante_IdAndCurso_Id(

            int estudianteId,

            int cursoId

    );



    /*
     * =====================================================
     * ¿EL ESTUDIANTE TIENE INSCRIPCIONES?
     * =====================================================
     *
     * Este método ya lo estamos utilizando
     * en EstudianteService.
     *
     *
     * Nos permite impedir eliminar
     * un estudiante que se encuentra
     * inscrito en uno o más cursos.
     */
    boolean existsByEstudiante_Id(

            int estudianteId

    );



    /*
     * =====================================================
     * ¿EL CURSO TIENE ESTUDIANTES INSCRITOS?
     * =====================================================
     *
     * Este método lo utiliza CursoService.
     *
     *
     * Nos permite impedir eliminar
     * un curso mientras tenga
     * estudiantes inscritos.
     */
    boolean existsByCurso_Id(

            int cursoId

    );



    /*
     * =====================================================
     * LISTAR ESTUDIANTES INSCRITOS EN UN CURSO
     * =====================================================
     *
     * Este será uno de los métodos
     * MÁS IMPORTANTES posteriormente.
     *
     *
     * Ejemplo:
     *
     * Curso Java
     *
     *      ↓
     *
     * buscamos todas sus inscripciones
     *
     *      ↓
     *
     * Juan Pérez
     * Ana Salazar
     * Pedro Soto
     *
     *
     * Además:
     *
     * OrderByEstudiante_NombreAsc
     *
     * ordenará los resultados
     * por el nombre del estudiante.
     *
     *
     * Este método será utilizado
     * cuando lleguemos a:
     *
     * Calificaciones.
     */
    List<Inscripcion>
    findByCurso_IdOrderByEstudiante_NombreAsc(

            int cursoId

    );



    /*
     * =====================================================
     * LISTAR CURSOS DE UN ESTUDIANTE
     * =====================================================
     *
     * Permite hacer la consulta inversa:
     *
     * Estudiante Juan Pérez
     *
     *      ↓
     *
     * ¿En qué cursos está inscrito?
     *
     *      ↓
     *
     * Java
     * Python
     * Spring Boot
     *
     *
     * Este método será útil posteriormente
     * para el portal del estudiante.
     */
    List<Inscripcion>
    findByEstudiante_IdOrderByCurso_NombreAsc(

            int estudianteId

    );

}