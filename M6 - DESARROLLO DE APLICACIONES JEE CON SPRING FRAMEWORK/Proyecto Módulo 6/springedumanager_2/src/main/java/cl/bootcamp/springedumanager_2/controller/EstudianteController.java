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

import cl.bootcamp.springedumanager_2.model.Estudiante;


@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {


    /*
     * Lista temporal que funciona como
     * nuestra fuente de datos.
     *
     * Todavía NO utilizamos base de datos.
     */
    private final List<Estudiante> listaEstudiantes =
            new ArrayList<>();


    /*
     * Simulamos temporalmente
     * un ID autoincremental.
     */
    private int siguienteId = 1;


    /*
     * Constructor.
     *
     * Cargamos algunos estudiantes
     * para visualizar información
     * al iniciar la aplicación.
     */
    public EstudianteController() {


        listaEstudiantes.add(
            new Estudiante(
                siguienteId++,
                "Roberto Goméz",
                "robertog@correo.cl"
            )
        );


        listaEstudiantes.add(
            new Estudiante(
                siguienteId++,
                "Juan Pérez",
                "juan@correo.cl"
            )
        );


        listaEstudiantes.add(
            new Estudiante(
                siguienteId++,
                "Ana Salazár",
                "ana@correo.cl"
            )
        );
    }


    /*
     * =====================================================
     * REDIRECCIÓN AL LISTADO
     * =====================================================
     *
     * GET /estudiantes
     * GET /estudiantes/
     */
    @GetMapping({"", "/"})
    public String irListar() {

        return "redirect:/estudiantes/listar";
    }


    /*
     * =====================================================
     * READ
     * =====================================================
     *
     * GET /estudiantes/listar
     */
    @GetMapping("/listar")
    public String listar(
            Model model) {


        /*
         * Enviamos la lista hacia Thymeleaf.
         *
         * Conceptualmente es parecido a:
         *
         * request.setAttribute(...)
         */
        model.addAttribute(
                "listaEstudiantes",
                listaEstudiantes
        );


        /*
         * Spring buscará:
         *
         * templates/estudiantes/lista.html
         */
        return "estudiantes/lista";
    }


    /*
     * =====================================================
     * CREATE / UPDATE
     * =====================================================
     *
     * POST /estudiantes/guardar
     *
     * Este método sirve tanto para crear
     * como para actualizar.
     */
    @PostMapping("/guardar")
    public String guardar(

            /*
             * @ModelAttribute toma los campos
             * enviados por el formulario y
             * construye un objeto Estudiante.
             */
            @ModelAttribute
            Estudiante estudiante) {


        /*
         * CREATE
         *
         * Como id es int, cuando un objeto
         * nuevo no tiene ID su valor es 0.
         */
        if (estudiante.getId() == 0) {


            /*
             * Asignamos un nuevo ID.
             */
            estudiante.setId(
                    siguienteId++
            );


            /*
             * Agregamos el estudiante
             * a nuestra lista temporal.
             */
            listaEstudiantes.add(
                    estudiante
            );

        }


        /*
         * UPDATE
         *
         * Si el ID es distinto de 0,
         * significa que el estudiante
         * ya existe.
         */
        else {


            for (int i = 0;
                 i < listaEstudiantes.size();
                 i++) {


                Estudiante actual =
                        listaEstudiantes.get(i);


                /*
                 * Buscamos el registro
                 * que tenga el mismo ID.
                 */
                if (actual.getId()
                        == estudiante.getId()) {


                    /*
                     * Reemplazamos el objeto
                     * antiguo por el actualizado.
                     */
                    listaEstudiantes.set(
                            i,
                            estudiante
                    );


                    break;
                }
            }
        }


        /*
         * Después de guardar o actualizar
         * realizamos una nueva petición GET.
         */
        return "redirect:/estudiantes/listar";
    }


    /*
     * =====================================================
     * DELETE
     * =====================================================
     *
     * POST /estudiantes/eliminar/2
     *
     * Aunque conceptualmente estamos eliminando,
     * durante esta primera iteración utilizamos
     * POST porque trabajamos con formularios HTML.
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(

            /*
             * @PathVariable obtiene el ID
             * desde la URL.
             */
            @PathVariable
            int id) {


        /*
         * removeIf elimina todos los elementos
         * que cumplan la condición indicada.
         */
        listaEstudiantes.removeIf(

            estudiante ->
                estudiante.getId() == id

        );


        return "redirect:/estudiantes/listar";
    }

}