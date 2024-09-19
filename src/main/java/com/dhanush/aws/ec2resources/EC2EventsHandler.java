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
	private static final EC2EventsHandler SINGLETON = new EC2EventsHandler();
	private static final AmazonEC2 EC2 = AmazonEC2ClientBuilder.defaultClient();
	private static EC2InstanceDetails ec2InstanceDetails = null;
	private static final String AMI_ID = "ami-085f9c64a9b75eed5";
	
	private EC2EventsHandler()
	{
		
	}
	
	public static EC2EventsHandler getInstance()
	{
		return SINGLETON;
	}
	
	public static void createEC2Instance() throws AmazonEC2Exception
	{
		System.out.println("Sending Resource request to create EC2 Instnace with ami id:" + AMI_ID);
		RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
		runInstancesRequest.withImageId(AMI_ID).withInstanceType(InstanceType.T2Micro).withMaxCount(1).withMinCount(1).withKeyName("dshivana");
		RunInstancesResult runInstancesResponse = EC2.runInstances(runInstancesRequest);
		String reservationId = runInstancesResponse.getReservation().getInstances().get(0).getInstanceId();
		System.out.println("Successfully started EC2 instance with Reservation ID:"+reservationId+ " based on AMI:"+AMI_ID);
		ec2InstanceDetails = new EC2InstanceDetailsBuilder().setEC2Instance(EC2).setInstanceID(reservationId).build();
	}
	
	public static void listInstances()
	{
		Filter runningInstances = new Filter("instance-state-name");
		runningInstances.withValues("running");
		DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
		describeInstancesRequest.withFilters(runningInstances);
        DescribeInstancesResult response = EC2.describeInstances(describeInstancesRequest);
        List<Reservation> reservations = response.getReservations();
        reservations.forEach(reservation -> reservation.getInstances().forEach(instance -> System.out.println("The following Instances were found:"+instance.getInstanceId())));
	}
	
	public static void terminateEC2Instances()
	{
		TerminateInstancesRequest terminateInstancesRequest = new TerminateInstancesRequest();
		terminateInstancesRequest.withInstanceIds(ec2InstanceDetails.getInstanceID());
		ec2InstanceDetails.getEC2Instnace().terminateInstances(terminateInstancesRequest);
	}
}
