package ar.edu.ceniit.demo.auth.infraestructure.services;

import ar.edu.ceniit.demo.auth.aplication.ports.output.AuthOutputs;
import ar.edu.ceniit.demo.auth.aplication.ports.output.CreateUserOnDomainOutput;
import ar.edu.ceniit.demo.auth.aplication.usecases.AuthUseCasesImpl;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends AuthUseCasesImpl {
    public AuthService(AuthOutputs authOutputs, CreateUserOnDomainOutput createUserOnDomainOutput) {
        super(authOutputs, createUserOnDomainOutput);
    }
}
