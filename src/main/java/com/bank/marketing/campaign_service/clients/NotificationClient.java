package com.bank.marketing.campaign_service.clients;

import com.bank.marketing.campaign_service.model.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="notification-service")
public interface NotificationClient {

    @PostMapping("/notifications/send")
    String sendNotification(@RequestBody NotificationRequest notificationRequest);
}

//