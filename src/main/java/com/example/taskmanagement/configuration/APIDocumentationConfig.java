package com.example.taskmanagement.configuration;

import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * This class is responsible for configuring the OpenAPI documentation for the
 * Task and Tags API.
 * It provides information about the API, such as title, version, description,
 * terms of service,
 * license, and contact details.
 *
 * @author API Support Tomlee
 * @version 1.0
 */
@Configuration
public class APIDocumentationConfig {

  /**
   * This method creates and returns an instance of OpenAPI with the configured
   * API information.
   *
   * @return An instance of OpenAPI with the configured API information.
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Task and Tags Api Docs")
            .version("1.0")
            .description("A detailed description of Task-Tags API")
            .termsOfService("http://task-tags-policy.com")
            .license(new License()
                .name("Apache 2.0")
                .url("http://www.apache.org/licenses/LICENSE-2.0"))
            .contact(new Contact()
                .name("API Support Tomlee")
                .url("https://github.com/tomleejumah")
                .email("tommlyjumah@gmail.com")));
  }
}
