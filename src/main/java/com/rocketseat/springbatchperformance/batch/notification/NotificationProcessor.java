package com.rocketseat.springbatchperformance.batch.notification;

import com.rocketseat.springbatchperformance.model.Notification;
import org.springframework.batch.item.ItemProcessor;

public class NotificationProcessor implements ItemProcessor<Notification, Notification> {

    @Override
    public Notification process(Notification notification) throws Exception {
        System.out.println("process Thread = " + Thread.currentThread().getName());

        return notification;
    }
}
