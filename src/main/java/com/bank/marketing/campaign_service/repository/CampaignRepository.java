package com.bank.marketing.campaign_service.repository;

import com.bank.marketing.campaign_service.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign,Long> {
    List<Campaign> findByName(String name);

    //The @Query annotation uses JPQL (Java Persistence Query Language) by default, not native SQL.
    // JPQL queries work with the entity and its fields, not the database tables directly.
    @Query("Select c from Campaign c where description = :description")
    Campaign findRecord(@Param("description")String description);

    //If you need to write a native SQL query, you can set nativeQuery = true in the @Query annotation:
    @Query(value = "SELECT * FROM Campaign WHERE name = :name", nativeQuery = true)
    Campaign findRecordByName(@Param("name") String name);


}
