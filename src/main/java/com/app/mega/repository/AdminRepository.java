package com.app.mega.repository;

import com.app.mega.entity.Admin;
import com.app.mega.entity.Institution;
import com.app.mega.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//어노테이션이 없어도 JpaRepository를 상속하면 IoC가 자동으로 등록됨.
public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findAllByInstitution(Institution institution);
}
