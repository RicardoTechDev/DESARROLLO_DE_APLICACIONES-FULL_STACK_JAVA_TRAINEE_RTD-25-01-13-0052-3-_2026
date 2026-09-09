package cl.bootcamp.springedumanager_2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import cl.bootcamp.springedumanager_2.exception.ReglaNegocioException;
import cl.bootcamp.springedumanager_2.model.Curso;
import cl.bootcamp.springedumanager_2.service.CursoService;



/*
 * @Controller
 *
 * Indica que esta clase pertenece
 * a la capa Controller de Spring MVC.
 *
 * Su responsabilidad principal será:
 *
 * - recibir solicitudes HTTP;
 * - comunicarse con CursoService;
 * - enviar información hacia Thymeleaf;
 * - redireccionar al usuario;
 * - mostrar mensajes de éxito o error.
 *
 *
 * IMPORTANTE:
 *
 * El Controller ya NO se encargará
 * directamente de acceder a la base de datos.
 *
 * La comunicación será:
 *
 * Controller
 *      ↓
 * Service
 *      ↓
 * Repository
 *      ↓
 * MySQL
 */
@Controller


/*
 * Ruta base del Controller.
 *
 * Todas las rutas de esta clase
 * comenzarán con:
 *
 * /cursos
 */
@RequestMapping("/cursos")

public class CursoController {



    /*
     * =====================================================
     * SERVICE
     * =====================================================
     *
     * En la Clase 1 teníamos:
     *
     * private final List<Curso> listaCursos;
     *
     * y:
     *
     * private int siguienteId;
     *
     *
     * Ahora ya NO necesitamos ninguno
     * de esos elementos.
     *
     * Los datos serán administrados
     * mediante CursoService.
     */
    private final CursoService cursoService;



    /*
     * =====================================================
     * INYECCIÓN DE DEPENDENCIAS
     * =====================================================
     *
     * Spring detectará CursoService
     * porque tiene la anotación:
     *
     * @Service
     *
     * y lo entregará automáticamente
     * al constructor.
     *
     * Esto se conoce como:
     *
     * Inyección de Dependencias.
     */
    public CursoController(
            CursoService cursoService) {


        /*
         * Guardamos la instancia
         * recibida desde Spring.
         */
        this.cursoService =
                cursoService;

    }



    /*
     * =====================================================
     * REDIRECCIÓN AL LISTADO
     * =====================================================
     *
     * GET /cursos
     *
     * GET /cursos/
     *
     * Ambas rutas redireccionan hacia:
     *
     * /cursos/listar
     */
    @GetMapping({"", "/"})
    public String irListar() {


        /*
         * redirect:
         *
         * indica al navegador que debe
         * realizar una nueva solicitud.
         *
         * En este caso:
         *
         * GET /cursos/listar
         */
        return "redirect:/cursos/listar";

    }



    /*
     * =====================================================
     * READ - LISTAR CURSOS
     * =====================================================
     *
     * GET /cursos/listar
     */
    @GetMapping("/listar")
    public String listar(
            Model model) {


        /*
         * CLASE 1:
         *
         * model.addAttribute(
         *     "listaCursos",
         *     listaCursos
         * );
         *
         *
         * CLASE 2:
         *
         * Ya no obtenemos los datos
         * desde un ArrayList.
         *
         * Solicitamos los datos
         * a CursoService.
         */
        model.addAttribute(

                "listaCursos",

                cursoService.listar()

        );


        /*
         * CursoService.listar()
         *
         * internamente realizará:
         *
         * cursoRepository.findAll()
         *
         * y Spring Data JPA recuperará
         * los registros desde MySQL.
         */


        /*
         * Spring + Thymeleaf buscarán:
         *
         * src/main/resources/templates/
         * cursos/lista.html
         */
        return "cursos/lista";

    }



