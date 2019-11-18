package com.dd.web.carcrawler.repositories.usual.repositories;

import com.dd.web.carcrawler.entities.usual.entities.UsualFuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsualFuelTypeRepository extends JpaRepository<UsualFuelType, Long> {
}
