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


import cl.bootcamp.springedumanager_2.model.Curso;



/*
 * @Controller
 *
 * Le indica a Spring que esta clase
 * pertenece a la capa Controller.
 *
 * Se encargará de recibir solicitudes HTTP
 * relacionadas con los cursos.
 */
@Controller


/*
 * @RequestMapping
 *
 * Define una ruta base para todas
 * las operaciones de este Controller.
 *
 * Todas comenzarán con:
 *
 * /cursos
 */
@RequestMapping("/cursos")

public class CursoController {



    /*
     * Lista temporal donde almacenamos
     * los cursos.
     *
     * Todavía NO utilizamos una base de datos.
     *
     * Los datos solamente existirán mientras
     * la aplicación esté ejecutándose.
     */
    private final List<Curso> listaCursos =
            new ArrayList<>();



    /*
     * Variable que utilizaremos para
     * simular un ID autoincremental.
     *
     * En la Clase 2 MySQL será quien
     * genere los ID.
     */
    private int siguienteId = 1;



    /*
     * Constructor.
     *
     * Se ejecuta cuando Spring crea
     * CursoController.
     *
     * Agregamos algunos cursos iniciales
     * para tener información en pantalla.
     */
    public CursoController() {


        listaCursos.add(
            new Curso(
                siguienteId++,
                "Java",
                "Bootcamp Full Stack Java"
            )
        );


        listaCursos.add(
            new Curso(
                siguienteId++,
                "Python",
                "Bootcamp Full Stack Python"
            )
        );


        listaCursos.add(
            new Curso(
                siguienteId++,
                "PHP",
                "Bootcamp Full Stack PHP"
            )
        );

    }



    /*
     * =====================================================
     * REDIRECCIÓN AL LISTADO
     * =====================================================
     *
     * GET /cursos
     *
     * GET /cursos/
     */
    @GetMapping({"", "/"})
    public String irListar() {


        /*
         * redirect indica que el navegador
         * debe realizar una nueva petición.
         *
         * En este caso:
         *
         * GET /cursos/listar
         */
        return "redirect:/cursos/listar";

    }



    /*
     * =====================================================
     * READ - LISTAR
     * =====================================================
     *
     * GET /cursos/listar
     */
    @GetMapping("/listar")
    public String listar(
            Model model) {


        /*
         * Model permite enviar información
         * desde el Controller hacia Thymeleaf.
         *
         * Antes con Servlets hacíamos:
         *
         * request.setAttribute(
         *     "listaCursos",
         *     listaCursos
         * );
         */
        model.addAttribute(
                "listaCursos",
                listaCursos
        );


        /*
         * Spring buscará:
         *
         * src/main/resources/
         * templates/cursos/lista.html
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
     * El mismo método nos servirá
     * tanto para crear como actualizar.
     */
    @PostMapping("/guardar")
    public String guardar(

            /*
             * @ModelAttribute recibe los campos
             * enviados por el formulario y
             * construye automáticamente
             * un objeto Curso.
             *
             * Por ejemplo:
             *
             * id=0
             * nombre=Spring Boot
             * descripcion=Curso de Spring Boot
             *
             * se transforma en un objeto Curso.
             */
            @ModelAttribute
            Curso curso) {



        /*
         * =================================================
         * CREATE
         * =================================================
         *
         * Como id es int, su valor por defecto
         * en un objeto nuevo es 0.
         */
        if (curso.getId() == 0) {


            /*
             * Asignamos un nuevo ID.
             */
            curso.setId(
                    siguienteId++
            );


            /*
             * Agregamos el curso
             * al ArrayList.
             */
            listaCursos.add(
                    curso
            );

        }


        /*
         * =================================================
         * UPDATE
         * =================================================
         *
         * Si id es distinto de 0,
         * significa que estamos editando
         * un curso existente.
         */
        else {


            /*
             * Recorremos la lista utilizando
             * el índice i.
             *
             * Necesitamos conocer la posición
             * porque vamos a reemplazar
             * el objeto existente.
             */
            for (int i = 0;
                 i < listaCursos.size();
                 i++) {


                Curso cursoActual =
                        listaCursos.get(i);



                /*
                 * Buscamos el curso cuyo ID
                 * coincida con el recibido
                 * desde el formulario.
                 *
                 * Como id es int utilizamos ==.
                 */
                if (cursoActual.getId()
                        == curso.getId()) {


                    /*
                     * Reemplazamos el objeto
                     * anterior por el actualizado.
                     */
                    listaCursos.set(
                            i,
                            curso
                    );


                    /*
                     * Ya encontramos el curso.
                     *
                     * No necesitamos seguir
                     * recorriendo la lista.
                     */
                    break;

                }

            }

        }



        /*
         * Después de crear o actualizar
         * volvemos al listado.
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
     * Por ahora utilizamos POST porque estamos
     * trabajando con formularios HTML.
     *
     * Más adelante podremos evolucionarlo
     * utilizando fetch() y DELETE.
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(

            /*
             * @PathVariable obtiene el ID
             * directamente desde la URL.
             *
             * Ejemplo:
             *
             * /cursos/eliminar/2
             *
             * id = 2
             */
            @PathVariable
            int id) {


        /*
         * removeIf elimina todos los objetos
         * que cumplan la condición.
         *
         * En este caso:
         *
         * curso.getId() == id
         */
        listaCursos.removeIf(

            curso ->
                curso.getId() == id

        );


        /*
         * Después de eliminar
         * volvemos al listado.
         */
        return "redirect:/cursos/listar";

    }

}