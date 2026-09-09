package cl.bootcamp.springedumanager_2.service;


import java.util.List;

import org.springframework.stereotype.Service;

import cl.bootcamp.springedumanager_2.exception.ReglaNegocioException;
import cl.bootcamp.springedumanager_2.model.Curso;
import cl.bootcamp.springedumanager_2.model.Evaluacion;
import cl.bootcamp.springedumanager_2.repository.CursoRepository;
import cl.bootcamp.springedumanager_2.repository.EvaluacionRepository;



/*
 * =========================================================
 * EVALUACION SERVICE
 * =========================================================
 *
 * @Service
 *
 * Esta clase pertenece a la capa
 * de lógica y reglas de negocio.
 *
 *
 * El Controller NO debería decidir:
 *
 * - si la ponderación es válida;
 *
 * - si el curso existe;
 *
 * - si existe otra evaluación
 *   con el mismo nombre;
 *
 * - si la suma supera el 100%.
 *
 *
 * Todas esas decisiones pertenecen
 * a la capa Service.
 *
 *
 * Flujo:
 *
 * Controller
 *      ↓
 * EvaluacionService
 *      ↓
 * EvaluacionRepository
 *      ↓
 * JPA / Hibernate
 *      ↓
 * MySQL
 */
@Service
public class EvaluacionService {



    /*
     * Repository principal
     * correspondiente a Evaluacion.
     */
    private final EvaluacionRepository
            evaluacionRepository;



    /*
     * Necesitamos CursoRepository
     * porque antes de crear una evaluación
     * debemos comprobar que el curso
     * seleccionado realmente exista.
     */
    private final CursoRepository
            cursoRepository;



    /*
     * =====================================================
     * INYECCIÓN DE DEPENDENCIAS
     * =====================================================
     */
    public EvaluacionService(

            EvaluacionRepository
            evaluacionRepository,

            CursoRepository
            cursoRepository) {


        this.evaluacionRepository =
                evaluacionRepository;


        this.cursoRepository =
                cursoRepository;

    }



    /*
     * =====================================================
     * LISTAR TODAS LAS EVALUACIONES
     * =====================================================
     */
    public List<Evaluacion> listar() {


        return evaluacionRepository
                .findAll();

    }



    /*
     * =====================================================
     * LISTAR EVALUACIONES POR CURSO
     * =====================================================
     *
     * Este método será especialmente útil
     * cuando posteriormente trabajemos
     * con calificaciones.
     *
     *
     * Flujo:
     *
     * Seleccionar Curso Java
     *
     *          ↓
     *
     * recuperar:
     *
     * Prueba 1
     * Proyecto
     * Examen
     */
    public List<Evaluacion> listarPorCurso(
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



        return evaluacionRepository
                .findByCurso_IdOrderByNombreAsc(
                    cursoId
                );

    }



    /*
     * =====================================================
     * BUSCAR EVALUACIÓN POR ID
     * =====================================================
     */
    public Evaluacion buscarPorId(
            int id) {


        return evaluacionRepository
                .findById(id)

                .orElseThrow(() ->

                    new ReglaNegocioException(

                        "La evaluación no existe."

                    )

                );

    }



