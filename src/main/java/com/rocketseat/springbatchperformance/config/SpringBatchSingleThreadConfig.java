package com.rocketseat.springbatchperformance.config;

import com.rocketseat.springbatchperformance.batch.info.InfoProcessor;
import com.rocketseat.springbatchperformance.batch.info.InfoWriter;
import com.rocketseat.springbatchperformance.batch.notification.NotificationProcessor;
import com.rocketseat.springbatchperformance.batch.notification.NotificationReader;
import com.rocketseat.springbatchperformance.batch.notification.NotificationWriter;
import com.rocketseat.springbatchperformance.model.Notification;
import com.rocketseat.springbatchperformance.model.NotificationRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.concurrent.ThreadPoolExecutor;

//@Configuration
//@EnableBatchProcessing
public class SpringBatchSingleThreadConfig {

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public ItemWriter<Notification> notificationWriter() {return new NotificationWriter();}

    @Bean
    public ItemProcessor<Notification, Notification> notificationProcessor() {return new NotificationProcessor();}

    @Bean
    public NotificationReader notificationReader() {return new NotificationReader();}

    @Bean
    public ItemProcessor<Notification, Notification> infoUserProcessor() {
        return new InfoProcessor();
    }

    @Bean
    public ItemWriter<Notification> infoUserWriter() {
        return new InfoWriter();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("Extract, transform, load")
                .incrementer(new RunIdIncrementer())
                .start(flowLoadFileToDataBase())
                .next(flowGetNecessaryInformation())
                .next(flowSendNotificationToClient())
                .build()
                .build();
    }

    @Bean
    public Flow flowLoadFileToDataBase() {
        return new FlowBuilder<SimpleFlow>("flowLoadFileToDataBase")
                .start(steps.get("step-file-load-to-data-base")
                        .<Notification, Notification>chunk(1000)
                        .reader(notificationReader().reader())
                        .processor(notificationProcessor())
                        .writer(notificationWriter())
                        .build())
                .build();
    }

    @Bean
    public Flow flowGetNecessaryInformation() {
        return new FlowBuilder<SimpleFlow>("flowGetInfoToSendNotification")
                .start(steps.get("step-get-info")
                        .<Notification, Notification>chunk(10)
                        .reader(asyncReader(null))
                        .processor(infoUserProcessor())
                        .writer(infoUserWriter())
                        .build())
                .build();
    }

    @Bean
    public Flow flowSendNotificationToClient() {
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(taskExecutor())
                .add(flowSendSMS(), flowSendEmail())
                .build();
    }

    @Bean
    public Flow flowSendSMS() {
        return new FlowBuilder<SimpleFlow>("flowSendSMS")
                .start(steps.get("step-send-sms")
                        .tasklet((contribution, chunkContext) -> {
                            Thread.sleep(2500);
                            System.out.println(
                                    "Step Send SMS Completed. Thread = " + Thread.currentThread().getName());
                            return RepeatStatus.FINISHED;
                        })
                        .build())
                .build();
    }

    @Bean
    public Flow flowSendEmail() {
        return new FlowBuilder<SimpleFlow>("flowSendEmail")
                .start(steps.get("step-send-email")
                        .tasklet((contribution, chunkContext) -> {
                            Thread.sleep(2500);
                            System.out.println(
                                    "Step Send Email Completed. Thread = " + Thread.currentThread().getName());
                            return RepeatStatus.FINISHED;
                        })
                        .build())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("thread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();

        return executor;
    }

    @Bean
    public ItemReader<Notification> asyncReader(DataSource dataSource) {

        return new JdbcPagingItemReaderBuilder<Notification>()
                .name("Reader")
                .dataSource(dataSource)
                .selectClause("SELECT * ")
                .fromClause("FROM Notification ")
                .whereClause("WHERE ID <= 1000000 ")
                .sortKeys(Collections.singletonMap("ID", Order.ASCENDING))
                .rowMapper(new NotificationRowMapper())
                .build();
    }
}
