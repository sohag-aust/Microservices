package com.shohag.Backend.services.Impl;

import com.shohag.Backend.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();

        // random name generate file
        String randomId = UUID.randomUUID().toString();
        String randomFileName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));

        // fullPath
        String filePath = path + File.separator + randomFileName;

        // create folder if not created
        File file = new File(path);
        if(!file.exists()) {
            file.mkdir();
        }

        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
        return randomFileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
