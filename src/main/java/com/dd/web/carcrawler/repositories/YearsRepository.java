package com.dd.web.carcrawler.repositories;

import com.dd.web.carcrawler.entities.Years;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearsRepository extends JpaRepository<Years, Long> {
}
