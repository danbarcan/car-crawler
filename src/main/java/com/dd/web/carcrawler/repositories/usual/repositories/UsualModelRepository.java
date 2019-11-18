package com.dd.web.carcrawler.repositories.usual.repositories;

import com.dd.web.carcrawler.entities.usual.entities.UsualModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsualModelRepository extends JpaRepository<UsualModel, Long> {
}
