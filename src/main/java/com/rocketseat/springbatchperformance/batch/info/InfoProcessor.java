package com.rocketseat.springbatchperformance.batch.info;

import com.rocketseat.springbatchperformance.model.Notification;
import org.springframework.batch.item.ItemProcessor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class InfoProcessor implements ItemProcessor<Notification, Notification> {

    private static final Map<Integer, String> TRACKING_CODE = new HashMap<>();

    public InfoProcessor() {
        TRACKING_CODE.put(120, "Em transito");
        TRACKING_CODE.put(100, "Em preparação para envio");
        TRACKING_CODE.put(130, "Destinatário ausente");
        TRACKING_CODE.put(200, "A Caminho do destino final");
        TRACKING_CODE.put(400, "Entrega finalizada com sucesso");
    }

    @Override
    public Notification process(Notification notification) {

        var tracking = notification.getTracking();
        var message = TRACKING_CODE.get(tracking);
        notification.setMessageTracking(message);

        System.out.printf("Converted from [%s] to [%s] T=" + Thread.currentThread().getName()+ " %n", tracking, message);

        notification.setSurname(generateRandomWords());
        notification.setOrderNumber(new Random().nextInt(1000));

        System.out.printf("Get Info name: [%s] surname:[%s]%n", notification.getName(), notification.getSurname());
        System.out.printf("Get Info Order: [%s] ", notification.getOrderNumber());

        notification.setTime(new Date());


        return notification;
    }

    public static String generateRandomWords() {
        Random random = new Random();
        char[] word = new char[random.nextInt(8) + 3];
        for (int index = 0; index < word.length; index++) {
            word[index] = (char) ('a' + random.nextInt(26));
        }
        return new String(word);
    }
}
