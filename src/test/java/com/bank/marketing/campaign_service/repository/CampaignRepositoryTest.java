package com.bank.marketing.campaign_service.repository;

import com.bank.marketing.campaign_service.model.Campaign;
import com.bank.marketing.campaign_service.model.CampaignStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class CampaignRepositoryTest {

    @Autowired
    CampaignRepository campaignRepository;

    Campaign campaign = new Campaign();

    @BeforeEach
    void setUP() {
        campaign.setName("credit_promos");
        campaign.setStartDate(LocalDateTime.of(2023, Month.DECEMBER, 25, 10, 30));
        campaign.setEndDate(LocalDateTime.of(2024, Month.DECEMBER, 24, 0, 0));
        campaign.setStatus(CampaignStatus.ACTIVE);
        campaign.setDescription("Credit card promotions");
        campaignRepository.save(campaign);
    }

    @Test
    public void testFindByName_Found() {
        // Add test code here
        List<Campaign> list = campaignRepository.findByName("credit_promos");
        assertThat(list.get(0).getName()).isEqualTo(campaign.getName());

    }

    @Test
    public void testFindByName_NotFound() {
        // Add test code here
        List<Campaign> list = campaignRepository.findByName("credit_promos1");
        //assertThat(list.get(0).getName()).isNotEqualTo("credit_promos1");
        assertThat(list.isEmpty()).isTrue();

    }
}
