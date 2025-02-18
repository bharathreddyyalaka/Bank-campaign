package com.bank.marketing.campaign_service.service;

import com.bank.marketing.campaign_service.model.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    public void createCustomer(Customer customer);
}