    /*
     * =====================================================
     * CREATE / UPDATE
     * =====================================================
     *
     * POST /cursos/guardar
     *
     * Seguimos utilizando el mismo
     * formulario/modal de la Clase 1.
     *
     *
     * Nuevo curso:
     *
     * id = 0
     *
     *
     * Editar curso:
     *
     * id = ID existente
     *
     *
     * La diferencia es que ahora
     * CursoService y JPA se encargarán
     * de guardar los datos.
     */
    @PostMapping("/guardar")
    public String guardar(

            /*
             * @ModelAttribute
             *
             * toma automáticamente los
             * campos enviados por el formulario:
             *
             * id
             * nombre
             * descripcion
             *
             * y construye un objeto Curso.
             */
            @ModelAttribute
            Curso curso,


            /*
             * RedirectAttributes permite
             * enviar información después
             * de un redirect.
             *
             * Lo utilizaremos para enviar
             * mensajes hacia SweetAlert2.
             */
            RedirectAttributes
            redirectAttributes) {


        try {


            /*
             * Antes de guardar debemos recordar
             * si se trata de un registro nuevo.
             *
             * Esto es importante porque después
             * de ejecutar save(), JPA asignará
             * automáticamente el nuevo ID.
             *
             *
             * Ejemplo:
             *
             * antes:
             *
             * id = 0
             *
             * después de guardar:
             *
             * id = 4
             */
            boolean nuevo =
                    curso.getId() == 0;



            /*
             * En la Clase 1 hacíamos:
             *
             * if (curso.getId() == 0) {
             *
             *     curso.setId(siguienteId++);
             *     listaCursos.add(curso);
             *
             * } else {
             *
             *     // recorrer ArrayList
             *     // y reemplazar objeto
             *
             * }
             *
             *
             * Ahora toda esa lógica desaparece.
             *
             * Simplemente llamamos al Service.
             */
            cursoService.guardar(
                    curso
            );



            /*
             * =================================================
             * MENSAJE DE ÉXITO
             * =================================================
             *
             * Estos atributos estarán disponibles
             * después del redirect.
             *
             * Nuestro layout base utilizará:
             *
             * tipoMensaje
             * mensaje
             *
             * para mostrar SweetAlert2.
             */
            redirectAttributes
                .addFlashAttribute(

                    "tipoMensaje",

                    "success"

                );



            /*
             * Personalizamos el mensaje
             * dependiendo de si fue:
             *
             * CREATE
             *
             * o
             *
             * UPDATE
             */
            redirectAttributes
                .addFlashAttribute(

                    "mensaje",

                    nuevo

                    ? "Curso registrado correctamente."

                    : "Curso actualizado correctamente."

                );

        }


        /*
         * =====================================================
         * ERROR DE REGLA DE NEGOCIO
         * =====================================================
         *
         * CursoService puede lanzar esta excepción
         * cuando detecta una operación inválida.
         *
         * Ejemplos:
         *
         * - nombre vacío;
         *
         * - curso duplicado;
         *
         * - intento de usar el nombre
         *   de otro curso.
         */
        catch (ReglaNegocioException e) {


            /*
             * Indicamos a SweetAlert2
             * que debe mostrar un error.
             */
            redirectAttributes
                .addFlashAttribute(

                    "tipoMensaje",

                    "error"

                );



            /*
             * Recuperamos exactamente
             * el mensaje generado
             * desde la capa Service.
             */
            redirectAttributes
                .addFlashAttribute(

                    "mensaje",

                    e.getMessage()

                );

        }



        /*
         * Después de guardar
         * regresamos al listado.
         */
        return "redirect:/cursos/listar";

    }



    /*
     * =====================================================
     * DELETE
     * =====================================================
     *
     * POST /cursos/eliminar/2
     *
     * Por ahora seguimos utilizando POST
     * porque todavía estamos trabajando
     * con formularios HTML tradicionales.
     *
     * En una iteración posterior,
     * utilizando fetch() y REST,
     * evolucionaremos a:
     *
     * DELETE /api/cursos/2
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(

            /*
             * @PathVariable
             *
             * captura el ID desde la URL.
             *
             * Ejemplo:
             *
             * /cursos/eliminar/2
             *
             * id = 2
             */
            @PathVariable
            int id,


            /*
             * Permitirá enviar mensajes
             * después del redirect.
             */
            RedirectAttributes
            redirectAttributes) {


        try {


            /*
             * Ya NO hacemos:
             *
             * listaCursos.removeIf(...)
             *
             *
             * Ahora:
             *
             * Controller
             *      ↓
             * Service
             *      ↓
             * Repository
             *      ↓
             * MySQL
             */
            cursoService.eliminar(
                    id
            );



            /*
             * Mensaje exitoso.
             */
            redirectAttributes
                .addFlashAttribute(

                    "tipoMensaje",

                    "success"

                );


            redirectAttributes
                .addFlashAttribute(

                    "mensaje",

                    "Curso eliminado correctamente."

                );

        }



        /*
         * El Service puede impedir
         * la eliminación.
         *
         * Más adelante, cuando tengamos
         * Inscripcion y Evaluacion,
         * podremos tener reglas como:
         *
         * "No se puede eliminar el curso
         * porque tiene estudiantes inscritos."
         *
         * o:
         *
         * "No se puede eliminar el curso
         * porque tiene evaluaciones registradas."
         */
        catch (ReglaNegocioException e) {


            redirectAttributes
                .addFlashAttribute(

                    "tipoMensaje",

                    "error"

                );


            redirectAttributes
                .addFlashAttribute(

                    "mensaje",

                    e.getMessage()

                );

        }



        return "redirect:/cursos/listar";

    }

}