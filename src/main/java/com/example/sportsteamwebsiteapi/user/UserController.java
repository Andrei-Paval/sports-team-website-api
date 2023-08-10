package com.example.sportsteamwebsiteapi.user;

import com.example.sportsteamwebsiteapi.role.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }
    
    @PostMapping()
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        User newUser = userService.saveUser(user);
        return ResponseEntity.created(URI.create("")).body(newUser);
    }
    
    @PostMapping("/createRole")
    public ResponseEntity<?> saveRole(@RequestBody Role role) {
        Role newRole = userService.saveRole(role);
        return ResponseEntity.created(URI.create("")).body(newRole);
    }
    
    @PostMapping("/setRole")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.setRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}
