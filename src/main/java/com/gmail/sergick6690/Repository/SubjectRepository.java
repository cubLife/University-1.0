package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.universityModels.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    @Query("SELECT s FROM Subject s JOIN Item i ON s.id=i.subject.id and i.audience.id=(SELECT a.id FROM Audience a WHERE a.id=:id)")
    public List<Subject> findAllSubjectRelatedToAudience(@Param("id") int id);
}