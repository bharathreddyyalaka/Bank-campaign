package com.bank.marketing.campaign_service.exception;


public class CampaignNotFoundException extends RuntimeException
{
    public CampaignNotFoundException(String message) {
        super(message);
    }

    public CampaignNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
