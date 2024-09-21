package com.dhanush.aws.ec2resources;

import java.util.List;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AmazonEC2Exception;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;

public class EC2EventsHandler
{
	private static final AmazonEC2 EC2 = AmazonEC2ClientBuilder.defaultClient();
	private static final String AMI_ID = "ami-085f9c64a9b75eed5";
	private static String reservationID = "";
	
	private EC2EventsHandler()
	{
		
	}
	
	public static void createEC2Instance(String amiId) throws AmazonEC2Exception
	{
		System.out.println("\n\nSending Resource request to create EC2 Instnace with ami id:" + amiId);
		RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
		runInstancesRequest.withImageId(amiId).withInstanceType(InstanceType.T2Micro).withMaxCount(1).withMinCount(1);
		RunInstancesResult runInstancesResponse = EC2.runInstances(runInstancesRequest);
		reservationID = runInstancesResponse.getReservation().getInstances().get(0).getInstanceId();
		System.out.println("\nSuccessfully started EC2 instance with Reservation ID:"+reservationID+ " based on AMI:"+amiId);
	}
	
	public static void listInstances()
	{
		Filter runningInstances = new Filter("instance-state-name");
		runningInstances.withValues("running");
		DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
		describeInstancesRequest.withFilters(runningInstances);
        DescribeInstancesResult response = EC2.describeInstances(describeInstancesRequest);
        List<Reservation> reservations = response.getReservations();
        if (reservations.isEmpty()) {
			System.out.println("\nNo EC2 Instances Found");
			return;
		}
        reservations.forEach(reservation -> reservation.getInstances().forEach(instance -> System.out.println("\nThe following Running Instances were found:"+instance.getInstanceId())));
	}
	
	public static void terminateEC2Instances()
	{
		TerminateInstancesRequest terminateInstancesRequest = new TerminateInstancesRequest();
		terminateInstancesRequest.withInstanceIds(reservationID);
		EC2.terminateInstances(terminateInstancesRequest);
		System.out.println("\nterminated the instance with Reservation ID:"+reservationID);
	}
}
