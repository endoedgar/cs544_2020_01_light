package cs544_2020_01_light_attendanceproject;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.models.Contact;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AttendanceProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceProjectApplication.class, args);
    }

    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()  
          .apis(RequestHandlerSelectors.basePackage("cs544_2020_01_light_attendanceproject"))
          //.apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());                                           
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo(
          "CS544 EA Attendance API", 
          "Implementation of Attendance API by Light group, CS544 ", 
          "LIGHT TEAM", 
          "Terms of service", 
          null, 
          "", 
          "www.miu.edu", Collections.emptyList());
    }
}
