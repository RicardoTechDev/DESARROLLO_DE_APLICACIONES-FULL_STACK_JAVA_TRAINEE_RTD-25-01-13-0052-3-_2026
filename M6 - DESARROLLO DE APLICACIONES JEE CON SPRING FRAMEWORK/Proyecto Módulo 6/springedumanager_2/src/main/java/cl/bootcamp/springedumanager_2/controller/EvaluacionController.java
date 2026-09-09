package cl.bootcamp.springedumanager_2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import cl.bootcamp.springedumanager_2.exception.ReglaNegocioException;
import cl.bootcamp.springedumanager_2.service.CursoService;
import cl.bootcamp.springedumanager_2.service.EvaluacionService;



/*
 * =========================================================
 * EVALUACION CONTROLLER
 * =========================================================
 *
 * @Controller
 *
 * Indica que esta clase pertenece
 * a la capa Controller de Spring MVC.
 *
 *
 * Su responsabilidad será:
 *
 * - recibir solicitudes HTTP;
 *
 * - recibir datos desde los formularios;
 *
 * - comunicarse con EvaluacionService;
 *
 * - solicitar los cursos a CursoService;
 *
 * - enviar información hacia Thymeleaf;
 *
 * - redireccionar;
 *
 * - enviar mensajes de éxito o error
 *   hacia SweetAlert2.
 *
 *
 * IMPORTANTE:
 *
 * El Controller ya NO contiene:
 *
 * ArrayList
 *
 * siguienteId
 *
 * ni lógica para recorrer listas.
 *
 *
 * La nueva arquitectura será:
 *
 * EvaluacionController
 *          ↓
 * EvaluacionService
 *          ↓
 * EvaluacionRepository
 *          ↓
 * JPA / Hibernate
 *          ↓
 * MySQL
 */
@Controller


/*
 * Todas las rutas manejadas
 * por este Controller comenzarán con:
 *
 * /evaluaciones
 */
@RequestMapping("/evaluaciones")

public class EvaluacionController {



    /*
     * =====================================================
     * SERVICES
     * =====================================================
     */



    /*
     * EvaluacionService
     *
     * Contiene las reglas de negocio
     * relacionadas con las evaluaciones.
     *
     * Por ejemplo:
     *
     * - validar nombre;
     *
     * - validar ponderación;
     *
     * - comprobar curso;
     *
     * - evitar evaluaciones duplicadas;
     *
     * - impedir que las ponderaciones
     *   superen el 100%;
     *
     * - eliminar evaluaciones.
     */
    private final EvaluacionService
            evaluacionService;



    /*
     * CursoService
     *
     * Lo necesitamos porque en el formulario
     * de evaluación tendremos un:
     *
     * <select>
     *
     * con todos los cursos disponibles.
     *
     *
     * Ejemplo:
     *
     * Curso:
     *
     * [ Java        ▼ ]
     * [ Python        ]
     * [ Spring Boot   ]
     */
    private final CursoService
            cursoService;



    /*
     * =====================================================
     * INYECCIÓN DE DEPENDENCIAS
     * =====================================================
     *
     * Spring detectará automáticamente:
     *
     * EvaluacionService
     *
     * y
     *
     * CursoService
     *
     * porque están marcados con:
     *
     * @Service
     *
     *
     * Luego los entregará al constructor.
     */
    public EvaluacionController(

            EvaluacionService
            evaluacionService,

            CursoService
            cursoService) {


        this.evaluacionService =
                evaluacionService;


        this.cursoService =
                cursoService;

    }



    /*
     * =====================================================
     * REDIRECCIÓN AL LISTADO
     * =====================================================
     *
     * GET /evaluaciones
     *
     * GET /evaluaciones/
     *
     *
     * Ambas rutas redireccionarán hacia:
     *
     * GET /evaluaciones/listar
     */
    @GetMapping({"", "/"})
    public String irListar() {


        return "redirect:/evaluaciones/listar";

    }



    /*
     * =====================================================
     * READ - LISTAR EVALUACIONES
     * =====================================================
     *
     * GET /evaluaciones/listar
     */
    @GetMapping("/listar")
    public String listar(
            Model model) {



        /*
         * =================================================
         * LISTA DE EVALUACIONES
         * =================================================
         *
         * CLASE 1:
         *
         * model.addAttribute(
         *     "listaEvaluaciones",
         *     listaEvaluaciones
         * );
         *
         *
         * CLASE 2:
         *
         * Los datos ya no vienen
         * desde un ArrayList.
         *
         * Ahora:
         *
         * EvaluacionController
         *          ↓
         * EvaluacionService
         *          ↓
         * EvaluacionRepository.findAll()
         *          ↓
         * MySQL
         */
        model.addAttribute(

                "listaEvaluaciones",

                evaluacionService.listar()

        );



        /*
         * =================================================
         * LISTA DE CURSOS
         * =================================================
         *
         * También necesitamos enviar
         * los cursos hacia la vista.
         *
         * Esto permitirá construir
         * el SELECT del modal:
         *
         * Curso
         *
         * [ Java       ▼ ]
         * [ Python       ]
         * [ Spring Boot  ]
         *
         *
         * Thymeleaf recibirá:
         *
         * listaCursos
         */
        model.addAttribute(

                "listaCursos",

                cursoService.listar()

        );



        /*
         * Spring buscará:
         *
         * src/main/resources/templates/
         * evaluaciones/lista.html
         */
        return "evaluaciones/lista";

    }



