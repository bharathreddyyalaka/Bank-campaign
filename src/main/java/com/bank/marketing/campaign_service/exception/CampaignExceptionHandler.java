package com.bank.marketing.campaign_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CampaignExceptionHandler {
    @ExceptionHandler(CampaignNotFoundException.class)
    public ResponseEntity<Object> handleCampaignNotFoundException(CampaignNotFoundException campaignNotFoundException){
        CampaignDetails campaignDetails = new CampaignDetails
                (campaignNotFoundException.getMessage(),
                        campaignNotFoundException.getCause(), org.springframework.http.HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(campaignDetails, HttpStatus.NOT_FOUND);
    }
}
