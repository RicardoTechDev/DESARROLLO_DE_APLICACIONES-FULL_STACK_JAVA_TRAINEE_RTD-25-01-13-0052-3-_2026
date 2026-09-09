package cl.bootcamp.springedumanager_2.service;


import java.util.List;

import org.springframework.stereotype.Service;

import cl.bootcamp.springedumanager_2.exception.ReglaNegocioException;
import cl.bootcamp.springedumanager_2.model.Curso;
import cl.bootcamp.springedumanager_2.repository.CursoRepository;
import cl.bootcamp.springedumanager_2.repository.EvaluacionRepository;
import cl.bootcamp.springedumanager_2.repository.InscripcionRepository;


/*
 * @Service
 *
 * Indica que esta clase pertenece
 * a la capa Service.
 *
 * Esta capa contiene principalmente
 * la lógica y las reglas de negocio
 * relacionadas con los cursos.
 *
 *
 * Nuestra arquitectura será:
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
 * El Controller recibe las solicitudes HTTP,
 * pero será el Service quien decida si una
 * operación puede o no realizarse.
 */
@Service
public class CursoService {



    /*
     * =====================================================
     * REPOSITORIES
     * =====================================================
     */



    /*
     * CursoRepository
     *
     * Permite realizar operaciones directamente
     * relacionadas con la entidad Curso.
     *
     * Algunos métodos que utilizaremos:
     *
     * findAll()
     *
     * findById()
     *
     * save()
     *
     * existsById()
     *
     * deleteById()
     *
     *
     * Además contiene nuestros métodos personalizados:
     *
     * existsByNombreIgnoreCase()
     *
     * existsByNombreIgnoreCaseAndIdNot()
     */
    private final CursoRepository cursoRepository;



    /*
     * InscripcionRepository
     *
     * Lo utilizaremos para comprobar
     * si existen estudiantes inscritos
     * en determinado curso.
     *
     *
     * Relación:
     *
     * Curso
     *      ↓
     * Inscripcion
     *      ↓
     * Estudiante
     */
    private final InscripcionRepository inscripcionRepository;



    /*
     * EvaluacionRepository
     *
     * Lo utilizaremos para comprobar
     * si un curso tiene evaluaciones
     * registradas.
     *
     *
     * Relación:
     *
     * Curso
     *      ↓
     * Evaluacion
     */
    private final EvaluacionRepository evaluacionRepository;



    /*
     * =====================================================
     * INYECCIÓN DE DEPENDENCIAS
     * =====================================================
     *
     * Spring detecta automáticamente
     * estos Repository y nos entrega
     * sus implementaciones.
     *
     * No necesitamos realizar:
     *
     * new CursoRepository()
     *
     * new InscripcionRepository()
     *
     * etc.
     *
     * Spring se encargará de administrar
     * estos objetos.
     */
    public CursoService(

            CursoRepository cursoRepository,

            InscripcionRepository inscripcionRepository,

            EvaluacionRepository evaluacionRepository) {


        this.cursoRepository =
                cursoRepository;


        this.inscripcionRepository =
                inscripcionRepository;


        this.evaluacionRepository =
                evaluacionRepository;

    }



    /*
     * =====================================================
     * LISTAR
     * =====================================================
     *
     * Recupera todos los cursos
     * almacenados en la base de datos.
     *
     * findAll() viene incluido
     * automáticamente en JpaRepository.
     */
    public List<Curso> listar() {


        return cursoRepository
                .findAll();

    }



    /*
     * =====================================================
     * BUSCAR POR ID
     * =====================================================
     *
     * Busca un curso utilizando
     * su identificador.
     *
     *
     * findById() devuelve un Optional.
     *
     * Esto significa que:
     *
     * el curso puede existir
     *
     * o
     *
     * puede no existir.
     *
     *
     * Utilizamos:
     *
     * orElseThrow()
     *
     * para lanzar una excepción
     * cuando el curso no existe.
     */
    public Curso buscarPorId(
            int id) {


        return cursoRepository
                .findById(id)

                .orElseThrow(() ->

                    new ReglaNegocioException(

                        "El curso no existe."

                    )

                );

    }



