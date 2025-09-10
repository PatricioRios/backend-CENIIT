package ar.edu.ceniit.demo.user.aplication.ports.input.dtos;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.BadRequestOnUpdateUserException;

import java.util.Optional;
import java.util.UUID;

public class UpdateUserResponseDTO {
    private UUID uuid; //UUID.fromString("00000000-0000-0000-0000-000000000000");//-> No Updatable (pero necesario)
    private Optional<String> username;//-> No Updatable
    private Optional<String> email;
    // String password;-> No Updatable
    ////propiedades del dominio
    private Optional<String> firstName;
    private Optional<String> secondName;//-> Opcional
    private Optional<String> lastName;
    private Optional<String> secondLastName;//-> Opcional
    private Optional<Integer> dni;
    public UpdateUserResponseDTO(
            UUID uuid,
            Optional<String> email,
            Optional<String> firstName,
            Optional<String> secondName,
            Optional<String> lastName,
            Optional<String> secondLastName,
            Optional<Integer> dni,
            Optional<String> username
            ){
        this.uuid = uuid;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.dni = dni;
        this.username = username;
    }
    public void validateUser() throws BadRequestOnUpdateUserException {
        if(this.uuid==null){
            throw new BadRequestOnUpdateUserException(BadRequestOnUpdateUserException.Reason.INVALID_UUID);
        }

        if(this.email.isPresent() && this.email.get().isBlank()) {
            if(!this.email.get().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
                throw new BadRequestOnUpdateUserException(BadRequestOnUpdateUserException.Reason.INVALID_EMAIL);
        }

        if(this.firstName.isPresent() && this.firstName.get().isBlank()){
            throw new BadRequestOnUpdateUserException(BadRequestOnUpdateUserException.Reason.INVALID_FIRST_NAME);
        }

        if(this.secondName.isPresent() && this.secondName.get().isBlank()){
            throw new BadRequestOnUpdateUserException(BadRequestOnUpdateUserException.Reason.INVALID_SECOND_NAME);
        }

        if(this.lastName.isPresent() && this.lastName.get().isBlank()){
            throw new BadRequestOnUpdateUserException(BadRequestOnUpdateUserException.Reason.INVALID_LAST_NAME);
        }

        if(this.secondLastName.isPresent() && this.secondLastName.get().isBlank()){
            throw new BadRequestOnUpdateUserException(BadRequestOnUpdateUserException.Reason.INVALID_SECOND_LAST_NAME);
        }
        if(this.dni.isPresent() && this.dni.get()<=0){
            throw new BadRequestOnUpdateUserException(BadRequestOnUpdateUserException.Reason.INVALID_DNI);
        }
    }

    public Optional<String> getUsername() {
        return username;
    }

    public void setUsername(Optional<String> username) {
        this.username = username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(Optional<String> email) {
        this.email = email;
    }

    public Optional<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(Optional<String> firstName) {
        this.firstName = firstName;
    }

    public Optional<String> getSecondName() {
        return secondName;
    }

    public void setSecondName(Optional<String> secondName) {
        this.secondName = secondName;
    }

    public Optional<String> getLastName() {
        return lastName;
    }

    public void setLastName(Optional<String> lastName) {
        this.lastName = lastName;
    }

    public Optional<String> getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(Optional<String> secondLastName) {
        this.secondLastName = secondLastName;
    }

    public Optional<Integer> getDni() {
        return dni;
    }

    public void setDni(Optional<Integer> dni) {
        this.dni = dni;
    }
}
