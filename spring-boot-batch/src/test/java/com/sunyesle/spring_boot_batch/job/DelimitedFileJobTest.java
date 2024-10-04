package com.sunyesle.spring_boot_batch.job;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@SpringBatchTest
class DelimitedFileJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Qualifier("delimitedFileJob")
    @Autowired
    private Job job;

    @Test
    void test() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("inputFile", "classpath:data/book.csv")
                .toJobParameters();

        jobLauncherTestUtils.setJob(job);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }
}
