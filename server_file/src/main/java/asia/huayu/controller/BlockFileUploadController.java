package asia.huayu.controller;

/**
 * @author RainZiYu
 * @Date 2023/4/12
 */

import asia.huayu.common.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.*;

@RestController
public class BlockFileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 方法uploadFile作用为：
     * 分块文件上传接口示例
     *
     * @param file
     * @param chunkNumber
     * @param totalChunks
     * @param filename
     * @param fileId
     * @param request
     * @return org.springframework.http.ResponseEntity<?>
     * @throws
     * @author RainZiYu
     */
    @PostMapping("/upload")
    public Result<?> uploadFile(@RequestPart("file") MultipartFile file,
                                @RequestParam("chunkNumber") int chunkNumber,
                                @RequestParam("totalChunks") int totalChunks,
                                @RequestParam("filename") String filename,
                                @RequestParam("fileId") String fileId,
                                HttpServletRequest request) throws IOException {

        String fileName = fileId + "-" + filename + "-" + chunkNumber;
        Path filePath = Paths.get(uploadDir, fileName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        if (chunkNumber == totalChunks) {
            // This is the last chunk, combine all the parts into a single file
            Path combinedFilePath = Paths.get(uploadDir, fileId + "-" + filename);
            for (int i = 1; i <= totalChunks; i++) {
                Path partFilePath = Paths.get(uploadDir, fileId + "-" + filename + "-" + i);
                Files.write(combinedFilePath, Files.readAllBytes(partFilePath), StandardOpenOption.CREATE);
                Files.delete(partFilePath);
            }
        }

        return Result.OK("文件上传成功");
    }

    /**
     * 方法downloadFile作用为：
     * 下载接口示例
     *
     * @param fileId
     * @param filename
     * @param request
     * @return org.springframework.http.ResponseEntity<org.springframework.core.io.Resource>
     * @throws
     * @author RainZiYu
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileId") String fileId,
                                                 @RequestParam("filename") String filename,
                                                 HttpServletRequest request) throws IOException {

        Path filePath = Paths.get(uploadDir, fileId + "-" + filename);
        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @PostMapping("/upload1")
    public ResponseEntity<?> uploadFile(@RequestHeader("FileName") String fileName,
                                        @RequestHeader("FileSize") Long fileSize,
                                        @RequestBody byte[] fileBytes) throws IOException {
        String filePath = "path/to/uploaded/file/" + fileName;
        File file = new File(filePath);
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        RandomAccessFile rafe = new RandomAccessFile(file, "rw");
        // seek to the end of the file
        raf.seek(file.length());

        // write the new data to the end of the file
        raf.write(fileBytes);

        raf.close();

        return ResponseEntity.ok().build();
    }


}

