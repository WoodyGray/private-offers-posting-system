package org.senla.woodygray.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "hash_password")
    private String hashPassword;
    @Column(name = "rating")
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role")
    private Role role;

}
