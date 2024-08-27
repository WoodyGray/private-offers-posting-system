package org.senla.woodygray.model;

import jakarta.persistence.*;
import lombok.Data;

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
