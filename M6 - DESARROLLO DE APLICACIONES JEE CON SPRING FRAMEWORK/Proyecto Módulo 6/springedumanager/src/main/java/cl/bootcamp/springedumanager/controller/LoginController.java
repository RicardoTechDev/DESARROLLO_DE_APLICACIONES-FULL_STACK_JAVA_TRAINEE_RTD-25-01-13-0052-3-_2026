package cl.bootcamp.springedumanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/")
	public String inicio() {
		return "redirect:/login";//redirigir hacia /login
	}                  
	
	@GetMapping("/login")
	public String mostarLogin() {
		return "login";//template login.html
	}
}
