package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;


public class    UserNotFoundException extends UserBaseException {
    public UserNotFoundException() {
        super("Usuario no encontrado");
    }

    public UserNotFoundException(String s, Throwable e) {
        super(s, e);
    }
}
