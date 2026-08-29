package cl.bootcamp.springedumanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.bootcamp.springedumanager.model.Estudiante;


@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {
	private final List<Estudiante> estudiantes = new ArrayList<>(); 
	
	public EstudianteController() {
		estudiantes.add(new Estudiante(1, "Ana Pérez", "ana@correo.cl"));
		estudiantes.add(new Estudiante(2, "Juan Soto", "sotito@correo.cl"));
	}
	
	@GetMapping
	public String listar(Model model) {
		model.addAttribute("listaEstudiantes", estudiantes);
		
		return "estudiantes/lista";
	}
	
	/*
	@GetMapping("/editar")
	public String editar(Model model) {
		model.addAttribute("estudiantes", estudiantes);
		
		return "estudiantes/lista";
	}*/
}
