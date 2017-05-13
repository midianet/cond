package midianet.cond.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"midianet.cond"})
@EntityScan("midianet.cond.domain")
@EnableJpaRepositories("midianet.cond.repository")
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}