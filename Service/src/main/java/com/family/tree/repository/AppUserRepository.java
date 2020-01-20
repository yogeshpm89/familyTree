package com.family.tree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.family.tree.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

}
