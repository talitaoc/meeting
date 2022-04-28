package br.com.bootcamp.meetup.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

        @Bean
        public Docket docket() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("br.com.bootcamp.meetup.controller.resource"))
                    .paths(PathSelectors.any())
                    .build()
                    .apiInfo(apiInfo());
        }

        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("Meetups API")
                    .description("RestApi de meetup produzido no bootcamp de Java da Womakerscode")
                    .version("1.0")
                    .contact(contact())
                    .build();
        }

        private Contact contact() {
            return new Contact("Talita Orlando de Campos",
                    "https://www.linkedin.com/in/talita-orlando-de-campos-2b6634146/",
                    "talitaoc@gmail.com");
        }
    }

