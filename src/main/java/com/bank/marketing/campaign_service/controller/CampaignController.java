package com.bank.marketing.campaign_service.controller;

import com.bank.marketing.campaign_service.clients.NotificationClient;
import com.bank.marketing.campaign_service.model.Campaign;
import com.bank.marketing.campaign_service.model.NotificationRequest;
import com.bank.marketing.campaign_service.service.CampaignService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService campaignService;
    @Autowired
    private final NotificationClient notificationClient;

    public CampaignController(CampaignService campaignService, NotificationClient notificationClient) {
        this.campaignService = campaignService;
        this.notificationClient = notificationClient;
    }


    @PostMapping
    public ResponseEntity<String> createCampaign(@RequestBody Campaign campaign) {
       // campaignService.createCampaign(campaign);
        return ResponseEntity.ok(campaignService.createCampaign(campaign));
    }



    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Campaign>> findCampaignByName(@PathVariable String name) {
        return ResponseEntity.ok(campaignService.findCampaignByName(name));
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

    @CircuitBreaker(name = "notificationService", fallbackMethod = "notificationFallback")
    @PostMapping("/{campaignId}/notify")
    public ResponseEntity<String> notifyUsers(@PathVariable Long campaignId, @RequestBody NotificationRequest notificationRequest) {
        // Create a sample notification request


        // Call the Notification Service
        String response = notificationClient.sendNotification(notificationRequest);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> notificationFallback(Long id, NotificationRequest notificationRequest, Throwable t) {
        return ResponseEntity.status(503).body("Notification Service is unavailable. Please try again later.");
    }
}
