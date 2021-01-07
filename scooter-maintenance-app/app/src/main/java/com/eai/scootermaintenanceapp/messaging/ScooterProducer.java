package com.eai.scootermaintenanceapp.messaging;

import com.eai.scootermaintenanceapp.data.model.Region;
import com.eai.scootermaintenanceapp.data.model.Scooter;
import com.swiftmq.amqp.AMQPContext;
import com.swiftmq.amqp.v100.client.AMQPException;
import com.swiftmq.amqp.v100.client.Connection;
import com.swiftmq.amqp.v100.client.ExceptionListener;
import com.swiftmq.amqp.v100.client.Producer;
import com.swiftmq.amqp.v100.client.QoS;
import com.swiftmq.amqp.v100.client.Session;
import com.swiftmq.amqp.v100.generated.messaging.message_format.AmqpValue;
import com.swiftmq.amqp.v100.messaging.AMQPMessage;
import com.swiftmq.amqp.v100.types.AMQPString;

import java.util.Date;

public class ScooterProducer {
    private static final String LOG_TAG = ScooterProducer.class.getSimpleName();

    private final Region region;
    private final String hostName;
    private final Integer port;

    private boolean isProducing = false;

    private Connection connection;
    private Session session;
    private Producer producer;

    ScooterProducer(Region region, String hostName, Integer port) {
        this.region = region;
        this.hostName = hostName;
        this.port = port;
    }

    public void startProducing() {
        isProducing = true;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    AMQPContext ctx = new AMQPContext(AMQPContext.CLIENT);

                    connection = new Connection(ctx, "192.168.178.22", 61616, false);
                    connection.setContainerId("maintenanceAppProducer");
                    connection.setExceptionListener(new ExceptionListener() {
                        public void onException(Exception e) {
                            e.printStackTrace();
                        }
                    });

                    connection.connect();

                    session = connection.createSession(50, 50);
                    producer = session.createProducer(region.getId(), QoS.AT_LEAST_ONCE);

                    while (isProducing) {
                        try {
                            AMQPMessage message = new AMQPMessage();
                            message.setAmqpValue(new AmqpValue(new AMQPString(new Date().toString())));

                            producer.send(message);
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void produce(Scooter scooter) {
        // TODO: implement
    }

    public void stopProducing() {
        isProducing = false;
    }

    public void close() {
        try {
            producer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
