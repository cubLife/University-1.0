package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.universityModels.Cathedra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CathedraRepository extends JpaRepository<Cathedra, Integer> {
}
