package cl.bootcamp.springedumanager_2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.bootcamp.springedumanager_2.model.Estudiante;


/*
 * Estos imports se utilizarían si quisiéramos
 * activar los ejemplos realizados con @Query.
 *
 * Por ahora los dejamos comentados porque
 * utilizaremos las consultas derivadas
 * de Spring Data JPA.
 */

// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;



/*
 * =========================================================
 * ESTUDIANTE REPOSITORY
 * =========================================================
 *
 * @Repository
 *
 * Marca esta interfaz como un componente
 * perteneciente a la capa de acceso a datos.
 *
 *
 * Nuestra arquitectura es:
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
 * EstudianteRepository se encargará
 * de realizar operaciones relacionadas
 * con la entidad Estudiante.
 *
 *
 * Al extender:
 *
 * JpaRepository<Estudiante, Integer>
 *
 * indicamos:
 *
 * Estudiante
 *      → entidad que administrará
 *        este Repository.
 *
 * Integer
 *      → tipo de dato correspondiente
 *        al ID de Estudiante.
 *
 *
 * Recordemos que nuestra entidad tiene:
 *
 * private int id;
 *
 * pero los genéricos de Java no permiten
 * utilizar tipos primitivos como:
 *
 * int
 *
 * por eso utilizamos:
 *
 * Integer
 */
