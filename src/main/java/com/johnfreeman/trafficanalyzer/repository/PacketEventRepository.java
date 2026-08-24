package com.johnfreeman.trafficanalyzer.repository;

import com.johnfreeman.trafficanalyzer.entity.PacketEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PacketEventRepository extends JpaRepository<PacketEventEntity, Long> {

    List<PacketEventEntity> findBySourceIp(String sourceIp);

    List<PacketEventEntity> findByProtocol(String protocol);

    @Query("SELECT p FROM PacketEventEntity p WHERE p.timestampUnixMs BETWEEN :start AND :end")
    List<PacketEventEntity> findByTimestampRange(@Param("start") Long start, @Param("end") Long end);
}
