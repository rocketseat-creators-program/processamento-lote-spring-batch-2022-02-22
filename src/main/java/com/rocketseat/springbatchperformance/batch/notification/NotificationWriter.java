package com.rocketseat.springbatchperformance.batch.notification;

import com.rocketseat.springbatchperformance.repository.UserRepository;
import com.rocketseat.springbatchperformance.model.Notification;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NotificationWriter implements ItemWriter<Notification> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void write(List<? extends Notification> users) throws Exception{
        System.out.println("Data Saved for Users: " + users);
        userRepository.saveAll(users);
    }
}
