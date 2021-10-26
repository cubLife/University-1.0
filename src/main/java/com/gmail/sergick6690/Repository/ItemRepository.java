package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.universityModels.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
