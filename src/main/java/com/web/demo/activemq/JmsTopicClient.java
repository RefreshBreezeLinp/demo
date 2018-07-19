package com.web.demo.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsTopicClient {

    public static void main(String[] args) {
        try {
            //创建ConnectionFactory
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            //创建Connection
            Connection connection = connectionFactory.createConnection("admin","admin");
            //开启会话
            Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
            //目的地
            Destination destination = session.createTopic("topic1");
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            //开启链接
            connection.start();
            for (int i = 0;i<5; i++){
                TextMessage message = session.createTextMessage("message:"+i);
                producer.send(message);
            }
            session.commit();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
