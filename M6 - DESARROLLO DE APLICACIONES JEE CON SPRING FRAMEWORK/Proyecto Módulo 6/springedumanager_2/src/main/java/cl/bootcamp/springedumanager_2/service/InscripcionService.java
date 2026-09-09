package cl.bootcamp.springedumanager_2.service;


import java.util.List;

import org.springframework.stereotype.Service;

import cl.bootcamp.springedumanager_2.exception.ReglaNegocioException;
import cl.bootcamp.springedumanager_2.model.Curso;
import cl.bootcamp.springedumanager_2.model.Estudiante;
import cl.bootcamp.springedumanager_2.model.Inscripcion;
import cl.bootcamp.springedumanager_2.repository.CursoRepository;
import cl.bootcamp.springedumanager_2.repository.EstudianteRepository;
import cl.bootcamp.springedumanager_2.repository.InscripcionRepository;



/*
 * =========================================================
 * INSCRIPCION SERVICE
 * =========================================================
 *
 * Esta clase contiene las reglas
 * de negocio relacionadas
 * con las inscripciones.
 *
 *
 * El Controller NO debería decidir:
 *
 * - si el estudiante existe;
 *
 * - si el curso existe;
 *
 * - si ya está inscrito;
 *
 * - si la inscripción puede eliminarse.
 *
 *
 * Esas decisiones pertenecen
 * a la capa Service.
 */
@Service
public class InscripcionService {



    /*
     * Repository principal
     * correspondiente a Inscripcion.
     */
    private final InscripcionRepository
            inscripcionRepository;



    /*
     * Necesitamos EstudianteRepository
     * para recuperar el estudiante
     * seleccionado.
     */
    private final EstudianteRepository
            estudianteRepository;



    /*
     * Necesitamos CursoRepository
     * para recuperar el curso
     * seleccionado.
     */
    private final CursoRepository
            cursoRepository;



    /*
     * =====================================================
     * INYECCIÓN DE DEPENDENCIAS
     * =====================================================
     */
    public InscripcionService(

            InscripcionRepository
            inscripcionRepository,

            EstudianteRepository
            estudianteRepository,

            CursoRepository
            cursoRepository) {


        this.inscripcionRepository =
                inscripcionRepository;


        this.estudianteRepository =
                estudianteRepository;


        this.cursoRepository =
                cursoRepository;

    }



    /*
     * =====================================================
     * LISTAR TODAS LAS INSCRIPCIONES
     * =====================================================
     */
    public List<Inscripcion> listar() {


        return inscripcionRepository
                .findAll();

    }



    /*
     * =====================================================
     * LISTAR INSCRIPCIONES POR CURSO
     * =====================================================
     *
     * Este método será muy importante
     * posteriormente.
     *
     *
     * Ejemplo:
     *
     * cursoId = 1
     *
     *      ↓
     *
     * Java
     *
     *      ↓
     *
     * recuperamos:
     *
     * Juan Pérez
     * Ana Salazar
     * Pedro Soto
     */
    public List<Inscripcion> listarPorCurso(
            int cursoId) {



        /*
         * Antes verificamos que
         * el curso realmente exista.
         */
        if (!cursoRepository
                .existsById(cursoId)) {


            throw new ReglaNegocioException(

                "El curso seleccionado no existe."

            );

        }



        return inscripcionRepository
                .findByCurso_IdOrderByEstudiante_NombreAsc(

                    cursoId

                );

    }



    /*
     * =====================================================
     * LISTAR INSCRIPCIONES POR ESTUDIANTE
     * =====================================================
     *
     * Permite conocer los cursos
     * de determinado estudiante.
     */
    public List<Inscripcion> listarPorEstudiante(
            int estudianteId) {



        if (!estudianteRepository
                .existsById(estudianteId)) {


            throw new ReglaNegocioException(

                "El estudiante seleccionado no existe."

            );

        }



        return inscripcionRepository
                .findByEstudiante_IdOrderByCurso_NombreAsc(

                    estudianteId

                );

    }



    /*
     * =====================================================
     * INSCRIBIR ESTUDIANTE
     * =====================================================
     *
     * Este método recibe:
     *
     * estudianteId
     *
     * cursoId
     *
     *
     * Ejemplo:
     *
     * estudianteId = 3
     *
     * cursoId = 1
     *
     *
     * Después recuperamos las entidades:
     *
     * Estudiante
     *
     * Curso
     *
     * y construimos la inscripción.
     */
    public Inscripcion inscribir(

            int estudianteId,

            int cursoId) {



        /*
         * =================================================
         * VALIDACIÓN 1
         * ESTUDIANTE EXISTENTE
         * =================================================
         *
         * No podemos inscribir
         * un estudiante inexistente.
         */
        Estudiante estudiante =
            estudianteRepository
                .findById(estudianteId)

                .orElseThrow(() ->

                    new ReglaNegocioException(

                        "El estudiante seleccionado no existe."

                    )

                );



        /*
         * =================================================
         * VALIDACIÓN 2
         * CURSO EXISTENTE
         * =================================================
         */
        Curso curso =
            cursoRepository
                .findById(cursoId)

                .orElseThrow(() ->

                    new ReglaNegocioException(

                        "El curso seleccionado no existe."

                    )

                );



        /*
         * =================================================
         * VALIDACIÓN 3
         * INSCRIPCIÓN DUPLICADA
         * =================================================
         *
         * Preguntamos si ya existe:
         *
         * estudiante + curso.
         *
         *
         * Ejemplo:
         *
         * Juan Pérez
         *      ↓
         * Java
         *
         *
         * Si ya existe,
         * no debemos volver a crearlo.
         */
        if (inscripcionRepository
                .existsByEstudiante_IdAndCurso_Id(

                    estudianteId,

                    cursoId

                )) {


            throw new ReglaNegocioException(

                "El estudiante ya se encuentra "
                + "inscrito en este curso."

            );

        }



        /*
         * =================================================
         * CREAR INSCRIPCIÓN
         * =================================================
         */
        Inscripcion inscripcion =
                new Inscripcion();



        /*
         * Asociamos el estudiante.
         */
        inscripcion.setEstudiante(
                estudiante
        );



        /*
         * Asociamos el curso.
         */
        inscripcion.setCurso(
                curso
        );



        /*
         * save() realizará el INSERT.
         */
        return inscripcionRepository
                .save(inscripcion);

    }



    /*
     * =====================================================
     * ELIMINAR INSCRIPCIÓN
     * =====================================================
     *
     * POR AHORA solamente comprobaremos
     * que la inscripción exista.
     *
     *
     * IMPORTANTE:
     *
     * Cuando creemos CalificacionRepository
     * agregaremos aquí la validación:
     *
     * NO eliminar una inscripción
     * si tiene calificaciones.
     */
    public void eliminar(
            int id) {



        /*
         * Verificamos que exista.
         */
        if (!inscripcionRepository
                .existsById(id)) {


            throw new ReglaNegocioException(

                "La inscripción que intenta eliminar no existe."

            );

        }



        /*
         * Por ahora eliminamos.
         *
         *
         * Posteriormente tendremos:
         *
         * if (calificacionRepository
         *        .existsByInscripcion_Id(id)) {
         *
         *     throw new ReglaNegocioException(
         *         "No se puede eliminar..."
         *     );
         *
         * }
         */
        inscripcionRepository
                .deleteById(id);

    }

}