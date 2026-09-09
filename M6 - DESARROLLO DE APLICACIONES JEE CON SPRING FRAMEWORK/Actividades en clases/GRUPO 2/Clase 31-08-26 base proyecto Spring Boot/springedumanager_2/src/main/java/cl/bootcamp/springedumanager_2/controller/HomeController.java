package cl.bootcamp.springedumanager_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/home")
	public String inicio() {
		
		//response.sendRedirect(request.getContextPath()+ "/libros.jsp");
		return "index";
	}
	
	
}
