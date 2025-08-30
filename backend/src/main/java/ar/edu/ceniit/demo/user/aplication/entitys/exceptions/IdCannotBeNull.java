package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;

public class IdCannotBeNull extends UserBaseException {
    public IdCannotBeNull() {
        super("UUID no puede ser nulo");
    }
}
