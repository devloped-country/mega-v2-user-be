package com.app.mega.mapper;

import com.app.mega.dto.response.CurriculumResponse;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CurriculumMapper {
    //R
    List<CurriculumResponse> findAllContent(Long courseId, Long curriculumId);
    List<CurriculumResponse> findAllCurriculum(Long id);        //course_id로 정보

    List<CurriculumResponse> findCurriculum(Long id);           //curriculum_id로 정보

//    List<CurriculumResponse> updateCurriculum(Long id);
    void deleteDetailSubject(Long id);
    void updateCurriculum(Long id, String subject, Integer time, String startDate, String endDate);
    void insertDetailSubject(Long id, List<String> contents);

    void updateListOrder();
}
