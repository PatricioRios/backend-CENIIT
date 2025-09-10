package ar.edu.ceniit.demo.config;

import ar.edu.ceniit.demo.user.aplication.ports.input.UserUseCases;
import ar.edu.ceniit.demo.user.aplication.ports.output.UserOutputs;
import ar.edu.ceniit.demo.user.aplication.ports.output.identity_provider.IPOutputs;
import ar.edu.ceniit.demo.user.aplication.usecases.UserUseCasesImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public UserUseCases userUseCases(UserOutputs userOutputs, IPOutputs ipOutputs) {
        return new UserUseCasesImpl(userOutputs, ipOutputs);
    }
}
