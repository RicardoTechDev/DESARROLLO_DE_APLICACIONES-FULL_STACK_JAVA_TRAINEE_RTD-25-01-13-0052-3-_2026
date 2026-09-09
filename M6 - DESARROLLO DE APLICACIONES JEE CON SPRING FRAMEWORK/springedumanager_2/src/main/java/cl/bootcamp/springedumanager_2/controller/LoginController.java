package cl.bootcamp.springedumanager_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/")
	public String irLogin() {
		return "redirect:/login";//Redirigir hacia /login
	}
	
	@GetMapping("/login")
	public String mostrarLogin() {
		return "login";//templates --> login.html
	}
}
