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
			EC2EventsHandler.createEC2Instance(args[0]);
			S3BucketEventsHandler.createS3Bucket();
			SQSQueueEventsHandler.createQueue();
			System.out.println("\nResources created successfully, waiting for 1 min, In the meantime check if resources have been created in AWS console");
			
			waitTime(60000);
			System.out.println("\nListing Created Resources Below:");
			EC2EventsHandler.listInstances();
			S3BucketEventsHandler.listS3Buckets();
			SQSQueueEventsHandler.listQueues();
			
			System.out.println("\nWaiting Sometime before uploading file to S3 Bucket and sending message through SQS Queue");
			waitTime(20000);
			S3BucketEventsHandler.uploadFileToS3Bucket(args[1]);
			SQSQueueEventsHandler.sendMessage();
			SQSQueueEventsHandler.countOfMessages();
			SQSQueueEventsHandler.receiveQueueMessages();
			SQSQueueEventsHandler.countOfMessages();
			
			System.out.println("Waiting sometime before proceeding to Delete the resources that were created");
			waitTime(30000);
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
			
			System.out.println("\nDeleted Resources Waiting for 1 minute to check if resources have been deleted");
			waitTime(60000);
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
