package org.carincho.test.springboot.app;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/***
 *
 * Colocar solamente los del package por que spring boot tambien inclyne controladores de error
 * PathSelectors.ant("/api/cuentas/*") asi podria solo incluir con esta
 */
@Configuration
public class SpringFoxConfig {

    //Crear el componente Swagger en el contexto de spring

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.carincho.test.springboot.app.controllers"))
                .paths(PathSelectors.ant("/api/cuentas/*"))
                .build();//Devuelve un objeto ApiSelectorBuilder permite seleccionar cuales son las rutas
                          //por defecto busca todos las rutas que encuentre o se puede seleccionar por
                          // exppresiones regulares solo las rutas seleccionadas
    }

}
