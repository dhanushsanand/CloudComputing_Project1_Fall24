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
			
			EC2EventsHandler.listInstances();
			S3BucketEventsHandler.listS3Buckets();
			SQSQueueEventsHandler.listQueues();
			
			S3BucketEventsHandler.uploadFileToS3Bucket();
			SQSQueueEventsHandler.sendMessage();
			
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
		}
	}
}
