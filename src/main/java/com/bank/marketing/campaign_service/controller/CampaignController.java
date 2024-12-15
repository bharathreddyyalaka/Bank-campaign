package com.bank.marketing.campaign_service.controller;

import com.bank.marketing.campaign_service.clients.NotificationClient;
import com.bank.marketing.campaign_service.model.Campaign;
import com.bank.marketing.campaign_service.model.NotificationRequest;
import com.bank.marketing.campaign_service.service.CampaignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService campaignService;
    private final NotificationClient notificationClient;

    public CampaignController(CampaignService campaignService, NotificationClient notificationClient) {
        this.campaignService = campaignService;
        this.notificationClient = notificationClient;
    }


    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        return ResponseEntity.ok(campaignService.createCampaign(campaign));
    }

    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id, @RequestBody Campaign campaign) {
        return ResponseEntity.ok(campaignService.updateCampaign(id, campaign));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{campaignId}/notify")
    public ResponseEntity<String> notifyUsers(@PathVariable Long campaignId) {
        // Create a sample notification request
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setCampaignId(campaignId);
        notificationRequest.setTitle("Campaign Notification");
        notificationRequest.setMessage("Check out our new campaign!");
        notificationRequest.setRecipient("user@example.com");

        // Call the Notification Service
        String response = notificationClient.sendNotification(notificationRequest);

        return ResponseEntity.ok(response);
    }

}
