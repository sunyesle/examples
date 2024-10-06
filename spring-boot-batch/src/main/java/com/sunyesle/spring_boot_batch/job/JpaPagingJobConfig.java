package com.sunyesle.spring_boot_batch.job;

import com.sunyesle.spring_boot_batch.entity.Post;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JpaPagingJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;

    public JpaPagingJobConfig(
            JobRepository jobRepository,
            @Qualifier("dataTransactionManager") PlatformTransactionManager transactionManager,
            EntityManagerFactory entityManagerFactory) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public Job jpaPagingJob() {
        return new JobBuilder("jpaPagingJob", jobRepository)
                .start(jpaPagingStep())
                .build();
    }

    @Bean
    public Step jpaPagingStep() {
        return new StepBuilder("jpaPagingStep", jobRepository)
                .<Post, Post>chunk(10, transactionManager)
                .reader(jpaPagingReader())
                .processor(jpaPagingProcessor())
                .writer(jpaPagingWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Post> jpaPagingReader() {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("viewCount", 1000);

        return new JpaPagingItemReaderBuilder<Post>()
                .name("jpaPagingReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("SELECT p FROM Post p WHERE p.viewCount >= :viewCount ORDER BY p.id")
                .parameterValues(parameterValues)
                .build();
    }

    @Bean
    public ItemProcessor<Post, Post> jpaPagingProcessor() {
        return post -> {
            post.setIsPopular(true);
            return post;
        };
    }

    @Bean
    public JpaItemWriter<Post> jpaPagingWriter() {
        return new JpaItemWriterBuilder<Post>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
