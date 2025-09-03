package ar.edu.ceniit.demo.auth.aplication.ports.input.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserForDomain {
    //Propiedades del sistema
    private UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private Integer id ;
    private String username;
    private String email = "";
    private String password;

    //propiedades del dominio
    private String firstName;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private Integer dni;
    //Propiedades de DB (?
    private Instant createdAt;
    private Instant updatedAt;

    public boolean verifyPassword() {//TODO
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        return this.password.matches(passwordRegex);
    }
    private boolean verifyEmail(String email) {
        // Verifica que el email tenga un formato válido
        if(email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return this.email;
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
    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getSecondLastName() {
        return secondLastName;
    }
    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }
    public Integer getDni() {
        return dni;
    }
    public void setDni(Integer dni) {
        this.dni = dni;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}