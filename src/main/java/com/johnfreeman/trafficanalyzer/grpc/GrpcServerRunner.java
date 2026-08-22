package com.johnfreeman.trafficanalyzer.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class GrpcServerRunner {

    private static final Logger log = Logger.getLogger(GrpcServerRunner.class.getName());
    private static final int GRPC_PORT = 50051;

    private final PacketIngestServiceImpl packetIngestService;
    private Server server;

    public GrpcServerRunner(PacketIngestServiceImpl packetIngestService) {
        this.packetIngestService = packetIngestService;
    }

    @PostConstruct
    public void start() throws IOException {
        server = ServerBuilder.forPort(GRPC_PORT)
                .addService(packetIngestService)
                .build()
                .start();

        log.info("gRPC server started, listening on port " + GRPC_PORT);

        Thread grpcThread = new Thread(() -> {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        grpcThread.setDaemon(true);
        grpcThread.start();
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            log.info("Shutting down gRPC server");
            server.shutdown();
        }
    }
}
