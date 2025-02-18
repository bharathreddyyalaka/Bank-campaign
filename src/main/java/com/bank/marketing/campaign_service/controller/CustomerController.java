package com.bank.marketing.campaign_service.controller;

import com.bank.marketing.campaign_service.model.Customer;
import com.bank.marketing.campaign_service.service.CustomerService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;

    /*This variable is the directory where file uploaded is saved to sent it to batch */
    private static final String TMP_DIR = System.getProperty("java.io.tmpdir");


    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    /* Here it will accept MultipartFile which extends InputStreamSource
    *       ***
    *
    * */
    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> createCustomer(@RequestParam MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            System.out.println("DEBUG: Received file = " + file.getOriginalFilename()); // Log file name
            System.out.println("DEBUG: Content-Type = " + file.getContentType()); // Log content type
            if(!file.getContentType().equals("application/json")) {
                response.put("error", "Only JSON files are allowed");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(response);
            }
//                return ResponseEntity.badRequest().body("Only JSON files are allowed");
//            }
            /*
            * we can also try
            * Path targetPath = Paths.get("/var/batch-files/" + file.getOriginalFilename());
              Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            * */
            String filePath = TMP_DIR + File.separator + file.getOriginalFilename();

            file.transferTo(new File(filePath));
            System.out.println("DEBUG: File saved at = " + filePath); // Log file path
            // Start batch job with filePath as parameter
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("filePath", filePath)
                    .addLong("time", System.currentTimeMillis()) // Unique Job Instance
                    .toJobParameters();
            System.out.println("DEBUG: Running Batch Job with filePath = " + filePath);
            System.out.println("DEBUG: Running Batch Job...");

            JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            System.out.println("DEBUG: Batch Job Status = " + jobExecution.getStatus());
            response.put("message", "File uploaded successfully");
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (Exception e) {

            System.out.println("DEBUG: " + e.getMessage());
            e.printStackTrace();

            response.put("error", "Error in uploading file");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(response);
        }
    }
}
