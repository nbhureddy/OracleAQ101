package com.nbhureddy.bm.oracleaq.a101;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.nbhureddy.bm.oracleaq.a101.util.ConstantUtils;
import com.nbhureddy.bm.oracleaq.a101.util.DBConnector;

import oracle.jdbc.aq.AQAgent;
import oracle.jdbc.aq.AQEnqueueOptions;
import oracle.jdbc.aq.AQFactory;
import oracle.jdbc.aq.AQMessage;
import oracle.jdbc.aq.AQEnqueueOptions.DeliveryMode;
import oracle.jdbc.aq.AQEnqueueOptions.VisibilityOption;
import oracle.jdbc.aq.AQMessageProperties;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.RAW;

/**
 * Message Producer will generate a RAW payload message
 * @author nbhureddy
 */
public class MessageProducer {
	
	public static void main(String [] args) {
		MessageProducer producer = new MessageProducer();
		System.out.println("Enqueuing Message for Publish-Subscript model: ");
		producer.produceMessageSubscriber();
		System.out.println("Message enqueued for Publish-Subscript model: ");
		
		System.out.println("Enqueuing Message for Point-to-Point: ");
		producer.produceMessagePointToPoint();
		System.out.println("Message enqueued for Point-to-Point: ");
	}
	
	/**
	 * This method enqueue's  RAW payload data into queue
	 *    An agent is not specified here since this is a Subscription model
	 *    Make sure the enqueue is started and a subscriber has subscribed to this queue - or an exception is thrown 
	 */
	private  void produceMessageSubscriber() {
		
		try {
			AQEnqueueOptions enqueueOptions = new AQEnqueueOptions();
			enqueueOptions.setDeliveryMode(DeliveryMode.PERSISTENT);
			enqueueOptions.setVisibility(VisibilityOption.IMMEDIATE);
			AQMessageProperties msgprop =  AQFactory.createAQMessageProperties();
			msgprop.setPriority(1);
			msgprop.setExpiration(0);
			AQMessage mesg = AQFactory.createAQMessage(msgprop);
			
			String strMsg = "Current Time is :" + new Date(System.currentTimeMillis()).toString();
			System.out.println("Payload: " + strMsg);
			RAW rawData = new RAW(strMsg.getBytes());
			mesg.setPayload(rawData);
			
			Connection connection = DBConnector.getDataBaseConnection();
			((OracleConnection) connection).enqueue(ConstantUtils.QUEUE_NAME, enqueueOptions, mesg);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method enqueue's RAW payload data into queue
	 *   The agent to whom the message is intended is specified
	 *   
	 */
	private  void produceMessagePointToPoint() {
		
		try {
			AQEnqueueOptions enqueueOptions = new AQEnqueueOptions();
			enqueueOptions.setDeliveryMode(DeliveryMode.PERSISTENT);
			enqueueOptions.setVisibility(VisibilityOption.IMMEDIATE);
			
			AQMessageProperties msgprop =  AQFactory.createAQMessageProperties();
			msgprop.setPriority(1);
			msgprop.setExpiration(0);
			
			//AQAgent parameter 
			AQAgent aqAgent = AQFactory.createAQAgent();
			aqAgent.setName(ConstantUtils.AGENT_NAME);
			AQAgent[] recipientList = new AQAgent[1];
			recipientList[0] = aqAgent;	
			msgprop.setRecipientList(recipientList);
			
			AQMessage mesg = AQFactory.createAQMessage(msgprop);
			
			String strMsg = "Current Time is :" + new Date(System.currentTimeMillis()).toString();
			System.out.println("Payload: " + strMsg);
			RAW rawData = new RAW(strMsg.getBytes());
			mesg.setPayload(rawData);
			
			Connection connection = DBConnector.getDataBaseConnection();
			((OracleConnection) connection).enqueue(ConstantUtils.QUEUE_NAME, enqueueOptions, mesg);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
