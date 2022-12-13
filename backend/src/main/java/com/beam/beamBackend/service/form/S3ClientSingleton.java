package com.beam.beamBackend.service.form;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class S3ClientSingleton {
    private static S3Client s3;

    private S3ClientSingleton() {}

    public static S3Client getInstance() {
        if (s3 == null) {
            ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
            Region region = Region.US_EAST_1;
            s3 = S3Client.builder()
                .region(region)
                .httpClientBuilder(ApacheHttpClient.builder())
                .credentialsProvider(credentialsProvider)
                .build();
        }

        return s3;
    }
}