    /*
     * =====================================================
     * GUARDAR / ACTUALIZAR
     * =====================================================
     *
     * Este mismo método se utilizará
     * para realizar:
     *
     * CREATE
     *
     * y
     *
     * UPDATE
     *
     *
     * JPA determinará qué operación
     * realizar según el ID.
     */
    public Curso guardar(
            Curso curso) {



        /*
         * =================================================
         * VALIDACIÓN 1
         * NOMBRE OBLIGATORIO
         * =================================================
         *
         * Verificamos que el nombre:
         *
         * - no sea null;
         *
         * - no esté vacío;
         *
         * - no contenga solamente espacios.
         */
        if (curso.getNombre() == null ||
            curso.getNombre().trim().isEmpty()) {


            throw new ReglaNegocioException(

                "Debe ingresar el nombre del curso."

            );

        }



        /*
         * =================================================
         * CREATE
         * =================================================
         *
         * Si:
         *
         * id == 0
         *
         * significa que el curso todavía
         * no se encuentra almacenado
         * en la base de datos.
         */
        if (curso.getId() == 0) {



            /*
             * Antes de crear verificamos
             * si ya existe otro curso
             * con el mismo nombre.
             *
             *
             * IgnoreCase permite considerar:
             *
             * Java
             *
             * JAVA
             *
             * java
             *
             * como el mismo nombre.
             */
            if (cursoRepository
                    .existsByNombreIgnoreCase(

                        curso.getNombre()

                    )) {


                throw new ReglaNegocioException(

                    "Ya existe un curso con ese nombre."

                );

            }

        }



        /*
         * =================================================
         * UPDATE
         * =================================================
         *
         * Si el ID es diferente de 0,
         * significa que estamos editando
         * un curso existente.
         */
        else {



            /*
             * Comprobamos que NO exista
             * OTRO curso con el mismo nombre.
             *
             *
             * Utilizamos:
             *
             * existsByNombreIgnoreCaseAndIdNot()
             *
             *
             * Ejemplo:
             *
             * ID 1 → Java
             *
             * Estamos editando Java.
             *
             * Debemos permitir que conserve
             * el nombre "Java".
             *
             *
             * Por eso preguntamos:
             *
             * ¿Existe Java con un ID
             * diferente de 1?
             */
            if (cursoRepository
                    .existsByNombreIgnoreCaseAndIdNot(

                        curso.getNombre(),

                        curso.getId()

                    )) {


                throw new ReglaNegocioException(

                    "Ya existe otro curso con ese nombre."

                );

            }

        }



        /*
         * =================================================
         * GUARDAR EN BASE DE DATOS
         * =================================================
         *
         * save() determinará automáticamente
         * qué operación realizar.
         *
         *
         * Curso nuevo:
         *
         * id = 0
         *
         *      ↓
         *
         * INSERT
         *
         *
         * Curso existente:
         *
         * id diferente de 0
         *
         *      ↓
         *
         * UPDATE
         */
        return cursoRepository
                .save(curso);

    }



    /*
     * =====================================================
     * ELIMINAR CURSO
     * =====================================================
     *
     * Ahora que trabajamos con una base de datos
     * relacionada, eliminar un curso no consiste
     * simplemente en borrar un registro.
     *
     * Antes debemos verificar distintas
     * reglas de negocio.
     *
     *
     * Por ejemplo:
     *
     * NO deberíamos eliminar:
     *
     * Curso Java
     *
     * si existen:
     *
     * - evaluaciones asociadas;
     *
     * - estudiantes inscritos.
     */
    public void eliminar(
            int id) {



        /*
         * =================================================
         * VALIDACIÓN 1
         * ¿EXISTE EL CURSO?
         * =================================================
         *
         * existsById() viene incorporado
         * automáticamente en JpaRepository.
         *
         * Si el curso no existe,
         * no tiene sentido intentar eliminarlo.
         */
        if (!cursoRepository
                .existsById(id)) {


            throw new ReglaNegocioException(

                "El curso que intenta eliminar no existe."

            );

        }



        /*
         * =================================================
         * VALIDACIÓN 2
         * ¿TIENE EVALUACIONES?
         * =================================================
         *
         * Nuestra relación es:
         *
         * Curso
         *      ↓
         * Evaluacion
         *
         *
         * Un curso puede tener múltiples
         * evaluaciones.
         *
         * Ejemplo:
         *
         * Java
         *
         * ├── Prueba 1
         * ├── Proyecto
         * └── Examen Final
         *
         *
         * Si existen evaluaciones asociadas,
         * no permitiremos eliminar el curso.
         */
        if (evaluacionRepository
                .existsByCurso_Id(
                    id
                )) {


            throw new ReglaNegocioException(

                "No se puede eliminar el curso "
                + "porque tiene evaluaciones registradas."

            );

        }



        /*
         * =================================================
         * VALIDACIÓN 3
         * ¿TIENE ESTUDIANTES INSCRITOS?
         * =================================================
         *
         * La relación es:
         *
         * Curso
         *      ↓
         * Inscripcion
         *      ↓
         * Estudiante
         *
         *
         * Ejemplo:
         *
         * Java
         *
         * ├── Juan Pérez
         * ├── Ana Salazar
         * └── Pedro Soto
         *
         *
         * Mientras existan inscripciones,
         * no permitiremos eliminar el curso.
         */
        if (inscripcionRepository
                .existsByCurso_Id(
                    id
                )) {


            throw new ReglaNegocioException(

                "No se puede eliminar el curso "
                + "porque tiene estudiantes inscritos."

            );

        }



        /*
         * =================================================
         * ELIMINAR
         * =================================================
         *
         * Si llegamos hasta este punto
         * significa que:
         *
         * ✅ el curso existe;
         *
         * ✅ no tiene evaluaciones;
         *
         * ✅ no tiene estudiantes inscritos.
         *
         *
         * Entonces podemos eliminarlo
         * de la base de datos.
         *
         *
         * deleteById() viene incorporado
         * en JpaRepository.
         */
        cursoRepository
                .deleteById(id);

    }

}