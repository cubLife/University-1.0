package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.universityModels.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    @Query("SELECT COUNT(t) FROM Teacher t WHERE t.degree=:degree")
    public Long findTeachersCountWithEqualDegree(@Param("degree") String degree);

}
