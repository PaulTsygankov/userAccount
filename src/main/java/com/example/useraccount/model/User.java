package com.example.useraccount.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", unique = true, length = 30)
    @Size(min = 2, message = "Минимальная длина должна составлять 2 символа")
    @Size(max = 30, message = "Максимальная длина должна составлять 30 символов")
    private String username;

    @Column(name = "password", length = 255)
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "email", length = 30)
    @Size(min = 2, message = "Минимальная длина должна составлять 2 символа")
    @Size(max = 30, message = "Максимальная длина должна составлять 30 символов")
    private String email;

    @Column(name = "name", length = 30)
    @Size(min = 2, message = "Минимальная длина должна составлять 2 символа")
    @Size(max = 30, message = "Максимальная длина должна составлять 30 символов")
    private String name;

    @Column(name = "surname", length = 30)
    @Size(min = 2, message = "Минимальная длина должна составлять 2 символа")
    @Size(max = 30, message = "Максимальная длина должна составлять 30 символов")
    private String surname;

    @Column(name = "role", length = 10)
    private String role;
}
