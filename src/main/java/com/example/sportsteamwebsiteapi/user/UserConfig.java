package com.example.sportsteamwebsiteapi.user;

import com.example.sportsteamwebsiteapi.role.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner runner(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ADMIN"));
            userService.saveRole(new Role(null, "CONTENT_CREATOR"));
            
            userService.saveUser(
                new User(
                    "andrei.paval2@student.usv.ro",
                    "password",
                    null
                )
            );
            userService.saveUser(
                new User(
                    "cristi.guliciuc@student.usv.ro",
                    "password",
                    null
                )
            );
            userService.saveUser(
                new User(
                    "florin.palaghianu@student.usv.ro",
                    "password",
                    null
                )
            );
            userService.saveUser(
                new User(
                    "iulian.paranici@student.usv.ro",
                    "password",
                    null
                )
            );
            userService.saveUser(
                new User(
                    "bianca.tapoi@student.usv.ro",
                    "password",
                    null
                )
            );
            
            userService.setRoleToUser(
                "andrei.paval2@student.usv.ro",
                "ADMIN"
            );
            userService.setRoleToUser(
                "iulian.paranici@student.usv.ro",
                "ADMIN"
            );
            userService.setRoleToUser(
                "cristi.guliciuc@student.usv.ro",
                "CONTENT_CREATOR"
            );
            userService.setRoleToUser(
                "florin.palaghianu@student.usv.ro",
                "CONTENT_CREATOR"
            );
            userService.setRoleToUser(
                "bianca.tapoi@student.usv.ro",
                "CONTENT_CREATOR"
            );
        };
    }
}
