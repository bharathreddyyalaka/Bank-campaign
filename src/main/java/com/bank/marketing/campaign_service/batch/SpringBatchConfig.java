package com.bank.marketing.campaign_service.batch;


import com.bank.marketing.campaign_service.model.Campaign;
import com.bank.marketing.campaign_service.model.Customer;
import com.bank.marketing.campaign_service.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Bean
    @StepScope
    public JsonItemReader<Customer> reader(@Value("#{jobParameters['filePath'] ?: ''}") String filePath) {
        System.out.println("DEBUG: Reader received file path = " + filePath);

        if (filePath.isEmpty()) {
            throw new IllegalArgumentException("Error: File path is missing in job parameters!");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalStateException("Error: File does not exist at path: " + filePath);
        }
//        ObjectMapper objectMapper = new ObjectMapper();
//        JacksonJsonObjectReader<Customer> jsonObjectReader = new JacksonJsonObjectReader<>(Customer.class);
//        jsonObjectReader.setMapper(objectMapper);
//        System.out.println("DEBUG: Creating JSON reader");
//        return new JsonItemReaderBuilder<Customer>()
//                .name("customerReader")
//                .resource(new FileSystemResource(file))
//                .jsonObjectReader(new JacksonJsonObjectReader<Customer>(Customer.class) {
//                    public Customer read(JsonParser jsonParser) throws IOException {
//                        JsonNode rootNode = jsonParser.readValueAsTree();
//                        JsonNode customersArray = rootNode.get("customers");  // Extract "customers" array
//                        if (customersArray == null || !customersArray.isArray()) {
//                            throw new IllegalStateException("Error: JSON does not contain 'customers' array.");
//                        }
//                        return objectMapper.treeToValue(customersArray, Customer.class);
//                    }
//                })
//                .strict(false)  // Allow flexible JSON parsing
//                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonJsonObjectReader<Customer> jsonObjectReader = new JacksonJsonObjectReader<>(Customer.class);
        objectMapper.registerModule(new JavaTimeModule());
        jsonObjectReader.setMapper(objectMapper); // Correctly setting ObjectMapper

        return new JsonItemReaderBuilder<Customer>()
                .name("customerReader")
                .resource(new FileSystemResource(file)) // Read file
                .jsonObjectReader(jsonObjectReader) // Uses properly configured Jackson reader
                .strict(false) // Allows flexible JSON parsing
                .build();
    }


    @Bean
    public ItemProcessor<Customer, Customer> processor() {
        return customer -> {
            // Custom processing logic
            customer.setEmail(customer.getEmail().toLowerCase());
            return customer;
        };
    }

    @Bean
    public ItemWriter<Customer> writer() {
        return customers -> {
            // Custom writing logic
            customerRepository.saveAll(customers);
        };
    }

    @Bean
    public Step customerStep() {
        System.out.println("DEBUG: Creating step");
        return new StepBuilder("customerStep", jobRepository)
                .<Customer, Customer>chunk(10, transactionManager)
                .reader(reader(null))
                .processor(processor())
                .writer(writer())
               // Enable multi-threading
                .build();
    }

    @Bean
    public Job customerJob() {
        System.out.println("DEBUG: Creating job");
        return new JobBuilder("customerJob", jobRepository)
                .start(customerStep())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.initialize();
        return executor;
    }
}
