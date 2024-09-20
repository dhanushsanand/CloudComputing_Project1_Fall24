package com.dhanush.aws;

import com.dhanush.aws.ec2resources.EC2EventsHandler;
import com.dhanush.aws.s3resources.S3BucketEventsHandler;
import com.dhanush.aws.sqsresources.SQSQueueEventsHandler;

public class App 
{
	public static void main(String[] args) 
	{
		try 
		{
			System.out.println("All resource request sent wait for 1 min...");	
			EC2EventsHandler.createEC2Instance();
			S3BucketEventsHandler.createS3Bucket();
			SQSQueueEventsHandler.createQueue();
			System.out.println("Resources created successfully, waiting for 1 min");
			
			waitTime(60000);
			
			EC2EventsHandler.listInstances();
			S3BucketEventsHandler.listS3Buckets();
			SQSQueueEventsHandler.listQueues();
			
			S3BucketEventsHandler.uploadFileToS3Bucket();
			SQSQueueEventsHandler.sendMessage();
			SQSQueueEventsHandler.countOfMessages();
			SQSQueueEventsHandler.receiveQueueMessages();
			SQSQueueEventsHandler.countOfMessages();
			waitTime(10000);
		} 
		catch (Exception exception)
		{
			System.out.println(exception.getMessage());
		} 
		finally 
		{
			EC2EventsHandler.terminateEC2Instances();
			S3BucketEventsHandler.deleteBucket();
			SQSQueueEventsHandler.deleteQueue();
			
			waitTime(20000);
			EC2EventsHandler.listInstances();
			S3BucketEventsHandler.listS3Buckets();
			SQSQueueEventsHandler.listQueues();
		}
	}

	private static void waitTime(long waitTime)
	{
		try
		{
			Thread.sleep(waitTime);
		} catch (InterruptedException exception) 
		{
			System.out.println(exception.getMessage());
			Thread.currentThread().interrupt();
		}
	}
}
