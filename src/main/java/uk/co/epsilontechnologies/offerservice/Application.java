package uk.co.epsilontechnologies.offerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class Application extends WebMvcConfigurerAdapter {

    public static void main(final String... args) {
        SpringApplication.run(Application.class, args);
    }

}
