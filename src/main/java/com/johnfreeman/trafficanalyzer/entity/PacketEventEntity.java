package com.johnfreeman.trafficanalyzer.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "packet_events")
public class PacketEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_ip", nullable = false, length = 45)
    private String sourceIp;

    @Column(name = "dest_ip", nullable = false, length = 45)
    private String destIp;

    @Column(name = "source_port")
    private Integer sourcePort;

    @Column(name = "dest_port")
    private Integer destPort;

    @Column(name = "protocol", nullable = false, length = 20)
    private String protocol;

    @Column(name = "length_bytes", nullable = false)
    private Integer lengthBytes;

    @Column(name = "tcp_flags", length = 20)
    private String tcpFlags;

    @Column(name = "timestamp_unix_ms", nullable = false)
    private Long timestampUnixMs;

    @Column(name = "alerts", columnDefinition = "text[]")
    private List<String> alerts;

    public PacketEventEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSourceIp() { return sourceIp; }
    public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }

    public String getDestIp() { return destIp; }
    public void setDestIp(String destIp) { this.destIp = destIp; }

    public Integer getSourcePort() { return sourcePort; }
    public void setSourcePort(Integer sourcePort) { this.sourcePort = sourcePort; }

    public Integer getDestPort() { return destPort; }
    public void setDestPort(Integer destPort) { this.destPort = destPort; }

    public String getProtocol() { return protocol; }
    public void setProtocol(String protocol) { this.protocol = protocol; }

    public Integer getLengthBytes() { return lengthBytes; }
    public void setLengthBytes(Integer lengthBytes) { this.lengthBytes = lengthBytes; }

    public String getTcpFlags() { return tcpFlags; }
    public void setTcpFlags(String tcpFlags) { this.tcpFlags = tcpFlags; }

    public Long getTimestampUnixMs() { return timestampUnixMs; }
    public void setTimestampUnixMs(Long timestampUnixMs) { this.timestampUnixMs = timestampUnixMs; }

    public List<String> getAlerts() { return alerts; }
    public void setAlerts(List<String> alerts) { this.alerts = alerts; }
}
