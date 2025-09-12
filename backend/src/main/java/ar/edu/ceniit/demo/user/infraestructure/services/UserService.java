package ar.edu.ceniit.demo.user.infraestructure.services;

import ar.edu.ceniit.demo.user.aplication.ports.input.UserUseCases;
import ar.edu.ceniit.demo.user.aplication.ports.output.UserOutputs;
import ar.edu.ceniit.demo.user.aplication.ports.output.identity_provider.IPOutputs;
import ar.edu.ceniit.demo.user.aplication.usecases.UserUseCasesImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService extends UserUseCasesImpl {
    public UserService(UserOutputs userOutputs, IPOutputs ipOutputs) {
        super(userOutputs, ipOutputs);
    }
}
