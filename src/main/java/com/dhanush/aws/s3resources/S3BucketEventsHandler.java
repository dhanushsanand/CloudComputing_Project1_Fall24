package com.dhanush.aws.s3resources;

import java.io.File;
import java.util.List;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;

public class S3BucketEventsHandler 
{
	private static final AmazonS3 S3 = AmazonS3ClientBuilder.defaultClient();
	private static final String BUCKET_NAME = "testbucketdshivana";
	private static final String KEY_NAME = "CSE546test.txt";
	
	private S3BucketEventsHandler() 
	{
		
	}
	
	public static void createS3Bucket() 
	{
		System.out.println("Sending Resource Request to Create S3 bucket with name:" + BUCKET_NAME);
		CreateBucketRequest bucketRequest = new CreateBucketRequest(BUCKET_NAME,Region.US_East_2);
		Bucket bucket = S3.createBucket(bucketRequest);
		System.out.println("Successfully created S3 Bucket with name:" + bucket.getName());
	}
	
	public static void uploadFileToS3Bucket()
	{
		/*
		 * JFileChooser fileChooser = new JFileChooser(); 
		 * int selection = fileChooser.showOpenDialog(null); 
		 * if (selection == JFileChooser.APPROVE_OPTION) 
		 * { File selectedFile = fileChooser.getSelectedFile();
		 * PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, KEY_NAME, file);
		 * S3.putObject(putObjectRequest);
		 *   }
		 */
		File file = new File("C:\\Users\\dhanu\\Documents\\ASU\\CloudComputing_CSE546\\CSE546test.txt");
		PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, KEY_NAME, file);
		S3.putObject(putObjectRequest);
		System.out.println("Successfully Uploaded file "+KEY_NAME+" into S3 bucket with name:"+BUCKET_NAME);
	}
	
	public static void listS3Buckets() 
	{
		List<Bucket> buckets = S3.listBuckets();
		buckets.forEach(bucket -> System.out.println("The Following Bucket was found:"+bucket.getName()));
	}

	public static void deleteBucket() 
	{
		S3.deleteObject(BUCKET_NAME, KEY_NAME);
		S3.deleteBucket(BUCKET_NAME);
		System.out.println("Deleted Bucket:"+BUCKET_NAME);
	}
}
