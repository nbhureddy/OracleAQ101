package com.nbhureddy.bm.oracleaq.a101;

import java.sql.Connection;
import java.util.Properties;

import com.nbhureddy.bm.oracleaq.a101.util.ConstantUtils;
import com.nbhureddy.bm.oracleaq.a101.util.DBConnector;

import oracle.jdbc.aq.AQDequeueOptions;
import oracle.jdbc.aq.AQMessage;
import oracle.jdbc.aq.AQNotificationEvent;
import oracle.jdbc.aq.AQNotificationListener;
import oracle.jdbc.aq.AQNotificationRegistration;
import oracle.jdbc.aq.AQDequeueOptions.DequeueMode;
import oracle.jdbc.aq.AQDequeueOptions.NavigationOption;
import oracle.jdbc.driver.OracleConnection;

/**
 * Client registers for the Queue Notification. The onAQNotification() is
 * invoked when a new message is enqueued
 * 
 * Run this class as a Java Program and then run the MessageProducer. 
 *  The console will show the message
 * @author naveenb
 * 
 */
public class MessageConsumer implements AQNotificationListener {

	static {
		initializeConsumer();
	}

	public static void initializeConsumer() {
		MessageConsumer consumer = new MessageConsumer();
		try {
			// register this thread as a listener to the task queue
			Properties globalOptions = new Properties();

			String[] queueNameArray = new String[] { ConstantUtils.QUEUE_NAME
					+ ":" + ConstantUtils.SUBSCRIBER_NAME };
			Properties[] options = new Properties[1];
			options[0] = new Properties();
			options[0].setProperty(OracleConnection.NTF_AQ_PAYLOAD, "true");

			// find the query and register this class with a listener
			Connection connection = DBConnector.initializeConnection();
			AQNotificationRegistration registration = ((OracleConnection) connection)
					.registerAQNotification(queueNameArray, options,
							globalOptions)[0];
			registration.addListener(consumer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("Listening for messages....");
	}

	@Override
	public void onAQNotification(AQNotificationEvent arg0) {
		Connection connection = null;
		try {
			AQDequeueOptions options = new AQDequeueOptions();
			options.setRetrieveMessageId(true);
			options.setNavigation(NavigationOption.FIRST_MESSAGE);
			options.setWait(AQDequeueOptions.DEQUEUE_NO_WAIT);
			options.setDequeueMode(DequeueMode.BROWSE);
			options.setConsumerName(ConstantUtils.SUBSCRIBER_NAME);

			System.out.println("Reading the message: ");
			connection = DBConnector.getDataBaseConnection();
			AQMessage a = ((OracleConnection) connection).dequeue(
					ConstantUtils.QUEUE_NAME, options,
					ConstantUtils.MSG_DATA_TYPE_RAW);

			System.out.println("Message: " + a);
			if (a != null) {
				byte[] bytes = a.getRAWPayload().getBytes();
				String payload = new String(bytes);
				System.out.println("----------------\nPayload: " + payload);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
