package org.senla.woodygray.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role", schema = "public")
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleType roleName;
}
