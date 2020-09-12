package com.ez2pay.file;

import com.ez2pay.config.FileStorageConfig;
import com.ez2pay.exception.FileNotFoundException;
import com.ez2pay.exception.FileStorageException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Data
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService (FileStorageConfig fileStorageConfig){
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.fileStorageLocation);
        }catch (Exception e){
            throw new FileStorageException("Could not create the directory for file uploading", e);
        }
    }

    public String storeFile (MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")){
                throw new FileStorageException("Filename contains invalid path sequence: " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        }catch (FileStorageException fse){
            throw fse;
        }
        catch (Exception e){
            throw new FileStorageException("Could not store file: " + fileName + ". Please try again", e);
        }
    }

    public Resource loadFileAsResource(String fileName){
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()){
                return resource;
            }else{
                throw new FileNotFoundException("File not found: " + fileName);
            }

        }catch (FileNotFoundException fileNotFoundException){
            throw fileNotFoundException;
        }catch (Exception e){
            throw new FileNotFoundException("File not found: " + fileName, e);
        }
    }

}
