package com.example.DATN.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "username" , length = 100)
    String username;
    @Column(name = "password" , length = 256)
    String password;
    @Column(name = "fullname")
    String fullName;
    @Column(name = "sdt" , length = 12)
    String sdt;
    @Column(name = "email")
    String email;
    @Column(name = "diachi")
    String diaChi;
    @Column(name = "trangthai")
    int trangThai;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    Collection<Role> roles;

    
}
