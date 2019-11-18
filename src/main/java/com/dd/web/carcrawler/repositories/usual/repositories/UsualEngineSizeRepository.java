package com.dd.web.carcrawler.repositories.usual.repositories;

import com.dd.web.carcrawler.entities.usual.entities.UsualEngineSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsualEngineSizeRepository extends JpaRepository<UsualEngineSize, Long> {
}
