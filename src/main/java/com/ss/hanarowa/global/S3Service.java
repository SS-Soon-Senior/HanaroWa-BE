package com.ss.hanarowa.global;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {
	private final S3Client s3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	public String uploadFile(MultipartFile file) throws IOException {
		String originalFileName = file.getOriginalFilename();
		String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String key = "uploads/" + UUID.randomUUID().toString() + fileExtension;

		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
			.bucket(bucketName)
			.key(key)
			.contentType(file.getContentType())
			.contentLength(file.getSize())
			.build();

		RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
		s3Client.putObject(putObjectRequest, requestBody);

		return "https://" + bucketName + ".s3." + s3Client.serviceClientConfiguration().region().id()
			+ ".amazonaws.com/" + key;
	}
}
