package com.sunyesle.spring_boot_batch.job;

import com.sunyesle.spring_boot_batch.entity.Post;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JdbcPagingJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    public JdbcPagingJobConfig(JobRepository jobRepository,
                               @Qualifier("dataTransactionManager") PlatformTransactionManager transactionManager,
                               @Qualifier("dataSource") DataSource dataSource) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.dataSource = dataSource;
    }

    @Bean
    public Job jdbcPagingJob() {
        return new JobBuilder("jdbcPagingJob", jobRepository)
                .start(jdbcPagingStep())
                .build();
    }

    @Bean
    public Step jdbcPagingStep() {
        return new StepBuilder("jdbcPagingStep", jobRepository)
                .<Post, Post>chunk(10, transactionManager)
                .reader(jdbcPagingReader())
                .processor(jdbcPagingProcessor())
                .writer(jdbcPagingWriter())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<Post> jdbcPagingReader() {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("viewCount", 1000);

        return new JdbcPagingItemReaderBuilder<Post>()
                .name("jdbcPagingReader")
                .dataSource(dataSource)
                .selectClause("SELECT id, title, view_count, is_popular")
                .fromClause("FROM post")
                .whereClause("WHERE view_count >= :viewCount")
                .sortKeys(Map.of("id", Order.ASCENDING))
                .rowMapper(new BeanPropertyRowMapper<>(Post.class))
                .pageSize(10)
                .parameterValues(parameterValues)
                .build();
    }

    @Bean
    public ItemProcessor<Post, Post> jdbcPagingProcessor() {
        return post -> {
            post.setIsPopular(true);
            return post;
        };
    }

    @Bean
    public ItemWriter<Post> jdbcPagingWriter() {
        return new JdbcBatchItemWriterBuilder<Post>()
                .dataSource(dataSource)
                .sql("UPDATE post SET is_popular = :isPopular WHERE id = :id")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .assertUpdates(true)
                .build();
    }
}
