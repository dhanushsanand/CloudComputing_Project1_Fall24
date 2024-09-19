package com.dhanush.aws.s3resources;

import java.io.File;
import javax.swing.JFileChooser;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;

public class S3BucketEventsHandler 
{
	
	private static final AmazonS3 S3 = AmazonS3ClientBuilder.defaultClient();
	private static final String BUCKET_NAME = "testBucketdshivana";
	
	public static void createS3Bucket() 
	{
		System.out.println("\nSending Resource Request to Create S3 bucket with name:" + BUCKET_NAME);
		CreateBucketRequest bucketRequest = new CreateBucketRequest(BUCKET_NAME,Region.US_East_2);
		Bucket bucket = S3.createBucket(bucketRequest);
		System.out.println("Successfully created S3 Bucket with name:" + bucket.getName());
	}
	
	public static void uploadFileToS3Bucket()
	{
		JFileChooser fileChooser = new JFileChooser();
		int selection = fileChooser.showOpenDialog(null);
		if (selection == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();
			uploadToS3(selectedFile);
		}
	}

	private static void uploadToS3(File selectedFile)
	{
		String keyName = selectedFile.getName();
		PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, keyName, selectedFile);
		S3.putObject(putObjectRequest);
		System.out.println("Successfully Uploaded file "+keyName+" into S3 bucket with name:"+BUCKET_NAME);
	}

	public static void deleteBucket() {
	}
}
