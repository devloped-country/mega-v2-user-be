package com.app.mega.repository;

import com.app.mega.entity.Institution;
import com.app.mega.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository   extends JpaRepository<Institution, Long> {
    Institution findByUser(User user);
}