    /*
     * =====================================================
     * GUARDAR / ACTUALIZAR
     * =====================================================
     *
     * Recibimos:
     *
     * id
     *
     * nombre
     *
     * ponderacion
     *
     * cursoId
     *
     *
     * El mismo método permite:
     *
     * CREATE
     *
     * y
     *
     * UPDATE
     */
    public Evaluacion guardar(

            int id,

            String nombre,

            double ponderacion,

            int cursoId) {



        /*
         * =================================================
         * VALIDACIÓN 1
         * NOMBRE OBLIGATORIO
         * =================================================
         */
        if (nombre == null ||
            nombre.trim().isEmpty()) {


            throw new ReglaNegocioException(

                "Debe ingresar el nombre de la evaluación."

            );

        }



        /*
         * Eliminamos espacios adicionales
         * al principio y al final.
         */
        nombre = nombre.trim();



        /*
         * =================================================
         * VALIDACIÓN 2
         * PONDERACIÓN
         * =================================================
         *
         * No permitimos:
         *
         * 0%
         *
         * valores negativos
         *
         * ni superiores al 100%.
         */
        if (ponderacion <= 0 ||
            ponderacion > 100) {


            throw new ReglaNegocioException(

                "La ponderación debe ser mayor que 0 "
                + "y no puede superar el 100%."

            );

        }



        /*
         * =================================================
         * VALIDACIÓN 3
         * CURSO
         * =================================================
         *
         * Debemos recuperar el objeto Curso
         * correspondiente al ID recibido.
         *
         *
         * No guardamos solamente:
         *
         * cursoId
         *
         *
         * Nuestra Evaluacion contiene:
         *
         * private Curso curso;
         *
         *
         * por eso necesitamos recuperar
         * la entidad completa.
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
         * CREATE
         * =================================================
         *
         * id == 0
         *
         * significa que la evaluación
         * todavía no existe.
         */
        if (id == 0) {



            /*
             * VALIDAR NOMBRE DUPLICADO
             *
             * No permitimos dos evaluaciones
             * con el mismo nombre
             * dentro del mismo curso.
             */
            if (evaluacionRepository
                    .existsByCurso_IdAndNombreIgnoreCase(

                        cursoId,
                        nombre

                    )) {


                throw new ReglaNegocioException(

                    "Ya existe una evaluación con ese nombre "
                    + "en el curso seleccionado."

                );

            }



            /*
             * Calculamos cuánto porcentaje
             * ya está utilizado por
             * las evaluaciones del curso.
             */
            double ponderacionActual =
                evaluacionRepository
                    .sumarPonderacionPorCurso(
                        cursoId
                    );



            /*
             * Ejemplo:
             *
             * actual = 80%
             *
             * nueva = 30%
             *
             * total = 110%
             *
             *      ↓
             *
             * ERROR
             */
            if (ponderacionActual +
                ponderacion > 100) {


                throw new ReglaNegocioException(

                    "No se puede registrar la evaluación. "
                    + "La suma de las ponderaciones del curso "
                    + "no puede superar el 100%."

                );

            }



            /*
             * Creamos el nuevo objeto.
             */
            Evaluacion evaluacion =
                    new Evaluacion();


            evaluacion.setNombre(
                    nombre
            );


            evaluacion.setPonderacion(
                    ponderacion
            );


            evaluacion.setCurso(
                    curso
            );



            /*
             * save() realizará un INSERT.
             */
            return evaluacionRepository
                    .save(evaluacion);

        }



        /*
         * =================================================
         * UPDATE
         * =================================================
         *
         * Si ID es diferente de 0
         * estamos modificando una evaluación.
         */
        else {



            /*
             * Primero verificamos
             * que la evaluación exista.
             */
            Evaluacion evaluacion =
                    buscarPorId(id);



            /*
             * Verificamos que no exista
             * OTRA evaluación con:
             *
             * mismo nombre
             *
             * mismo curso
             *
             * diferente ID.
             */
            if (evaluacionRepository
                    .existsByCurso_IdAndNombreIgnoreCaseAndIdNot(

                        cursoId,
                        nombre,
                        id

                    )) {


                throw new ReglaNegocioException(

                    "Ya existe otra evaluación con ese nombre "
                    + "en el curso seleccionado."

                );

            }



            /*
             * =================================================
             * VALIDAR PONDERACIÓN DURANTE UPDATE
             * =================================================
             *
             * Debemos sumar las otras evaluaciones,
             * excluyendo la evaluación actual.
             *
             *
             * Ejemplo:
             *
             * Prueba ID 1       30%
             * Proyecto ID 2     40%
             * Examen ID 3       30%
             *
             *
             * Editamos ID 1.
             *
             * Primero sumamos:
             *
             * 40 + 30
             *
             * = 70
             *
             *
             * Entonces Prueba podrá tener
             * como máximo:
             *
             * 30%.
             */
            double ponderacionOtras =
                evaluacionRepository
                    .sumarPonderacionPorCursoExcluyendoEvaluacion(

                        cursoId,
                        id

                    );



            if (ponderacionOtras +
                ponderacion > 100) {


                throw new ReglaNegocioException(

                    "No se puede actualizar la evaluación. "
                    + "La suma de las ponderaciones del curso "
                    + "no puede superar el 100%."

                );

            }



            /*
             * Actualizamos los datos.
             */
            evaluacion.setNombre(
                    nombre
            );


            evaluacion.setPonderacion(
                    ponderacion
            );


            evaluacion.setCurso(
                    curso
            );



            /*
             * Como tiene un ID existente,
             * save() realizará conceptualmente:
             *
             * UPDATE
             */
            return evaluacionRepository
                    .save(evaluacion);

        }

    }



    /*
     * =====================================================
     * ELIMINAR EVALUACIÓN
     * =====================================================
     *
     * POR AHORA verificamos solamente
     * que la evaluación exista.
     *
     *
     * IMPORTANTE:
     *
     * Cuando creemos:
     *
     * CalificacionRepository
     *
     * vamos a agregar una regla adicional:
     *
     * NO permitir eliminar una evaluación
     * que ya tenga calificaciones.
     */
    public void eliminar(
            int id) {



        /*
         * Verificamos existencia.
         */
        if (!evaluacionRepository
                .existsById(id)) {


            throw new ReglaNegocioException(

                "La evaluación que intenta eliminar no existe."

            );

        }



        /*
         * Por ahora podemos eliminar.
         *
         * Más adelante agregaremos aquí:
         *
         * if (calificacionRepository
         *         .existsByEvaluacion_Id(id)) {
         *
         *     throw new ReglaNegocioException(
         *         "No se puede eliminar..."
         *     );
         * }
         */
        evaluacionRepository
                .deleteById(id);

    }

}