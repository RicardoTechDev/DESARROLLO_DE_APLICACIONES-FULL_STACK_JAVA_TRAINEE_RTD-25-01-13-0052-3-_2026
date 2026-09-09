package cl.bootcamp.springedumanager_2.service;


import java.util.List;

import org.springframework.stereotype.Service;

import cl.bootcamp.springedumanager_2.exception.ReglaNegocioException;
import cl.bootcamp.springedumanager_2.model.Estudiante;
import cl.bootcamp.springedumanager_2.repository.CalificacionRepository;
import cl.bootcamp.springedumanager_2.repository.EstudianteRepository;
import cl.bootcamp.springedumanager_2.repository.InscripcionRepository;


/*
 * @Service
 *
 * Esta capa contiene las
 * reglas de negocio.
 *
 * El Controller NO debería decidir
 * directamente cómo guardar,
 * actualizar o eliminar datos.
 *
 * La estructura será:
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
 */
@Service
public class EstudianteService {


    /*
     * =====================================================
     * REPOSITORIES
     * =====================================================
     *
     * EstudianteRepository:
     *
     * Nos permite realizar operaciones
     * directamente sobre la entidad Estudiante.
     *
     * Ejemplos:
     *
     * findAll()
     * save()
     * existsById()
     * deleteById()
     */
    private final EstudianteRepository estudianteRepository;



    /*
     * InscripcionRepository:
     *
     * Lo necesitamos para comprobar si
     * el estudiante está inscrito
     * en uno o más cursos.
     *
     * Esto será importante antes
     * de eliminar un estudiante.
     */
    private final InscripcionRepository inscripcionRepository;



    /*
     * CalificacionRepository:
     *
     * Lo utilizamos para verificar si
     * el estudiante tiene calificaciones
     * registradas.
     *
     * Recordemos que la relación es:
     *
     * Estudiante
     *      ↓
     * Inscripcion
     *      ↓
     * Calificacion
     */
    private final CalificacionRepository calificacionRepository;



    /*
     * =====================================================
     * INYECCIÓN DE DEPENDENCIAS
     * =====================================================
     *
     * Spring nos entrega automáticamente
     * las implementaciones de los Repository.
     *
     * No necesitamos hacer:
     *
     * new EstudianteRepository()
     *
     * porque Spring administra estos objetos.
     */
    public EstudianteService(

            EstudianteRepository estudianteRepository,

            InscripcionRepository inscripcionRepository,

            CalificacionRepository calificacionRepository) {


        this.estudianteRepository =
                estudianteRepository;


        this.inscripcionRepository =
                inscripcionRepository;


        this.calificacionRepository =
                calificacionRepository;

    }



    /*
     * =====================================================
     * LISTAR
     * =====================================================
     *
     * Recupera todos los estudiantes
     * almacenados en la base de datos.
     *
     * findAll() viene incluido
     * automáticamente en JpaRepository.
     */
    public List<Estudiante> listar() {


        return estudianteRepository
                .findAll();

    }



    /*
     * =====================================================
     * GUARDAR / ACTUALIZAR
     * =====================================================
     *
     * Este mismo método permite realizar:
     *
     * CREATE
     *
     * y
     *
     * UPDATE
     *
     * dependiendo del ID del objeto.
     */
    public Estudiante guardar(
            Estudiante estudiante) {



        /*
         * =================================================
         * VALIDACIÓN 1
         * NOMBRE OBLIGATORIO
         * =================================================
         *
         * Verificamos:
         *
         * estudiante.getNombre() == null
         *
         * o que después de eliminar
         * espacios esté vacío.
         */
        if (estudiante.getNombre() == null ||
            estudiante.getNombre().trim().isEmpty()) {


            throw new ReglaNegocioException(

                "El nombre del estudiante es obligatorio."

            );

        }



        /*
         * =================================================
         * VALIDACIÓN 2
         * CORREO OBLIGATORIO
         * =================================================
         */
        if (estudiante.getEmail() == null ||
            estudiante.getEmail().trim().isEmpty()) {


            throw new ReglaNegocioException(

                "El correo del estudiante es obligatorio."

            );

        }



        /*
         * =================================================
         * CREATE
         * =================================================
         *
         * id == 0 significa que todavía
         * no ha sido persistido/creado.
         *
         * En este caso debemos comprobar
         * si ya existe otro estudiante
         * con el mismo correo.
         */
        if (estudiante.getId() == 0) {



            /*
             * existsByEmailIgnoreCase()
             *
             * es una consulta derivada
             * de Spring Data JPA.
             *
             * Busca si existe un estudiante
             * utilizando el email sin importar
             * mayúsculas o minúsculas.
             */
            if (estudianteRepository
                    .existsByEmailIgnoreCase(
                        estudiante.getEmail()
                    )) {


                throw new ReglaNegocioException(

                    "Ya existe un estudiante con ese correo."

                );

            }

        }



        /*
         * =================================================
         * UPDATE
         * =================================================
         *
         * Si ID es diferente de 0,
         * significa que estamos modificando
         * un registro existente.
         */
        else {



            /*
             * Necesitamos comprobar que
             * el correo no pertenezca
             * a OTRO estudiante.
             *
             * Por eso utilizamos:
             *
             * existsByEmailIgnoreCaseAndIdNot()
             *
             *
             * Ejemplo:
             *
             * Estamos editando:
             *
             * ID 1
             * juan@correo.cl
             *
             * Debemos permitir que Juan
             * conserve su propio correo.
             *
             * Pero no debemos permitir que
             * tome el correo de otro estudiante.
             */
            if (estudianteRepository
                    .existsByEmailIgnoreCaseAndIdNot(

                        estudiante.getEmail(),

                        estudiante.getId()

                    )) {


                throw new ReglaNegocioException(

                    "El correo ingresado pertenece a otro estudiante."

                );

            }

        }



        /*
         * =================================================
         * GUARDAR EN LA BASE DE DATOS
         * =================================================
         *
         * save() decide automáticamente:
         *
         * ID nuevo / no persistido
         *      ↓
         * INSERT
         *
         *
         * ID existente
         *      ↓
         * UPDATE
         */
        return estudianteRepository
                .save(estudiante);

    }



