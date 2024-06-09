package com.petito.project.repository;

import com.petito.project.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>
{
    User findByLogin(String login);
    Boolean existsByLogin(String login);
}
