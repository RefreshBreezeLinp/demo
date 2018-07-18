package com.web.demo.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.TransactionContext;

import javax.jms.*;
import javax.transaction.Transaction;

public class JmsServer {

    public static void main(String[] args) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.111:61616");
            Connection connection = connectionFactory.createConnection("admin","admin");
            connection.start();
            Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("queueTest");
            MessageProducer producer = session.createProducer(destination);
            for (int i= 0; i<5;i++){
                TextMessage message = session.createTextMessage();
                message.setText("message"+i);
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
