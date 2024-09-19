package com.dhanush.aws.ec2resources;

import com.amazonaws.services.ec2.AmazonEC2;

public class EC2InstanceDetailsBuilder {
	private String instanceID;
	private AmazonEC2 ec2;

	public EC2InstanceDetailsBuilder setInstanceID(String instanceID) {
		this.instanceID = instanceID;
		return this;
	}

	public EC2InstanceDetailsBuilder setEC2Instance(AmazonEC2 ec2) {
		this.ec2 = ec2;
		return this;
	}

	public EC2InstanceDetails build() {
		return new EC2InstanceDetails(instanceID,ec2);
	}

}
