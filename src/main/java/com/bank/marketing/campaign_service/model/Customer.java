package com.bank.marketing.campaign_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("Index")
    private int id_in_file;

    @JsonProperty("User Id")
    private String user_id;

    @JsonProperty("First Name")
    private String first_name;

    @JsonProperty("Last Name")
    private String last_name;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Phone")
    private String phone_number;

    @JsonProperty("Sex")
    private String sex;

    @JsonProperty("Date of birth")
    private LocalDate birth_date;

    @JsonProperty("Job Title")
    private String job_description;
}
