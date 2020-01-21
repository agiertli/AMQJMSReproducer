package org.redhat.reproducer.amqp;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmqpApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(AmqpApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AmqpApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		

		ConnectionFactory factory;
		Destination destination;
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;

		logger.info("AmqpApplication initialized");
		Hashtable<Object, Object> env = new Hashtable<Object, Object>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
		env.put("connectionfactory.bpmFactory", "amqp://127.0.0.1:5672");
		env.put("queue.bpmLookup", "bpmqueue");
		javax.naming.Context context = new javax.naming.InitialContext(env);
		factory = (ConnectionFactory) context.lookup("bpmFactory");
		destination = (Destination) context.lookup("bpmLookup");

		try {
			connection = factory.createConnection();
			connection.start();

			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			producer = session.createProducer(destination);

			TextMessage message = session.createTextMessage("sample event");
			producer.send(message);

			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
