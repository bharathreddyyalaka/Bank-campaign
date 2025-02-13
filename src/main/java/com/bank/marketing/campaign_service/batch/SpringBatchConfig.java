package com.bank.marketing.campaign_service.batch;

import com.bank.marketing.campaign_service.model.Campaign;
import jakarta.transaction.Transactional;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

//    @Bean
//    public FlatFileItemReader reader(){
//        return new FlatFileItemReaderBuilder<Campaign>()
//                .name("CampaignItemReader").build();
//    }



//    @Bean
//    Job job(JobRepository jobRepository, Step step){
//        return new JobBuilder("importPersons", jobRepository).start(step).build();
//    }

//    @Bean
//    Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
//
//        return new StepBuilder("importing-file-step", jobRepository)
//                .<Campaign, Campaign>chunk(10, platformTransactionManager)
//                .build();
//    }
}
