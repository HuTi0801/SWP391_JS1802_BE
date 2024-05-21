package com.js1802_team5.diamondShop.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseStorageService {
    private Storage storage;

    public FirebaseStorageService() {
        // Đọc GoogleCredentials từ tệp JSON
        try {
            FileInputStream serviceAccountStream = new FileInputStream("src/main/resources/diamondshop-db041-firebase-adminsdk-4ug7d-4963ba3109.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream);

            // Tạo StorageOptions với GoogleCredentials
            StorageOptions options = StorageOptions.newBuilder().setCredentials(credentials).build();

            // Tạo đối tượng Storage từ StorageOptions
            storage = options.getService();
        }
        catch (Exception e) {

        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename(); // tránh trùng id
        BlobId blobId = BlobId.of("diamondshop-db041.appspot.com", fileName); // bucket: địa chỉ của storage trên firebase project web
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        var result = storage.create(blobInfo, file.getBytes());
        return fileName;
    }
}
