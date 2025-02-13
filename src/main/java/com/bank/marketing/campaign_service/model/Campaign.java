package com.bank.marketing.campaign_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Getter
@Setter
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String targetAudience;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Campaign campaign)) return false;
        return Objects.equals(id, campaign.id) && Objects.equals(name, campaign.name) && Objects.equals(description, campaign.description) && status == campaign.status && Objects.equals(startDate, campaign.startDate) && Objects.equals(endDate, campaign.endDate) && Objects.equals(targetAudience, campaign.targetAudience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, targetAudience);
    }
}

