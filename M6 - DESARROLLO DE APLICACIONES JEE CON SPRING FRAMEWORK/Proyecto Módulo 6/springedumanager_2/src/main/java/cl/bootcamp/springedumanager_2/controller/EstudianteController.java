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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.bootcamp.springedumanager_2.exception.ReglaNegocioException;
import cl.bootcamp.springedumanager_2.model.Estudiante;
import cl.bootcamp.springedumanager_2.service.EstudianteService;


@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {
	/*
     * Ya no trabajamos con ArrayList.
     *
     * Controller
     *      ↓
     * Service
     */
	private final EstudianteService estudianteService;
    
	//Constructor
    public EstudianteController(EstudianteService estudianteService) {
    		this.estudianteService = estudianteService;
    
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
    public String listar(Model model) {
        /*
         * Enviamos la data hacia Thymeleaf.
         *
         * Conceptualmente es parecido a:
         *
         * request.setAttribute(...)
         */
        model.addAttribute(
                "listaEstudiantes", estudianteService.listar()
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
    /*
     * =====================================================
     * GUARDAR
     * =====================================================
     */
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Estudiante estudiante, RedirectAttributes redirectAttributes) {
        try {
            estudianteService.guardar(estudiante);
            
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            
            String msg = estudiante.getId() == 0
                    ? "Estudiante registrado correctamente."
                    : "Estudiante actualizado correctamente.";
            
            redirectAttributes.addFlashAttribute("mensaje", msg);

        }
        catch (ReglaNegocioException e) {
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
            redirectAttributes.addFlashAttribute("mensaje",e.getMessage());
        }

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
    public String eliminar() {
        return "redirect:/estudiantes/listar";
    }

}