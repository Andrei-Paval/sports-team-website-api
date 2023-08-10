package com.example.sportsteamwebsiteapi.user;

import com.example.sportsteamwebsiteapi.role.Role;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @SequenceGenerator(
        name = "user_sequence",
        sequenceName = "user_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_sequence"
    )
    private Long id;
    private String username;
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
    
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
