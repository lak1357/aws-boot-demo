package com.example;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class Main {

	private static String QUEUE_URL = "https://sqs.ap-southeast-1.amazonaws.com/XXXXXXXXX/TEST_JAVA";

	public static void main(String[] args) {

		AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

		for (int i = 0; i < 20; i++) {
			Thread t1 = new Thread(new SQSMessageSender(QUEUE_URL, sqs));
			t1.start();
		}
		
		
		Thread readerThread =  new Thread(new SQSMessageReader(QUEUE_URL, sqs));
		readerThread.start();

	}

}
