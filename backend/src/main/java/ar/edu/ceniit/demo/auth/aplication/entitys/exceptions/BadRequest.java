package ar.edu.ceniit.demo.auth.aplication.entitys.exceptions;

import ar.edu.ceniit.demo.auth.aplication.ports.input.AuthUseCases;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBaseException;

public class BadRequest extends AuthException {
    public BadRequest(String message) {
        super(message);
    }
}
