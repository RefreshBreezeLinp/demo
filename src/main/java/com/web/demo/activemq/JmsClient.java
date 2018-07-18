package com.web.demo.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsClient {

    public static void main(String[] args) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection("admin","admin");
            connection.start();
            Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("queueTest");
            MessageConsumer consumer = session.createConsumer(destination);
            for (int i =0 ;i<5;i++){
                TextMessage message = (TextMessage) consumer.receive();
                System.out.println(message.getText());
            }
            session.commit();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}
