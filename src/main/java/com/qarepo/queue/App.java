package com.qarepo.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qarepo.queue.model.QueueMessage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class App {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        QueueMessage message = new QueueMessage("AALB2986152", "FAKEJOB2 1361570770 HTML5 Banners"
                , "http://zpreview.ztrac.com/clients/583f191395dbd78ca1a2dae63a87acdc");

        mapper.writeValue(new File("banner.json"), message);
        String jsonMsg = mapper.writeValueAsString(message);
        System.out.println(jsonMsg);

        RabbitMqPublisher publisher = new RabbitMqPublisher();
       /* publisher.publish(publisher.getConnection(), "", message.getJobName()
                , jsonMsg + "\n{\"date/time\": " + "\"" + LocalDateTime.now() + "\"}");*/

        RabbitMqConsumer consumer = new RabbitMqConsumer();
        consumer.consume(publisher.getConnection(), message.getJobNumber());
    }
}
