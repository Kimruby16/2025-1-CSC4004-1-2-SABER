//package com.oss.saber.s3;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/files")
//public class S3Controller {
//    private final S3Service s3Service;
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadMedia(@RequestParam("file") MultipartFile file) {
//        try {
//            String fileUrl = s3Service.uploadFile(file);
//            return ResponseEntity.ok(fileUrl);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
//        }
//    }
//
//}
