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
			EC2EventsHandler.createEC2Instance("ami-085f9c64a9b75eed5");
			S3BucketEventsHandler.createS3Bucket();
			SQSQueueEventsHandler.createQueue();
			System.out.println("All resource request sent wait for 1 min...");	
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