    /*
     * =====================================================
     * CREATE / UPDATE
     * =====================================================
     *
     * POST /evaluaciones/guardar
     *
     *
     * Este mismo método servirá para:
     *
     * CREATE
     *
     * y
     *
     * UPDATE.
     *
     *
     * Nuestro formulario enviará:
     *
     * id
     *
     * nombre
     *
     * ponderacion
     *
     * cursoId
     */
    @PostMapping("/guardar")
    public String guardar(



            /*
             * =================================================
             * ID
             * =================================================
             *
             * Cuando creamos:
             *
             * id = 0
             *
             *
             * Cuando editamos:
             *
             * id = ID existente.
             *
             *
             * Ejemplo:
             *
             * id = 3
             */
            @RequestParam
            int id,



            /*
             * Nombre de la evaluación.
             *
             * Ejemplo:
             *
             * Prueba 1
             */
            @RequestParam
            String nombre,



            /*
             * Porcentaje correspondiente
             * a la evaluación.
             *
             * Ejemplo:
             *
             * 30
             *
             * significa:
             *
             * 30%
             */
            @RequestParam
            double ponderacion,



            /*
             * ID del curso seleccionado
             * desde el <select>.
             *
             * Ejemplo:
             *
             * cursoId = 2
             *
             *
             * EvaluacionService utilizará
             * este ID para recuperar
             * la entidad Curso.
             */
            @RequestParam
            int cursoId,



            /*
             * RedirectAttributes permite
             * enviar información después
             * de realizar un redirect.
             *
             * Lo utilizaremos para:
             *
             * tipoMensaje
             *
             * mensaje
             *
             * que posteriormente serán
             * mostrados utilizando SweetAlert2.
             */
            RedirectAttributes
            redirectAttributes) {


        try {



            /*
             * =================================================
             * DETERMINAR CREATE / UPDATE
             * =================================================
             *
             * Guardamos esta información
             * ANTES de persistir.
             *
             *
             * id == 0
             *
             *      ↓
             *
             * Nueva evaluación.
             */
            boolean nueva =
                    id == 0;



            /*
             * =================================================
             * GUARDAR
             * =================================================
             *
             * El Controller NO realiza
             * las validaciones de negocio.
             *
             * Simplemente entrega los datos
             * al Service.
             */
            evaluacionService.guardar(

                    id,

                    nombre,

                    ponderacion,

                    cursoId

            );



            /*
             * =================================================
             * MENSAJE DE ÉXITO
             * =================================================
             *
             * SweetAlert2 recibirá:
             *
             * icon = success
             */
            redirectAttributes
                .addFlashAttribute(

                    "tipoMensaje",

                    "success"

                );



            /*
             * El mensaje dependerá
             * de la operación realizada.
             */
            redirectAttributes
                .addFlashAttribute(

                    "mensaje",

                    nueva

                    ? "Evaluación registrada correctamente."

                    : "Evaluación actualizada correctamente."

                );

        }



        /*
         * =====================================================
         * ERROR DE REGLA DE NEGOCIO
         * =====================================================
         *
         * EvaluacionService puede lanzar:
         *
         * ReglaNegocioException
         *
         *
         * Por ejemplo:
         *
         * - nombre vacío;
         *
         * - curso inexistente;
         *
         * - evaluación duplicada;
         *
         * - ponderación inválida;
         *
         * - suma de ponderaciones
         *   superior al 100%.
         */
        catch (ReglaNegocioException e) {



            /*
             * SweetAlert2 mostrará
             * el icono de error.
             */
            redirectAttributes
                .addFlashAttribute(

                    "tipoMensaje",

                    "error"

                );



            /*
             * Recuperamos directamente
             * el mensaje generado
             * desde el Service.
             *
             *
             * Ejemplo:
             *
             * "La suma de las ponderaciones
             * del curso no puede superar
             * el 100%."
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
        return "redirect:/evaluaciones/listar";

    }



    /*
     * =====================================================
     * DELETE - ELIMINAR EVALUACIÓN
     * =====================================================
     *
     * POST /evaluaciones/eliminar/3
     *
     *
     * Seguimos utilizando POST
     * porque en esta Clase 2 todavía
     * trabajamos con formularios HTML.
     *
     *
     * Más adelante con REST podremos tener:
     *
     * DELETE /api/evaluaciones/3
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(



            /*
             * @PathVariable
             *
             * obtiene el ID directamente
             * desde la URL.
             *
             *
             * Ejemplo:
             *
             * /evaluaciones/eliminar/3
             *
             *      ↓
             *
             * id = 3
             */
            @PathVariable
            int id,



            /*
             * Lo utilizamos nuevamente
             * para enviar los mensajes
             * hacia SweetAlert2.
             */
            RedirectAttributes
            redirectAttributes) {


        try {



            /*
             * =================================================
             * ELIMINAR
             * =================================================
             *
             * El Controller NO ejecuta:
             *
             * repository.deleteById()
             *
             *
             * Tampoco utiliza:
             *
             * listaEvaluaciones.removeIf()
             *
             *
             * Entrega la responsabilidad
             * al Service.
             */
            evaluacionService.eliminar(
                    id
            );



            /*
             * Si no ocurrió una excepción,
             * significa que fue eliminada.
             */
            redirectAttributes
                .addFlashAttribute(

                    "tipoMensaje",

                    "success"

                );


            redirectAttributes
                .addFlashAttribute(

                    "mensaje",

                    "Evaluación eliminada correctamente."

                );

        }



        /*
         * =====================================================
         * ERROR
         * =====================================================
         *
         * Actualmente puede ocurrir:
         *
         * - evaluación inexistente.
         *
         *
         * Más adelante, cuando tengamos
         * CalificacionRepository:
         *
         * - evaluación con calificaciones
         *   registradas.
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



        return "redirect:/evaluaciones/listar";

    }

}