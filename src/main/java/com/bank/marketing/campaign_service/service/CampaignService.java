package com.bank.marketing.campaign_service.service;

import com.bank.marketing.campaign_service.exception.CampaignNotFoundException;
import com.bank.marketing.campaign_service.model.Campaign;
import com.bank.marketing.campaign_service.repository.CampaignRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {
    private final CampaignRepository campaignRepository;


    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public String createCampaign(Campaign campaign) {
         campaignRepository.save(campaign);
         return "Successfully created campaign"+ campaign.getId();
    }

    public List<Campaign> findCampaignByName(String name) {
        return campaignRepository.findByName(name);
    }
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public Campaign updateCampaign(Long id, Campaign updatedCampaign) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new CampaignNotFoundException("Campaign not found with id: " + id));
        campaign.setName(updatedCampaign.getName());
        campaign.setDescription(updatedCampaign.getDescription());
        campaign.setStatus(updatedCampaign.getStatus());
        campaign.setStartDate(updatedCampaign.getStartDate());
        campaign.setEndDate(updatedCampaign.getEndDate());
        campaign.setTargetAudience(updatedCampaign.getTargetAudience());

        return campaignRepository.save(campaign);
    }

    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }
}
