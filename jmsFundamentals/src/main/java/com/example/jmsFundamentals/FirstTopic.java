package com.example.jmsFundamentals;

import org.springframework.boot.SpringApplication;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstTopic {

    public static void main(String[] args) throws NamingException, JMSException {

        SpringApplication.run(FirstTopic.class, args);

        InitialContext initialContext = new InitialContext();

        Topic topic = (Topic) initialContext.lookup("topic/myTopic");

        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection();

        Session session = connection.createSession();

        MessageProducer messageProducer = session.createProducer(topic);
        MessageConsumer messageConsumer1 = session.createConsumer(topic);
        MessageConsumer messageConsumer2 = session.createConsumer(topic);

        TextMessage messageToSend = session.createTextMessage("I am the creator of this message");

        messageProducer.send(messageToSend);

        System.out.println("Message sent: " + messageToSend.getText());

        connection.start();

        TextMessage message1Received = (TextMessage) messageConsumer1.receive();
        System.out.println("Consumer1 message received: " + message1Received.getText());

        TextMessage message2Received = (TextMessage) messageConsumer2.receive();
        System.out.println("Consumer2 message received: " + message2Received.getText());

        session.close();
        connection.close();
        initialContext.close();
    }
}
