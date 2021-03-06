import org.apache.activemq.artemis.jms.client.ActiveMQTopicConnectionFactory;

import javax.jms.*;

public class Consumer {

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.consume();
        consumer.cleanup();
    }

    private MessageConsumer consumer;
    private Session session;
    private Connection connection;

    public Consumer()
    {
        try {

            // Create a ConnectionFactory
            ActiveMQTopicConnectionFactory connectionFactory = new ActiveMQTopicConnectionFactory("tcp://artemis:61616");

            // Create a Connection
            connection = connectionFactory.createConnection("default", "default");
            connection.setClientID("statboardAdapter");
            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Topic destination = session.createTopic("scooters");

            // Create a MessageConsumer from the Session to the Topic or Queue
            consumer = session.createDurableSubscriber(destination, "statboardAdapter");
        }  catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }

    }

    public Message consume(){
         try {
            // Wait for a message
            Message message = consumer.receive(10000);

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println("Received: " + text);
            } else {
                System.out.println("Received: " + message);
            }
            return message;
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
         return null;
    }

    public void cleanup() {
        try {
            this.consumer.close();
            this.session.close();
            this.connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
