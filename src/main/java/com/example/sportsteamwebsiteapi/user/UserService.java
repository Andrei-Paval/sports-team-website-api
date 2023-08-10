package com.example.sportsteamwebsiteapi.user;

import com.example.sportsteamwebsiteapi.role.Role;
import com.example.sportsteamwebsiteapi.role.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
    public UserService(
        UserRepository userRepo,
        RoleRepository roleRepo
    ) {
        this.userRepository = userRepo;
        this.roleRepository = roleRepo;
    }
    
    public User saveUser(User user) throws RuntimeException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "User already exists"
            );
        }
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }
    
    public Role saveRole(Role role) throws RuntimeException {
        if (roleRepository.findByName(role.getName()) != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Role already exists"
            );
        }
        return roleRepository.save(role);
    }
    
    public void setRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.setRole(role);
    }
    
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }
    
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
