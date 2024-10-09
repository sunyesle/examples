package com.sunyesle.spring_boot_batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
    public Step taskletStep(JobRepository jobRepository,
                            @Qualifier("dataTransactionManager") PlatformTransactionManager transactionManager) {
        return new StepBuilder("taskletStep", jobRepository)
                .tasklet(tasklet(null), transactionManager)
                .build();
    }

    @Bean
    @StepScope
    public Tasklet tasklet(@Value("#{jobParameters[date]}") String date) {
        return (contribution, chunkContext) -> {
            log.info("Running tasklet step - {}", date);
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("chunkStep", jobRepository)
                .<String, String>chunk(1, transactionManager)
                .reader(reader(null))
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<String> reader(@Value("#{jobParameters[count]}") Integer count) {
        return new ItemReader<>() {
            private int alreadyRead = 0;

            @Override
            public String read() {
                if (alreadyRead < (count != null ? count : 0)) {
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
