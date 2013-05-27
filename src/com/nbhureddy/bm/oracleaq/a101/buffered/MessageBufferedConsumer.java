package com.nbhureddy.bm.oracleaq.a101.buffered;

import java.sql.Connection;
import java.util.Properties;

import com.nbhureddy.bm.oracleaq.a101.util.ConstantUtils;
import com.nbhureddy.bm.oracleaq.a101.util.DBConnector;

import oracle.jdbc.aq.AQDequeueOptions;
import oracle.jdbc.aq.AQDequeueOptions.VisibilityOption;
import oracle.jdbc.aq.AQMessage;
import oracle.jdbc.aq.AQNotificationEvent;
import oracle.jdbc.aq.AQNotificationListener;
import oracle.jdbc.aq.AQNotificationRegistration;
import oracle.jdbc.driver.OracleConnection;

public class MessageBufferedConsumer implements AQNotificationListener {
	static {
		initializeConsumer();
	}

	public static void initializeConsumer() {
		MessageBufferedConsumer consumer = new MessageBufferedConsumer();
		try {
			Properties globalOptions = new Properties();

			String[] queueNameArray = new String[] { ConstantUtils.BUFFERED_QUEUE_NAME
					+ ":" + ConstantUtils.BUFFERED_SUBSCRIBER_NAME };
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
			options.setVisibility(VisibilityOption.IMMEDIATE);
			options.setDeliveryFilter(AQDequeueOptions.DeliveryFilter.BUFFERED);
			options.setConsumerName(ConstantUtils.BUFFERED_SUBSCRIBER_NAME);

			System.out.println("Reading the message: ");
			connection = DBConnector.getDataBaseConnection();
			AQMessage a = ((OracleConnection) connection).dequeue(
					ConstantUtils.BUFFERED_QUEUE_NAME, options,
					ConstantUtils.MSG_DATA_TYPE_RAW);

			System.out.println("Message: " + a);
			if (a != null) {
				byte[] bytes = a.getRAWPayload().getBytes();
				String payload = new String(bytes);
				System.out.println(payload);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
