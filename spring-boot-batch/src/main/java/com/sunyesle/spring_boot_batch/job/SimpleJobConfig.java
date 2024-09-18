package com.sunyesle.spring_boot_batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class SimpleJobConfig {

    @Bean
    public Job simpleJob(JobRepository jobRepository, Step taskletStep, Step chunkStep) {
        return new JobBuilder("simpleJob", jobRepository)
                .start(taskletStep)
                .next(chunkStep)
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

    @Bean
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("chunkStep", jobRepository)
                .<String, String>chunk(1, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemReader<String> reader() {
        return new ItemReader<String>() {
            private int alreadyRead = 0;

            @Override
            public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                if (alreadyRead < 5) {
                    alreadyRead++;
                    return "Hello, Spring Batch!";
                } else {
                    return null;
                }
            }
        };
    }

    @Bean
    public ItemProcessor<String, String> processor() {
        return String::toUpperCase;
    }

    @Bean
    public ItemWriter<String> writer() {
        return item -> item.forEach(log::info);
    }
}
