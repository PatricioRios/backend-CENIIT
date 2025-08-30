package ar.edu.ceniit.demo.user.infraestructure.input;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping()
    public String createUser() {
        return "User created";
    }
}
