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
import cl.bootcamp.springedumanager_2.service.EstudianteService;
import cl.bootcamp.springedumanager_2.service.InscripcionService;



/*
 * =========================================================
 * INSCRIPCION CONTROLLER
 * =========================================================
 *
 * Recibe las solicitudes HTTP
 * relacionadas con inscripciones.
 *
 *
 * Su responsabilidad es:
 *
 * - recibir solicitudes;
 *
 * - enviar datos hacia Thymeleaf;
 *
 * - llamar al Service;
 *
 * - manejar mensajes para SweetAlert2.
 *
 *
 * NO debe contener las reglas
 * de negocio.
 */
@Controller
@RequestMapping("/inscripciones")

public class InscripcionController {



    /*
     * Service principal.
     */
    private final InscripcionService
            inscripcionService;



    /*
     * Lo necesitamos para llenar
     * el select de estudiantes.
     */
    private final EstudianteService
            estudianteService;



    /*
     * Lo necesitamos para llenar
     * el select de cursos.
     */
    private final CursoService
            cursoService;



    /*
     * =====================================================
     * INYECCIÓN DE DEPENDENCIAS
     * =====================================================
     */
    public InscripcionController(

            InscripcionService
            inscripcionService,

            EstudianteService
            estudianteService,

            CursoService
            cursoService) {


        this.inscripcionService =
                inscripcionService;


        this.estudianteService =
                estudianteService;


        this.cursoService =
                cursoService;

    }



    /*
     * =====================================================
     * REDIRECCIÓN
     * =====================================================
     *
     * GET /inscripciones
     *
     * GET /inscripciones/
     */
    @GetMapping({"", "/"})
    public String irListar() {


        return "redirect:/inscripciones/listar";

    }



    /*
     * =====================================================
     * LISTAR
     * =====================================================
     *
     * GET /inscripciones/listar
     */
    @GetMapping("/listar")
    public String listar(
            Model model) {



        /*
         * Enviamos todas las inscripciones
         * existentes hacia la tabla.
         */
        model.addAttribute(

                "listaInscripciones",

                inscripcionService.listar()

        );



        /*
         * Enviamos los estudiantes
         * para llenar el SELECT.
         */
        model.addAttribute(

                "listaEstudiantes",

                estudianteService.listar()

        );



        /*
         * Enviamos los cursos
         * para llenar el SELECT.
         */
        model.addAttribute(

                "listaCursos",

                cursoService.listar()

        );



        /*
         * Spring buscará:
         *
         * templates/inscripciones/lista.html
         */
        return "inscripciones/lista";

    }



    /*
     * =====================================================
     * CREAR INSCRIPCIÓN
     * =====================================================
     *
     * POST /inscripciones/guardar
     *
     *
     * El formulario enviará:
     *
     * estudianteId
     *
     * cursoId
     */
    @PostMapping("/guardar")
    public String guardar(

            @RequestParam
            int estudianteId,

            @RequestParam
            int cursoId,

            RedirectAttributes
            redirectAttributes) {


        try {



            /*
             * El Controller solamente
             * entrega los ID al Service.
             *
             * El Service recuperará
             * Estudiante y Curso.
             */
            inscripcionService.inscribir(

                    estudianteId,

                    cursoId

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

                    "Estudiante inscrito correctamente."

                );

        }



        /*
         * Si alguna regla de negocio
         * no se cumple:
         *
         * - estudiante inexistente;
         *
         * - curso inexistente;
         *
         * - inscripción duplicada.
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



        return "redirect:/inscripciones/listar";

    }



    /*
     * =====================================================
     * ELIMINAR INSCRIPCIÓN
     * =====================================================
     *
     * POST /inscripciones/eliminar/3
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(

            @PathVariable
            int id,

            RedirectAttributes
            redirectAttributes) {


        try {



            /*
             * El Service decide si
             * puede eliminarse.
             */
            inscripcionService.eliminar(
                    id
            );



            redirectAttributes
                .addFlashAttribute(

                    "tipoMensaje",

                    "success"

                );


            redirectAttributes
                .addFlashAttribute(

                    "mensaje",

                    "Inscripción eliminada correctamente."

                );

        }


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



        return "redirect:/inscripciones/listar";

    }

}