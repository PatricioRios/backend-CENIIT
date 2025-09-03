package ar.edu.ceniit.demo.config;

import ar.edu.ceniit.demo.user.infraestructure.input.mapper.UserDTOMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public UserDTOMapper userDTOMapper() {
        return new UserDTOMapper();
    }
}
