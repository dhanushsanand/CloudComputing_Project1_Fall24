package com.dhanush.aws.sqsresources;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.ListQueuesRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SQSQueueEventsHandler 
{
	private static final AmazonSQS SQS = AmazonSQSClientBuilder.defaultClient();
	private static final String QUEUE_NAME = "testQueue";
	private static CreateQueueResult queueResult = null;
	private static final String TEST_MESSAGE = "This is a test message";
	
	private SQSQueueEventsHandler() 
	{
		
	}
	
	public static void createQueue() 
	{
		System.out.println("\nSending Resource Request to create SQS Queue");
		queueResult = SQS.createQueue(QUEUE_NAME);
		System.out.println("\nThe following queue was created with URL: "+ queueResult.getQueueUrl());
	}
	
	public static void listQueues()
	{
		ListQueuesRequest request = new ListQueuesRequest();
		request.setQueueNamePrefix(QUEUE_NAME);
		ListQueuesResult result = SQS.listQueues(request);
		List<String> queueUrls = result.getQueueUrls();
		if (queueUrls.isEmpty()) 
		{
			System.out.println("No SQS Queue Found");
		}
		queueUrls.stream().forEach(queueUrl -> System.out.println("\nThe following Queue URL was found:"+queueUrl) );
	}
	
	public static void sendMessage()
	{
		System.out.println("\nSending Message on SQS");
		SendMessageRequest sendMessageRequest = new SendMessageRequest(queueResult.getQueueUrl(),TEST_MESSAGE );
		Map<String, MessageAttributeValue> messageAttributesMap = new HashMap<>();
		messageAttributesMap.put("MessageName", new MessageAttributeValue().withDataType("String").withStringValue("test message"));
		sendMessageRequest.setMessageAttributes(messageAttributesMap);
		SQS.sendMessage(sendMessageRequest);
		System.out.println("\nMessage Sent");
	}

	public static void deleteQueue() 
	{
		SQS.deleteQueue(queueResult.getQueueUrl());
		System.out.println("\nDeleted queue:"+QUEUE_NAME);
	}
	
	public static void receiveQueueMessages()
	{
		ReceiveMessageResult messageResult =  SQS.receiveMessage(queueResult.getQueueUrl());
		List<Message> messages = messageResult.getMessages();
		
		for(Message message : messages)
		{
			System.out.println("\n"+message.getBody());
		}
	}
	
	public static void countOfMessages()
	{
		GetQueueAttributesResult result = SQS.getQueueAttributes(queueResult.getQueueUrl(),Arrays.asList("ApproximateNumberOfMessages") );
		Integer numberOfMessages = Integer.parseInt(result.getAttributes().get("ApproximateNumberOfMessages"));
		System.out.println("\nNumber of Messages in Queue:"+numberOfMessages); 
	}
}
