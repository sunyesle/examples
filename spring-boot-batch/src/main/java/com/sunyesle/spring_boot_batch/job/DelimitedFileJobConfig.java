package com.sunyesle.spring_boot_batch.job;

import com.sunyesle.spring_boot_batch.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class DelimitedFileJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public DelimitedFileJobConfig(JobRepository jobRepository,
                                  @Qualifier("dataTransactionManager") PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job delimitedFileJob() {
        return new JobBuilder("delimitedFileJob", jobRepository)
                .start(delimitedFileStep())
                .build();
    }

    @Bean
    public Step delimitedFileStep() {
        return new StepBuilder("delimitedFileStep", jobRepository)
                .<Book, Book>chunk(10, transactionManager)
                .reader(delimitedFileReader(null))
                .processor(delimitedFileProcessor())
                .writer(delimitedFileWriter())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Book> delimitedFileReader(@Value("#{jobParameters[inputFile]}") Resource resource) {
        return new FlatFileItemReaderBuilder<Book>()
                .name("delimitedFileReader")
                .resource(resource)
                .delimited()
                .names("title", "price")
                .targetType(Book.class)
                .build();
    }

    @Bean
    public ItemProcessor<Book, Book> delimitedFileProcessor() {
        return book -> {
            book.setPrice((int) Math.round(book.getPrice() * 1.1));
            return book;
        };
    }

    @Bean
    public FlatFileItemWriter<Book> delimitedFileWriter() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = formatter.format(new Date());
        return new FlatFileItemWriterBuilder<Book>()
                .name("delimitedFileWriter")
                .resource(new FileSystemResource("./output/" + date + ".csv"))
                .delimited()
                .names("title", "price")
                .build();
    }
}
