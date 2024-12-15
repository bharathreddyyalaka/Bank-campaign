package com.bank.marketing.campaign_service.repository;

import com.bank.marketing.campaign_service.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign,Long> {
}
