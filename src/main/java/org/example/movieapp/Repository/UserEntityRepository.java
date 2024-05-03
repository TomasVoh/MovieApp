package org.example.movieapp.Repository;

import org.example.movieapp.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findFirstByEmail(String email);
}
