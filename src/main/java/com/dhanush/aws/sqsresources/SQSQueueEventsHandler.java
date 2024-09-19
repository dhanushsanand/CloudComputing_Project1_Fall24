package com.dhanush.aws.sqsresources;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueResult;

public class SQSQueueEventsHandler {
	private static final AmazonSQS SQS = AmazonSQSClientBuilder.defaultClient();
	private static final String QUEUE_NAME = "testQueue";
	
	public static void createQueue() {
		CreateQueueResult queueResult = SQS.createQueue(QUEUE_NAME);
		String queueURL = queueResult.getQueueUrl();
	}

	public static void deleteQueue() {
		
	}
}
