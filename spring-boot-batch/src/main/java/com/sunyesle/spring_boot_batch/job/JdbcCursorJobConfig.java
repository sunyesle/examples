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
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class JdbcCursorJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    public JdbcCursorJobConfig(
            JobRepository jobRepository,
            @Qualifier("dataTransactionManager") PlatformTransactionManager transactionManager,
            @Qualifier("dataSource") DataSource dataSource) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.dataSource = dataSource;
    }

    @Bean
    public Job jdbcCursorJob() {
        return new JobBuilder("jdbcCursorJob", jobRepository)
                .start(jdbcCursorStep())
                .build();
    }

    @Bean
    public Step jdbcCursorStep() {
        return new StepBuilder("jdbcCursorStep", jobRepository)
                .<Post, Post>chunk(10, transactionManager)
                .reader(jdbcCursorReader())
                .processor(jdbcCursorProcessor())
                .writer(jdbcCursorWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Post> jdbcCursorReader() {
        return new JdbcCursorItemReaderBuilder<Post>()
                .name("jdbcCursorReader")
                .fetchSize(10)
                .dataSource(dataSource)
                .beanRowMapper(Post.class)
                .sql("SELECT id, title, view_count, is_popular FROM post WHERE view_count >= ?")
                .queryArguments(1000)
                .build();
    }

    @Bean
    public ItemProcessor<Post, Post> jdbcCursorProcessor() {
        return post -> {
            post.setIsPopular(true);
            return post;
        };
    }

    @Bean
    public ItemWriter<Post> jdbcCursorWriter() {
        return new JdbcBatchItemWriterBuilder<Post>()
                .dataSource(dataSource)
                .sql("UPDATE post SET is_popular = :isPopular WHERE id = :id")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .assertUpdates(true)
                .build();
    }
}