@Repository
public interface EstudianteRepository
        extends JpaRepository<Estudiante, Integer> {



    /*
     * =====================================================
     * MÉTODOS QUE YA OBTENEMOS DE JpaRepository
     * =====================================================
     *
     * NO necesitamos escribir estos métodos.
     *
     * JpaRepository ya los incorpora
     * automáticamente.
     *
     *
     * -----------------------------------------------------
     * findAll()
     * -----------------------------------------------------
     *
     * Recupera todos los estudiantes
     * almacenados en la base de datos.
     *
     * Ejemplo:
     *
     * estudianteRepository.findAll();
     *
     *
     * Conceptualmente:
     *
     * SELECT *
     * FROM estudiantes;
     *
     *
     *
     * -----------------------------------------------------
     * findById(id)
     * -----------------------------------------------------
     *
     * Busca un estudiante utilizando
     * su clave primaria.
     *
     * Ejemplo:
     *
     * estudianteRepository.findById(3);
     *
     *
     * Conceptualmente:
     *
     * SELECT *
     * FROM estudiantes
     * WHERE id = 3;
     *
     *
     *
     * -----------------------------------------------------
     * save(estudiante)
     * -----------------------------------------------------
     *
     * Permite guardar o actualizar.
     *
     *
     * Si el objeto todavía no existe:
     *
     * INSERT
     *
     *
     * Si el objeto ya tiene un ID existente:
     *
     * UPDATE
     *
     *
     * Ejemplo:
     *
     * estudianteRepository.save(estudiante);
     *
     *
     *
     * -----------------------------------------------------
     * existsById(id)
     * -----------------------------------------------------
     *
     * Comprueba si existe un estudiante
     * con determinado ID.
     *
     * Devuelve:
     *
     * true
     *      → existe.
     *
     * false
     *      → no existe.
     *
     *
     * Lo utilizamos antes de eliminar:
     *
     * estudianteRepository.existsById(id);
     *
     *
     *
     * -----------------------------------------------------
     * deleteById(id)
     * -----------------------------------------------------
     *
     * Elimina un estudiante utilizando
     * su ID.
     *
     * Ejemplo:
     *
     * estudianteRepository.deleteById(id);
     *
     *
     * IMPORTANTE:
     *
     * Aunque Repository puede eliminar
     * directamente, nosotros realizaremos
     * primero las reglas de negocio
     * dentro de EstudianteService.
     *
     * Por ejemplo:
     *
     * ¿Tiene inscripciones?
     *
     * ¿Tiene calificaciones?
     *
     * Solamente después llamaremos a:
     *
     * deleteById(id)
     */



    /*
     * =====================================================
     * CONSULTAS DERIVADAS
     * =====================================================
     *
     * Spring Data JPA permite generar
     * consultas automáticamente
     * a partir del nombre del método.
     *
     *
     * Algunas palabras que Spring reconoce:
     *
     *
     * findBy
     *
     *      → buscar registros.
     *
     *
     * existsBy
     *
     *      → comprobar si existe
     *        algún registro.
     *
     *
     * countBy
     *
     *      → contar registros.
     *
     *
     * deleteBy
     *
     *      → eliminar registros.
     *
     *
     * And
     *
     *      → condición AND.
     *
     *
     * Or
     *
     *      → condición OR.
     *
     *
     * Not
     *
     *      → negación.
     *
     *
     * IgnoreCase
     *
     *      → ignora diferencias entre
     *        mayúsculas y minúsculas.
     *
     *
     * OrderBy
     *
     *      → ordenar resultados.
     *
     *
     * Asc
     *
     *      → orden ascendente.
     *
     *
     * Desc
     *
     *      → orden descendente.
     *
     *
     * GreaterThan
     *
     *      → mayor que.
     *
     *
     * LessThan
     *
     *      → menor que.
     *
     *
     * Between
     *
     *      → entre dos valores.
     *
     *
     * IsNull
     *
     *      → valor NULL.
     *
     *
     * IsNotNull
     *
     *      → valor distinto de NULL.
     *
     *
     * Containing
     *
     *      → contiene determinado texto.
     *
     *
     * StartingWith
     *
     *      → comienza con determinado texto.
     *
     *
     * EndingWith
     *
     *      → termina con determinado texto.
     */



    /*
     * =====================================================
     * VALIDAR CORREO AL CREAR UN ESTUDIANTE
     * =====================================================
     *
     * Este método será utilizado principalmente
     * cuando queremos CREAR un estudiante.
     *
     *
     * Spring interpreta el nombre:
     *
     * existsByEmailIgnoreCase
     *
     * de la siguiente forma:
     *
     *
     * exists
     *
     *      → pregunta si existe
     *        algún registro.
     *
     *
     * ByEmail
     *
     *      → utiliza la propiedad:
     *
     *        email
     *
     *        de la entidad Estudiante.
     *
     *
     * IgnoreCase
     *
     *      → ignora diferencias entre
     *        mayúsculas y minúsculas.
     *
     *
     * Ejemplo:
     *
     * En MySQL existe:
     *
     * juan@correo.cl
     *
     *
     * Si buscamos:
     *
     * JUAN@CORREO.CL
     *
     * será considerado el mismo correo.
     *
     *
     * Esto nos permite evitar:
     *
     * Juan Pérez
     * juan@correo.cl
     *
     * y
     *
     * Juan Soto
     * JUAN@CORREO.CL
     *
     *
     * Conceptualmente preguntamos:
     *
     * ¿Existe algún estudiante
     * cuyo correo sea igual
     * al correo recibido?
     */
    boolean existsByEmailIgnoreCase(
            String email
    );



    /*
     * =====================================================
     * VALIDAR CORREO AL EDITAR UN ESTUDIANTE
     * =====================================================
     *
     * Este método será utilizado principalmente
     * durante un UPDATE.
     *
     *
     * Spring interpreta:
     *
     * existsByEmailIgnoreCaseAndIdNot
     *
     *
     * exists
     *
     *      → comprueba existencia.
     *
     *
     * ByEmail
     *
     *      → busca por email.
     *
     *
     * IgnoreCase
     *
     *      → ignora mayúsculas
     *        y minúsculas.
     *
     *
     * And
     *
     *      → agrega otra condición.
     *
     *
     * IdNot
     *
     *      → el ID debe ser diferente
     *        al ID recibido.
     *
     *
     * ¿Por qué hacemos esto?
     *
     *
     * Supongamos:
     *
     * ID 1
     * Juan Pérez
     * juan@correo.cl
     *
     *
     * Queremos editar el nombre:
     *
     * Juan Pérez
     *
     * por:
     *
     * Juan Pérez Soto
     *
     *
     * pero mantenemos:
     *
     * juan@correo.cl
     *
     *
     * Si utilizáramos solamente:
     *
     * existsByEmailIgnoreCase(
     *     "juan@correo.cl"
     * )
     *
     * devolvería:
     *
     * true
     *
     * porque efectivamente existe.
     *
     *
     * Pero pertenece al MISMO estudiante
     * que estamos editando.
     *
     *
     * Entonces preguntamos:
     *
     * ¿Existe "juan@correo.cl"
     * en un estudiante cuyo ID
     * sea diferente de 1?
     *
     *
     * Conceptualmente:
     *
     * email = "juan@correo.cl"
     *
     * AND
     *
     * id <> 1
     *
     *
     * Esto permite que el estudiante
     * conserve su propio correo,
     * pero evita utilizar el correo
     * de otro estudiante.
     */
    boolean existsByEmailIgnoreCaseAndIdNot(
            String email,
            int id
    );



    /*
     * =====================================================
     * ¿PUEDEN LOS MÉTODOS LLAMARSE EN ESPAÑOL?
     * =====================================================
     *
     * Sí.
     *
     * Pero si queremos que Spring Data JPA
     * construya automáticamente una consulta
     * a partir del nombre del método,
     * debemos utilizar las palabras reconocidas
     * por Spring:
     *
     * findBy
     * existsBy
     * IgnoreCase
     * And
     * Not
     * etc.
     *
     *
     * Si queremos utilizar nombres
     * completamente en español:
     *
     * existeEstudianteConEmail(...)
     *
     * podemos escribir manualmente
     * la consulta utilizando:
     *
     * @Query
     */



    /*
     * =====================================================
     * EJEMPLOS UTILIZANDO @Query
     * =====================================================
     *
     * LOS SIGUIENTES MÉTODOS ESTÁN COMENTADOS.
     *
     * No forman parte de la implementación
     * que estamos utilizando actualmente.
     *
     * Los dejamos solamente como ejemplo
     * para comparar:
     *
     *
     * CONSULTA DERIVADA
     *
     * vs
     *
     * @Query
     *
     *
     * Cuando utilizamos @Query:
     *
     * nosotros escribimos explícitamente
     * la consulta utilizando JPQL.
     *
     *
     * IMPORTANTE:
     *
     * JPQL trabaja principalmente con:
     *
     * entidades Java
     *
     * y
     *
     * propiedades Java.
     *
     *
     * Por eso escribimos:
     *
     * FROM Estudiante e
     *
     * y NO:
     *
     * FROM estudiantes
     *
     *
     * Porque:
     *
     * Estudiante
     *
     * es nuestra entidad Java.
     */



    /*
     * =====================================================
     * EJEMPLO @Query 1
     * =====================================================
     *
     * Equivalente a:
     *
     * existsByEmailIgnoreCase(...)
     *
     *
     * En este caso podemos darle
     * un nombre completamente en español:
     *
     * existeEstudianteConEmail(...)
     *
     *
     * LOWER()
     *
     * transforma los textos a minúsculas
     * antes de realizar la comparación.
     *
     *
     * De esta manera:
     *
     * juan@correo.cl
     *
     * y
     *
     * JUAN@CORREO.CL
     *
     * serán considerados iguales.
     *
     *
     *
     * @Query("""
     *
     *     SELECT CASE
     *              WHEN COUNT(e) > 0
     *              THEN true
     *              ELSE false
     *            END
     *
     *     FROM Estudiante e
     *
     *     WHERE LOWER(e.email)
     *           = LOWER(:email)
     *
     * """)
     *
     * boolean existeEstudianteConEmail(
     *
     *         @Param("email")
     *         String email
     *
     * );
     *
     */



    /*
     * =====================================================
     * EJEMPLO @Query 2
     * =====================================================
     *
     * Equivalente a:
     *
     * existsByEmailIgnoreCaseAndIdNot(...)
     *
     *
     * Este método podría llamarse:
     *
     * existeOtroEstudianteConEmail(...)
     *
     *
     * La condición:
     *
     * e.id <> :id
     *
     * significa:
     *
     * ID diferente al ID recibido.
     *
     *
     * Ejemplo:
     *
     * ID actual:
     *
     * 1
     *
     *
     * correo:
     *
     * juan@correo.cl
     *
     *
     * preguntamos:
     *
     * ¿Existe algún estudiante
     * con ese email
     * cuyo ID sea distinto de 1?
     *
     *
     *
     * @Query("""
     *
     *     SELECT CASE
     *              WHEN COUNT(e) > 0
     *              THEN true
     *              ELSE false
     *            END
     *
     *     FROM Estudiante e
     *
     *     WHERE LOWER(e.email)
     *           = LOWER(:email)
     *
     *     AND e.id <> :id
     *
     * """)
     *
     * boolean existeOtroEstudianteConEmail(
     *
     *         @Param("email")
     *         String email,
     *
     *         @Param("id")
     *         int id
     *
     * );
     *
     */

}