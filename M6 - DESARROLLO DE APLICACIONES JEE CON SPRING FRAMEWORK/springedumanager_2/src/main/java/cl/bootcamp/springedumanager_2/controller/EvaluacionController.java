package cl.bootcamp.springedumanager_2.controller;


import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import cl.bootcamp.springedumanager_2.model.Evaluacion;

/*
 * @Controller
 *
 * Indica que esta clase atenderá
 * solicitudes HTTP relacionadas
 * con evaluaciones.
 */
@Controller


/*
 * Todas las rutas de este Controller
 * comenzarán con:
 *
 * /evaluaciones
 */
@RequestMapping("/evaluaciones")

public class EvaluacionController {
    private final List<Evaluacion> listaEvaluaciones =
            new ArrayList<>();

    private int siguienteId = 1;

    public EvaluacionController() {
        listaEvaluaciones.add(
            new Evaluacion(
                siguienteId++,
                "Prueba Java",
                "Roberto Gómez",
                "Java",
                6.2
            )
        );


        listaEvaluaciones.add(
            new Evaluacion(
                siguienteId++,
                "Proyecto Python",
                "Juan Pérez",
                "Python",
                5.8
            )
        );


        listaEvaluaciones.add(
            new Evaluacion(
                siguienteId++,
                "Evaluación PHP",
                "Ana Salazár",
                "PHP",
                6.5
            )
        );

    }


    /*
     * =====================================================
     * REDIRECCIÓN AL LISTADO
     * =====================================================
     *
     * GET /evaluaciones
     *
     * GET /evaluaciones/
     */
    @GetMapping({"", "/"})
    public String irListar() {

        /*
         * Redireccionamos hacia:
         *
         * GET /evaluaciones/listar
         */
        return "redirect:/evaluaciones/listar";
    }


    /*
     * =====================================================
     * READ - LISTAR
     * =====================================================
     *
     * GET /evaluaciones/listar
     */
    @GetMapping("/listar")
    public String listar(Model model) {
    	 /*
         * Model permite enviar datos
         * desde el Controller hacia Thymeleaf.
         *
         * Conceptualmente reemplaza:
         *
         * request.setAttribute(...)
         */

        /*
         * Enviamos el ArrayList
         * hacia la vista Thymeleaf.
         */
        model.addAttribute("listaEvaluaciones",listaEvaluaciones);


        /*
         * Spring buscará:
         *
         * templates/evaluaciones/lista.html
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
     * Este único método se encargará
     * de crear y actualizar.
     */
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Evaluacion evaluacion) {
    	/*
         * @ModelAttribute toma los parámetros
         * enviados por el formulario:
         *
         * id
         * nombre
         * estudiante
         * curso
         * nota
         *
         * y crea automáticamente
         * un objeto Evaluacion.
         */



        /*
         * =================================================
         * CREATE
         * =================================================
         *
         * Si el ID es 0 significa que estamos
         * creando una nueva evaluación.
         */
        if (evaluacion.getId() == 0) {
            evaluacion.setId(siguienteId++);
            
            listaEvaluaciones.add(evaluacion);
        }


        /*
         * =================================================
         * UPDATE
         * =================================================
         *
         * Si tiene un ID distinto de 0,
         * significa que estamos editando.
         */
        else {
            /*
             * Recorremos la lista utilizando
             * su índice.
             */
            for (int i = 0; i < listaEvaluaciones.size(); i++) {
                Evaluacion evaluacionActual = listaEvaluaciones.get(i);
                
                if (evaluacionActual.getId() == evaluacion.getId()) {

                    /*
                     * Reemplazamos la evaluación
                     * anterior por la nueva versión.
                     */
                    listaEvaluaciones.set(
                            i,
                            evaluacion
                    );

                    break;
                }
            }
        }

        /*
         * Después de guardar o actualizar
         * regresamos al listado.
         */
        return "redirect:/evaluaciones/listar";

    }



    /*
     * =====================================================
     * DELETE
     * =====================================================
     *
     * POST /evaluaciones/eliminar/2
     *
     * De momento usamos POST porque
     * estamos trabajando con formularios HTML.
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
    	/*
         * @PathVariable captura el ID
         * que viene desde la URL.
         */

        /*
         * Eliminamos la evaluación
         * cuyo ID coincida.
         */
        listaEvaluaciones.removeIf(
            evaluacion ->
                evaluacion.getId() == id
        );


        /*
         * Volvemos al listado.
         */
        return "redirect:/evaluaciones/listar";

    }

}