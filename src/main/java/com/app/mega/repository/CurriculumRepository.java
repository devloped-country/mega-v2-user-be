package com.app.mega.repository;

import com.app.mega.entity.Course;
import com.app.mega.entity.Curriculum;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    @Override
    ArrayList<Curriculum> findAll();

    @Query(value="select count(*) from curriculum where course_id = :courseId", nativeQuery = true)
    int prevCurriculumCount(Long courseId);

    List<Curriculum> findAllByCourse(Course course);

    List<Curriculum> findAllBycourse_id(Long id);

    Curriculum findByCourseIdAndId(Long id, Long curriculumId);

    void deleteById(Long id);
}
