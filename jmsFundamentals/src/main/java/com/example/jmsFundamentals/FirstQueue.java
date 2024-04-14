package com.example.jmsFundamentals;

import org.springframework.boot.SpringApplication;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {

    public static void main(String[] args) throws NamingException, JMSException {

        SpringApplication.run(FirstQueue.class, args);

        InitialContext initialContext = new InitialContext();

        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection();

        Session session = connection.createSession();

        MessageProducer messageProducer = session.createProducer(queue);

        TextMessage messageToSend = session.createTextMessage("I am the creator of this message");

        messageProducer.send(messageToSend);

        System.out.println("Message sent: " + messageToSend.getText());

        connection.start();

        MessageConsumer messageConsumer = session.createConsumer(queue);

        TextMessage messageReceived = (TextMessage) messageConsumer.receive(5000);

        System.out.println("The message received: " + messageReceived.getText());

        session.close();
        connection.close();
        initialContext.close();
    }
}

