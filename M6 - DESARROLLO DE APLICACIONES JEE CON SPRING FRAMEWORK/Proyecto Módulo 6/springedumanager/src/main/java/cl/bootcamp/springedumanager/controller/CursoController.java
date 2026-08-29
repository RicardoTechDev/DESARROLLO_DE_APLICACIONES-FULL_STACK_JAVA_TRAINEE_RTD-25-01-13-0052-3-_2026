package cl.bootcamp.springedumanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.bootcamp.springedumanager.model.Curso;
import cl.bootcamp.springedumanager.model.Estudiante;

@Controller
@RequestMapping("/cursos")
public class CursoController {
	private final List<Curso> cursos = new ArrayList<>(); 
	
	public CursoController() {
		cursos.add(new Curso(1, "Java", "Bootcamp Full Stack Java"));
		cursos.add(new Curso(2, "Python", "Bootcamp Full Stack Python"));
	}
	
	@GetMapping
	public String listar(Model model) {
		model.addAttribute("listaCursos", cursos);
		
		return "cursos/lista";
	}
}
