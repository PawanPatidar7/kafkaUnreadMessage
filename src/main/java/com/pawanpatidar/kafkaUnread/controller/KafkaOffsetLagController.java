package com.pawanpatidar.kafkaUnread.controller;

import com.pawanpatidar.kafkaUnread.service.KafkaOffsetLagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/kafka")
public class KafkaOffsetLagController {

	@Autowired
    private final KafkaOffsetLagService kafkaOffsetLagService;

    public KafkaOffsetLagController(KafkaOffsetLagService kafkaOffsetLagService) {
        this.kafkaOffsetLagService = kafkaOffsetLagService;
    }

    @GetMapping("/lag")
    public Map<String, Long> getOffsetLagInfo(@RequestParam("topic") String topic, @RequestParam("groupId") String groupId) {
        return kafkaOffsetLagService.getOffsetLagInfo(topic, groupId);
    }
}
