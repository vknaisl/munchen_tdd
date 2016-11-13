package cz.knaisl.munchen_tdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cz.knaisl.munchen_tdd"})
public class Application {

    public static void main(String[] args) {
        String property = System.getProperties().getProperty("spring.profiles.active");
        if (property == null) {
            System.setProperty("spring.profiles.active", Profiles.PROD);
        }
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }
}
