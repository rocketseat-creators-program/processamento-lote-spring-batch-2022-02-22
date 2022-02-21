package com.rocketseat.springbatchperformance.batch.info;

import com.rocketseat.springbatchperformance.model.Notification;
import com.rocketseat.springbatchperformance.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

public class InfoWriter implements ItemWriter<Notification> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void write(List<? extends Notification> users) throws Exception{
        System.out.println("Data Saved for Users: " + users);
        userRepository.saveAll(users);
    }
}
