package org.bsuir.service;

import org.bsuir.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(Long id);
}
