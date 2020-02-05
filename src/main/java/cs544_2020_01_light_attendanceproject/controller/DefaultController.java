package cs544_2020_01_light_attendanceproject.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Secured({"ROLE_STUDENT", "ROLE_FACULTY", "ROLE_ADMIN"})
public class DefaultController {

	
	@GetMapping
	public void loadIndex(HttpServletResponse httpResponse) throws Exception {
	   //String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		//httpResponse.sendRedirect("/user/"+currentUsername);
		httpResponse.sendRedirect("/swagger-ui.html");
	}
}
