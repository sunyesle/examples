package com.sunyesle.spring_boot_batch.job;

import com.sunyesle.spring_boot_batch.entity.Post;
import com.sunyesle.spring_boot_batch.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
public class PopularPostJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final PostRepository postRepository;

    public PopularPostJobConfig(JobRepository jobRepository,
                                @Qualifier("dataTransactionManager") PlatformTransactionManager transactionManager,
                                PostRepository postRepository) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.postRepository = postRepository;
    }

    @Bean
    public Job popularPostJob() {
        return new JobBuilder("popularPostJob", jobRepository)
                .start(popularPostStep())
                .build();
    }

    @Bean
    public Step popularPostStep() {
        return new StepBuilder("popularPostStep", jobRepository)
                .<Post, Post>chunk(10, transactionManager)
                .reader(popularPostReader())
                .processor(popularPostProcessor())
                .writer(popularPostWriter())
                .build();
    }

    @Bean
    public RepositoryItemReader<Post> popularPostReader() {
        return new RepositoryItemReaderBuilder<Post>()
                .name("popularPostReader")
                .pageSize(10)
                .methodName("findByViewCountGreaterThanEqual")
                .arguments(1000)
                .repository(postRepository)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<Post, Post> popularPostProcessor() {
        return post -> {
            post.setIsPopular(true);
            return post;
        };
    }

    @Bean
    public ItemWriter<Post> popularPostWriter() {
        return new RepositoryItemWriterBuilder<Post>()
                .repository(postRepository)
                .methodName("save")
                .build();
    }
}
