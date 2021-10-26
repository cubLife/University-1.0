package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.universityModels.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    public List<Group> findAllByCathedra_Id(int cathedraId);
}
