package com.johnfreeman.trafficanalyzer.grpc;

import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

@Component
public class PacketIngestServiceImpl extends PacketIngestGrpc.PacketIngestImplBase {

    private static final Logger log = Logger.getLogger(PacketIngestServiceImpl.class.getName());

    private final List<PacketEvent> receivedEvents = new CopyOnWriteArrayList<>();

    public List<PacketEvent> getReceivedEvents() {
        return Collections.unmodifiableList(receivedEvents);
    }

    @Override
    public StreamObserver<IngestMessage> streamPackets(StreamObserver<IngestSummary> responseObserver) {
        return new StreamObserver<IngestMessage>() {
            long packetsReceived = 0;
            long alertsReceived = 0;

            @Override
            public void onNext(IngestMessage message) {
                if (message.hasSessionInfo()) {
                    SessionInfo info = message.getSessionInfo();
                    log.info("Session started: " + info.getSessionId()
                            + " on interface " + info.getInterfaceName());
                } else if (message.hasPacketEvent()) {
                    PacketEvent event = message.getPacketEvent();
                    receivedEvents.add(event);
                    packetsReceived++;

                    if (event.getAlertsCount() > 0) {
                        alertsReceived += event.getAlertsCount();
                        log.warning("ALERT packet #" + packetsReceived + ": "
                                + event.getSourceIp() + " -> " + event.getDestIp()
                                + " (" + event.getAlerts(0) + ")");
                    } else if (packetsReceived % 100 == 0) {
                        log.info("...received " + packetsReceived + " packets so far");
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                log.severe("Stream error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                log.info("Stream closed. Total: " + packetsReceived
                        + " packets, " + alertsReceived + " alerts");

                IngestSummary summary = IngestSummary.newBuilder()
                        .setPacketsReceived(packetsReceived)
                        .setAlertsReceived(alertsReceived)
                        .build();

                responseObserver.onNext(summary);
                responseObserver.onCompleted();
            }
        };
    }
}
