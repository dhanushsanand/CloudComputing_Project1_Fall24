package com.dhanush.aws.sqsresources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueResult;
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
		queueResult = SQS.createQueue(QUEUE_NAME);
		System.out.println("The following queue was created with queue URL: "+ queueResult.getQueueUrl());
	}
	
	public static void listQueues()
	{
		ListQueuesRequest request = new ListQueuesRequest();
		request.setQueueNamePrefix(QUEUE_NAME);
		ListQueuesResult result = SQS.listQueues(request);
		result.getQueueUrls().forEach(System.out::println);
	}
	
	public static void sendMessage()
	{
		SendMessageRequest sendMessageRequest = new SendMessageRequest(queueResult.getQueueUrl(),TEST_MESSAGE );
		Map<String, MessageAttributeValue> messageAttributesMap = new HashMap<>();
		messageAttributesMap.put("MessageName", new MessageAttributeValue().withDataType("String").withStringValue("test message"));
		sendMessageRequest.setMessageAttributes(messageAttributesMap);
		SQS.sendMessage(sendMessageRequest);
		System.out.println("Message Sent");
	}

	public static void deleteQueue() 
	{
		SQS.deleteQueue(queueResult.getQueueUrl());
		System.out.println("Deleted queue:"+QUEUE_NAME);
	}
	
	public static void receiveQueueMessages()
	{
		ReceiveMessageResult messageResult =  SQS.receiveMessage(queueResult.getQueueUrl());
		List<Message> messages = messageResult.getMessages();
		
		for(Message message : messages)
		{
			System.out.println(message.getBody());
		}
	}
	
	public static void countOfMessages() {
		System.out.println("Number of Messages in Queue:"+SQS.receiveMessage(queueResult.getQueueUrl()).getMessages().size()); 
	}
}
