package com.example;

import java.util.List;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;

public class SQSMessageReader implements Runnable {

	private String queueUrl;
	private AmazonSQS sqs;
	
	public SQSMessageReader(String queueUrl,AmazonSQS sqs) {
		this.queueUrl = queueUrl;
		this.sqs = sqs;
	}

	public void run() {https://aws.amazon.com/eclipse 

		while (true) {
			List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();
			System.out.println("Message Count : " + messages.size());
			
			for (Message message : messages) {
				
				System.out.println("Message :" +  message.getBody());
				sqs.deleteMessage(queueUrl, message.getReceiptHandle());
				
			}
			
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
