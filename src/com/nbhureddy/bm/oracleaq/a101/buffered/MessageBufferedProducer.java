package com.nbhureddy.bm.oracleaq.a101.buffered;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.nbhureddy.bm.oracleaq.a101.util.ConstantUtils;
import com.nbhureddy.bm.oracleaq.a101.util.DBConnector;

import oracle.jdbc.aq.AQEnqueueOptions;
import oracle.jdbc.aq.AQFactory;
import oracle.jdbc.aq.AQMessage;
import oracle.jdbc.aq.AQEnqueueOptions.DeliveryMode;
import oracle.jdbc.aq.AQEnqueueOptions.VisibilityOption;
import oracle.jdbc.aq.AQMessageProperties;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.RAW;

public class MessageBufferedProducer {
	
	public static void main(String [] args) {
		MessageBufferedProducer producer = new MessageBufferedProducer();
		System.out.println("Enqueuing Message : ");
		producer.produceMessage();
		System.out.println("Message enqueued : ");
	}
	
	private  void produceMessage() {
		AQEnqueueOptions enqueueOptions = new AQEnqueueOptions();
		try {
			enqueueOptions.setDeliveryMode(DeliveryMode.BUFFERED);
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
			((OracleConnection) connection).enqueue(ConstantUtils.BUFFERED_QUEUE_NAME, enqueueOptions, mesg);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
