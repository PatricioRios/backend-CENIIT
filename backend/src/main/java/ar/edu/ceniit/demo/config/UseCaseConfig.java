package ar.edu.ceniit.demo.config;

import ar.edu.ceniit.demo.user.aplication.ports.input.UserUseCases;
import ar.edu.ceniit.demo.user.aplication.ports.output.OutputsUser;
import ar.edu.ceniit.demo.user.aplication.usecases.UserUseCasesImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public UserUseCases userUseCases(OutputsUser userOutputs) {
        return new UserUseCasesImpl(userOutputs);
    }
}
