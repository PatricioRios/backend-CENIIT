package ar.edu.ceniit.demo.config;

import ar.edu.ceniit.demo.auth.aplication.ports.output.CreateUserOnDomainOutput;
import ar.edu.ceniit.demo.user.infraestructure.output.OutputsMockUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserOutputConfig {

    @Bean
    public CreateUserOnDomainOutput createUserOnDomainOutput() {
        return new OutputsMockUser();
    }
}
