package com.example.dao;

import com.example.entity.Role;

public interface RoleRepository {
    Role findRoleById(int id);
}
