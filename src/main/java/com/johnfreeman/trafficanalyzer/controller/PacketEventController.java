package com.johnfreeman.trafficanalyzer.controller;

import com.johnfreeman.trafficanalyzer.entity.PacketEventEntity;
import com.johnfreeman.trafficanalyzer.repository.PacketEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/packets")
public class PacketEventController {

    @Autowired
    private PacketEventRepository repository;

    @GetMapping
    public List<PacketEventEntity> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<PacketEventEntity> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping("/by-source-ip/{ip}")
    public List<PacketEventEntity> getBySourceIp(@PathVariable String ip) {
        return repository.findBySourceIp(ip);
    }

    @GetMapping("/by-protocol/{protocol}")
    public List<PacketEventEntity> getByProtocol(@PathVariable String protocol) {
        return repository.findByProtocol(protocol);
    }

    @GetMapping("/by-time-range")
    public List<PacketEventEntity> getByTimeRange(
            @RequestParam Long start,
            @RequestParam Long end) {
        return repository.findByTimestampRange(start, end);
    }
}
