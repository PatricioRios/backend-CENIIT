package ar.edu.ceniit.demo.user.aplication.ports.input.dtos;

public class CreateUserInput {
    private String username;
    private String password;

    public CreateUserInput(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
