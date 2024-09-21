package com.dhanush.aws.s3resources;

import java.io.File;
import java.util.List;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;

public class S3BucketEventsHandler 
{
	private static final AmazonS3 S3 = AmazonS3ClientBuilder.defaultClient();
	private static final String BUCKET_NAME = "testbucket"+UUID.randomUUID();
	private static final String KEY_NAME = "CSE546test.txt";
	
	private S3BucketEventsHandler() 
	{
		
	}
	
	public static void createS3Bucket() 
	{
		System.out.println("\nSending Resource Request to Create S3 bucket with name:" + BUCKET_NAME);
		CreateBucketRequest bucketRequest = new CreateBucketRequest(BUCKET_NAME,Region.US_East_2);
		Bucket bucket = S3.createBucket(bucketRequest);
		System.out.println("\nSuccessfully created S3 Bucket with name:" + bucket.getName());
	}
	
	public static void uploadFileToS3Bucket(String filePath)
	{
		System.out.println("\nUploading the File to S3 Bucket");
		File file = new File(filePath);
		PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, KEY_NAME, file);
		S3.putObject(putObjectRequest);
		System.out.println("\nSuccessfully Uploaded file "+KEY_NAME+" into S3 bucket with name:"+BUCKET_NAME);
	}
	
	public static void listS3Buckets() 
	{
		List<Bucket> buckets = S3.listBuckets();
		if (buckets.isEmpty()) {
			System.out.println("No S3 Buckets Found");
			return;
		}
		buckets.forEach(bucket -> System.out.println("\nThe Following Bucket was found:"+bucket.getName()));
	}

	public static void deleteBucket() 
	{
		S3.deleteObject(BUCKET_NAME, KEY_NAME);
		S3.deleteBucket(BUCKET_NAME);
		System.out.println("\nDeleted Bucket:"+BUCKET_NAME);
	}
}
