package ar.edu.ceniit.demo.config;

import ar.edu.ceniit.demo.auth.aplication.ports.input.AuthUseCases;
import ar.edu.ceniit.demo.auth.aplication.ports.input.RegisterNewUserUseCase;
import ar.edu.ceniit.demo.auth.aplication.ports.output.AuthOutputs;
import ar.edu.ceniit.demo.auth.aplication.ports.output.CreateUserOnDomainOutput;
import ar.edu.ceniit.demo.auth.aplication.usecases.AuthUseCasesImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthUseCaseConfig {

    @Bean
    public AuthUseCases authUseCases(AuthOutputs authOutputs, CreateUserOnDomainOutput createUserOnDomainOutput) {
        return new AuthUseCasesImpl(authOutputs, createUserOnDomainOutput);
    }

}
