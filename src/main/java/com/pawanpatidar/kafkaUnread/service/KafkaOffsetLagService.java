package com.pawanpatidar.kafkaUnread.service;


import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Service
public class KafkaOffsetLagService {

    private final List<String> bootstrapServers = Arrays.asList("localhost:9092"); 

  
    public Map<String, Long> getOffsetLagInfo(String topic, String groupId) {
        Properties props = new Properties();
        props.put("bootstrap.servers", String.join(",", bootstrapServers));
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        List<TopicPartition> partitions = new ArrayList<>();
        consumer.partitionsFor(topic).forEach(partitionInfo -> {
            partitions.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));
        });

        consumer.assign(partitions);

        long totalMessages = 0;
        long totalReadMessages = 0;
        long totalUnreadMessages = 0;

        for (TopicPartition partition : partitions) {
           
        	OffsetAndMetadata committedOffset = consumer.committed(partition);
            long committed = (committedOffset != null) ? committedOffset.offset() : 0;

            consumer.seekToEnd(Collections.singleton(partition));
            long latestOffset = consumer.position(partition);

            totalMessages += latestOffset;
            totalReadMessages += committed;
            totalUnreadMessages += (latestOffset - committed);
        }

        consumer.close();

        Map<String, Long> result = new HashMap<>();
        result.put("totalMessages", totalMessages);
        result.put("totalReadMessages", totalReadMessages);
        result.put("totalUnreadMessages", totalUnreadMessages);

        return result;
    }
}
