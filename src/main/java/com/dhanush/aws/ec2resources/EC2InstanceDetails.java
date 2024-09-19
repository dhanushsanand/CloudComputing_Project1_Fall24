package com.dhanush.aws.ec2resources;

import com.amazonaws.services.ec2.AmazonEC2;

public class EC2InstanceDetails {
	private String instanceID;
	private AmazonEC2 ec2;

	public EC2InstanceDetails(String instanceID, AmazonEC2 ec2) {
		super();
		this.instanceID = instanceID;
		this.ec2 = ec2;
	}

	public String getInstanceID() {
		return instanceID;
	}

	public AmazonEC2 getEC2Instnace() {
		return ec2;
	}
}
