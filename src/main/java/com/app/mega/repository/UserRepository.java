package com.app.mega.repository;

import com.app.mega.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

//어노테이션이 없어도 JpaRepository를 상속하면 IoC가 자동으로 등록됨.
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findByEmailAndPhoneAndName(String email, String phone, String name);

    @Query(value="SELECT is_identified FROM user WHERE email = :email AND phone = :phone AND name = :name", nativeQuery=true)
    Boolean identifyState(String email, String phone, String name);
}