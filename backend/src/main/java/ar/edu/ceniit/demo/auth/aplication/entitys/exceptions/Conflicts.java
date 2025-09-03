package ar.edu.ceniit.demo.auth.aplication.entitys.exceptions;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBaseException;

public class Conflicts extends AuthException {
    public Conflicts(String message) {
        super(message);
    }
}
