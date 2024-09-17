package com.sunyesle.spring_boot_batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class SimpleJobConfig {

    @Bean
    public Job simpleJob(JobRepository jobRepository, Step taskletStep) {
        return new JobBuilder("simpleJob", jobRepository)
                .start(taskletStep)
                .build();
    }

    @Bean
    public Step taskletStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("taskletStep", jobRepository)
                .tasklet(tasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            log.info("Running tasklet step");
            return RepeatStatus.FINISHED;
        };
    }
}
