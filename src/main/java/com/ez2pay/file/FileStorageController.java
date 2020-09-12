package com.ez2pay.file;

import com.ez2pay.business.customer.CustomerController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/file")
@Tag(name = "File Upload/Download API", description = "API to upload/download file")
public class FileStorageController {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Operation(summary = "Upload file", description = "Upload file and return file info if successfully", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded"),
            @ApiResponse(responseCode = "500", description = "Could not upload file", content = @Content),
    })
    @PostMapping("/uploadFile")
    @ResponseStatus(code = HttpStatus.OK)
    public FileUploadResponse uploadFile (@Parameter(description = "File to upload")  @RequestParam MultipartFile file) {
       String fileName = fileStorageService.storeFile(file);

       String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
               .path("/v1/file/downloadFile/")
               .path(fileName)
               .toUriString();

       return new FileUploadResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }


    @Operation(summary = "Upload multiple files", description = "Upload multiple files and return a list of file info if successfully", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files uploaded"),
            @ApiResponse(responseCode = "500", description = "Could not upload files", content = @Content),
    })
    @PostMapping("/uploadMultipleFiles")
    @ResponseStatus(code = HttpStatus.OK)
    public List<FileUploadResponse> uploadMultipleFiles (@Parameter(description = "Files to upload")  @RequestParam MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }


    @Operation(summary = "Download file", description = "Download file", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File downloaded"),
            @ApiResponse(responseCode = "500", description = "Could not download file", content = @Content),
    })
    @GetMapping("/downloadFile/{fileName:.+}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Resource> downloadFile (@Parameter(description = "Filename to download") @PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Could not determine file type");
        }

        if (contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename()  + "\"")
                .body(resource);
    }


}