    /*
     * =====================================================
     * ELIMINAR
     * =====================================================
     *
     * Eliminar un estudiante ya no consiste
     * simplemente en borrar un elemento
     * de un ArrayList.
     *
     * Ahora tenemos relaciones entre tablas,
     * por lo que debemos verificar ciertas
     * reglas de negocio ANTES de eliminar.
     *
     *
     * Ejemplo:
     *
     * No queremos eliminar:
     *
     * Juan Pérez
     *
     * si Juan todavía está:
     *
     * - inscrito en un curso;
     * - o tiene calificaciones registradas.
     */
    public void eliminar(
            int id) {



        /*
         * =================================================
         * VALIDACIÓN 1
         * ¿EXISTE EL ESTUDIANTE?
         * =================================================
         *
         * existsById() viene incorporado
         * automáticamente por JpaRepository.
         *
         * Si el ID no existe,
         * lanzamos una excepción.
         */
        if (!estudianteRepository
                .existsById(id)) {


            throw new ReglaNegocioException(

                "El estudiante que intenta eliminar no existe."

            );

        }



        /*
         * =================================================
         * VALIDACIÓN 2
         * ¿TIENE CALIFICACIONES?
         * =================================================
         *
         * Nuestra estructura es:
         *
         * Estudiante
         *      ↓
         * Inscripcion
         *      ↓
         * Calificacion
         *
         *
         * Por eso consultamos:
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
         * Si existe al menos una calificación
         * correspondiente al estudiante,
         * no permitimos eliminarlo.
         */
        if (calificacionRepository
                .existsByInscripcion_Estudiante_Id(
                    id
                )) {


            throw new ReglaNegocioException(

                "No se puede eliminar el estudiante "
                + "porque tiene calificaciones registradas."

            );

        }



        /*
         * =================================================
         * VALIDACIÓN 3
         * ¿TIENE INSCRIPCIONES?
         * =================================================
         *
         * Incluso si todavía no tiene notas,
         * puede encontrarse inscrito
         * en uno o más cursos.
         *
         * Ejemplo:
         *
         * Juan Pérez
         *      ↓
         * Inscripcion
         *      ↓
         * Java
         *
         *
         * Mientras esa inscripción exista
         * no permitiremos eliminar al estudiante.
         */
        if (inscripcionRepository
                .existsByEstudiante_Id(
                    id
                )) {


            throw new ReglaNegocioException(

                "No se puede eliminar el estudiante "
                + "porque se encuentra inscrito "
                + "en uno o más cursos."

            );

        }



        /*
         * =================================================
         * ELIMINAR
         * =================================================
         *
         * Si el código llegó hasta aquí
         * significa que:
         *
         * ✅ el estudiante existe;
         *
         * ✅ no tiene calificaciones;
         *
         * ✅ no tiene inscripciones.
         *
         *
         * Por lo tanto podemos eliminarlo.
         *
         * deleteById() viene incorporado
         * en JpaRepository.
         */
        estudianteRepository
                .deleteById(id);

    }

}