package com.example;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

public class SQSMessageSender implements Runnable{
	
	private String queueUrl;
	private AmazonSQS sqs;
	
	public SQSMessageSender(String queueUrl,AmazonSQS sqs) {
		this.queueUrl = queueUrl;
		this.sqs = sqs;
	}

	public void run() {
		
		long tid = Thread.currentThread().getId();
		SendMessageRequest send_msg_request = new SendMessageRequest()
		        .withQueueUrl(queueUrl)
		        .withMessageBody("hello world " + tid)
		        .withDelaySeconds(5);
		SendMessageResult smr = sqs.sendMessage(send_msg_request);
		System.out.println("Message Id :" + smr.getMessageId());
		
	}

}
