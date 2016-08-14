package com.example.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.example.pojo.Role;

@Transactional
public interface RoleRepository extends CrudRepository<Role, Long>
{

}
