package com.web.demo.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsTopicServer {

    public static void main(String[] args) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection("admin","admin");
            connection.setClientID("cc1");
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Topic destination = session.createTopic("topic1");
            TopicSubscriber topicSubscriber = session.createDurableSubscriber(destination,"t2");
            //MessageConsumer consumer = session.createConsumer(destination);
            connection.start();
            TextMessage message = (TextMessage) topicSubscriber.receive();
            while (message!=null){
                System.out.println(message.getText());
                message = (TextMessage) topicSubscriber.receive(100);
            }
            session.commit();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
