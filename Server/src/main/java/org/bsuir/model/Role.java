package org.bsuir.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Role {
    @Id
    @Column(name = "id_role")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;
    @Basic
    @Column(name = "role_name")
    private String roleName;
}
