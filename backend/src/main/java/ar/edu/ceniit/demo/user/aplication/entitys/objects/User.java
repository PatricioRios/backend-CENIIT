package ar.edu.ceniit.demo.user.aplication.entitys.objects;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Builder
@NoArgsConstructor
public class User {
    //Propiedades del sistema
    private UUID uuid; //-> Existe en DB del dominio
    private Long id ; //-> Existe en DB del dominio
    private String username; //-> Existe en DB del dominio
    private String email; //-> Existe en DB del dominio
    //private String password; //-> NO Existe en DB del dominio

    //propiedades del dominio
    private String firstName; //-> Existe en DB del dominio
    private Optional<String> secondName; //-> Existe en DB del dominio
    private String lastName; //-> Existe en DB del dominio
    private Optional<String> secondLastName; //-> Existe en DB del dominio
    private Integer dni; //-> Existe en DB del dominio
    //Propiedades de DB (?
    private Instant createdAt; //-> Existe en DB del dominio
    private Instant updatedAt; //-> Existe en DB del dominio
    public enum Field {

        UUID("uuid"),
        USERNAME("username"),
        EMAIL("email"),
        FIRST_NAME("firstName"),
        LAST_NAME("lastName"),
        DNI("dni"),
        CREATED_AT("createdAt"),
        UPDATED_AT("updatedAt");

        private final String fieldName;

        Field(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        @Override
        public String toString() {
            return this.fieldName;
        }
        public void algo(){

        }
    }

    //public boolean verifyPassword() {//TODO
    //    String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
    //    return this.password.matches(passwordRegex);
    //}
    private boolean verifyEmail(String email) {
        // Verifica que el email tenga un formato válido
        if(email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
    public boolean verifyEmail() {
        return verifyEmail(this.email);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Optional<String> getSecondName() {
        return secondName;
    }

    public void setSecondName(Optional<String> secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Optional<String> getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(Optional<String> secondLastName) {
        this.secondLastName = secondLastName;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void verifyUser() throws
            EmailBadFormat,
            UserNameBadFormat,
            FirstNameBadFormat,
            SecondNameBadFormat,
            LastNameBadFormat,
            SecondLastNameBadFormat
            {



        if(!this.verifyEmail(this.email)){
            throw new EmailBadFormat();
        }
        if(this.username == null || this.username.isEmpty()){
            throw new UserNameBadFormat();
        }

//        //propiedades del dominio
        if(this.firstName == null || this.firstName.isEmpty()){
            throw new FirstNameBadFormat();
        }
        if(this.secondName.isPresent() && this.secondName != null){
            if(this.secondName.get().isEmpty() || this.secondName.get().isBlank()){
                throw new SecondNameBadFormat();
            }
        }
        if(this.lastName == null || this.lastName.isEmpty()){
            throw new LastNameBadFormat();
        }
        if(this.secondLastName.isPresent() && this.secondLastName != null){
            if(this.secondLastName.get().isEmpty() || this.secondLastName.get().isBlank())
                throw new SecondLastNameBadFormat();
        }

    }
    public class FirstNameBadFormat extends Exception {
        public FirstNameBadFormat() {
            super("The first name format is invalid");
        }
    }
    public class SecondNameBadFormat extends Exception {
        public SecondNameBadFormat() {
            super("The second name format is invalid");
        }
    }
    public class LastNameBadFormat extends Exception {
        public LastNameBadFormat() {
            super("The last name format is invalid");
        }
    }

    public class SecondLastNameBadFormat extends Exception {
        public SecondLastNameBadFormat() {
            super("The second last name format is invalid");
        }
    }

    public class UserNameBadFormat extends Exception {
        public UserNameBadFormat() {
            super("The username format is invalid");
        }
    }

    public class EmailBadFormat extends Exception {
        public EmailBadFormat() {
            super("The email format is invalid");
        }
    }

    public User(UUID uuid, Long id, String username, String email, String firstName, Optional<String> secondName, String lastName, Optional<String> secondLastName, Integer dni, Instant createdAt, Instant updatedAt) {
        this.uuid = uuid;
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.dni = dni;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
