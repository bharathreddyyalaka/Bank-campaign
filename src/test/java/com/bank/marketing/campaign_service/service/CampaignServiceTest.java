package com.bank.marketing.campaign_service.service;

import com.bank.marketing.campaign_service.model.Campaign;
import com.bank.marketing.campaign_service.model.CampaignStatus;
import com.bank.marketing.campaign_service.repository.CampaignRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;


class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;
    @InjectMocks
    private CampaignService campaignService;

    Campaign campaign = new Campaign();
    Campaign newCampaign = new Campaign();
    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
       // campaignService = new CampaignService(campaignRepository);

        campaign.setId(1L);
        campaign.setName("credit_promos");
        campaign.setStartDate(LocalDateTime.of(2023, Month.DECEMBER, 25, 10, 30));
        campaign.setEndDate(LocalDateTime.of(2024, Month.DECEMBER, 24, 0, 0));
        campaign.setStatus(CampaignStatus.ACTIVE);
        campaign.setDescription("Credit card promotions");


        newCampaign.setDescription("updated campaign");
        campaign.setStatus(CampaignStatus.CANCELLED);
//        campaignRepository.save(campaign);
    }


    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createCampaign() {
        mock(Campaign.class);
        mock(CampaignRepository.class);

        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));
        when(campaignRepository.save(campaign)).thenReturn(campaign);

        assertThat(campaignService.createCampaign(campaign)).isEqualTo("Successfully created campaign"+campaign.getId());
    }

    @Test
    void findCampaignByName() {
            List<Campaign> result = List.of(campaign);

            when(campaignRepository.findByName("credit_promos")).thenReturn(result);
            List<Campaign> resultFromMethod = campaignService.findCampaignByName("credit_promos");

            verify(campaignRepository, times(1)).findByName("credit_promos");
            assertEquals(resultFromMethod.get(0).getName(), campaign.getName());
            assertEquals(resultFromMethod.get(0).getDescription(), campaign.getDescription());
            assertNotEquals(null, campaign.getDescription());

            Assertions.assertNotNull(resultFromMethod, "the list must not be empty");

    }

    @Test
    void getAllCampaigns() {
        Campaign campaign2  = new Campaign();
        campaign2.setId(2L);
        campaign2.setName("debitCard_promos");
        campaign2.setDescription("will activate");
        campaign2.setStatus(CampaignStatus.ACTIVE);

        List<Campaign> list = new ArrayList<>();
        list.add(campaign);
        list.add(campaign2);
        when(campaignRepository.findAll()).thenReturn(list);

        List<Campaign> result = campaignService.getAllCampaigns();
        assertNotEquals(result.isEmpty(), !result.isEmpty());
        verify(campaignRepository, times(1)).findAll();
    }


    @Test
    void updateCampaign() {
//        mock(Campaign.class);
//        mock(CampaignRepository.class);

        when(campaignRepository.findById(1L)).thenReturn(Optional.ofNullable(campaign));

        when(campaignRepository.save(campaign)).thenReturn(newCampaign);

        //assertThat(campaignService.updateCampaign(1L, campaign)).isEqualTo(campaign);

        assertEquals("updated campaign", newCampaign.getDescription());
    }

    @Test
    void deleteCampaign() {
    }
}