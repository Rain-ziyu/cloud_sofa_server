package asia.huayu.service.impl;

import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.common.util.FileTypeTool;
import asia.huayu.config.MinioConfig;
import asia.huayu.service.MinioService;
import cn.hutool.core.codec.Base64;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @author RainZiYu
 */
@Service
@Slf4j
public class MinioServiceImpl implements MinioService {


    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查看存储bucket是否存在
     *
     * @param bucketName 桶名字
     * @return 返回找到，true找到，false没有
     */
    @Override
    public Boolean bucketExists(String bucketName) {
        Boolean found;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            log.error("bucketExists", e);
            return false;
        }
        return found;
    }


    /**
     * 方法<code>getPreviewFileUrl</code>作用为：
     * 根据默认存储桶获取预览链接
     *
     * @param fileName
     * @return java.lang.String
     * @throws
     * @author RainZiYu
     */
    @Override
    public String getPreviewFileUrl(String fileName) throws Exception {
        return minioClient
                .getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(minioConfig.getBucketName())
                        .object(fileName)
                        .build());
    }


    /**
     * 创建存储bucket
     *
     * @param bucketName 存储bucket名称
     * @return Boolean 成功true
     */
    @Override
    public Boolean makeBucket(String bucketName) {

        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除存储bucket
     *
     * @param bucketName 存储bucket名称
     * @return Boolean 成功则true
     */
    @Override
    public Boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除存储bucket内的文件
     *
     * @param bucketName 存储bucket名称
     * @return Boolean 成功则true
     */
    @Override
    public Boolean removeObject(String bucketName, String name) {
        try {
            minioClient.removeObject(RemoveObjectArgs
                    .builder()
                    .bucket(bucketName)
                    .object(name)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据文件路径得到预览文件绝对地址
     *
     * @param bucketName
     * @param fileName   文件路径
     * @return
     */
    @Override
    public String getPreviewFileUrl(String bucketName, String fileName) throws Exception {
        return minioClient
                .getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(fileName)
                        .build());
    }

    /**
     * 文件上传
     *
     * @param file       文件
     * @param bucketName 存储bucket
     * @return Boolean 成功true
     */
    @Override
    public Boolean upload(MultipartFile file, String bucketName, String fileName) {
        try (InputStream inputStream = file.getInputStream()) {
            return upload(file.getContentType(), bucketName, fileName, inputStream, file.getSize());
        } catch (IOException e) {
            throw new ServiceProcessException("MultipartFile获取IO流出现问题", e);
        }

    }

    @Override
    public Boolean upload(byte[] in, String fileType, String bucketName, String fileName) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(in)) {
            return upload(fileType, bucketName, fileName, inputStream, Long.valueOf(in.length));
        } catch (IOException e) {
            throw new ServiceProcessException(e);
        }
    }


    /**
     * 文件上传至默认存储桶
     *
     * @param file     文件
     * @param fileName
     * @return Boolean 成功true
     */
    @Override
    public Boolean upload(MultipartFile file, String fileName) {
        return upload(file, minioConfig.getBucketName(), fileName);
    }

    /**
     * 方法<code>upload</code>作用为：
     * 使用前端传过来的base64进行文件上传
     *
     * @param userAvatar
     * @return java.lang.Boolean
     * @throws
     * @author RainZiYu
     */
    @Override
    public String upload(String userAvatar, String fileName) {
        byte[] decode = Base64.decode(userAvatar);
        String fileType = FileTypeTool.getFileType(decode);
        fileName += "." + fileType;
        // fileName 文件名
        String contentType = MediaTypeFactory.getMediaType(fileName).orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
        upload(decode, contentType, minioConfig.getBucketName(), fileName);
        return fileName;
    }

    /**
     * 方法<code>upload</code>作用为：
     * 根据URL对象进行文件上传
     *
     * @param url
     * @return java.lang.String
     * @throws
     * @author RainZiYu
     */
    @Override
    public String upload(URI url) {
        Resource fileResource = restTemplate.getForObject(url, Resource.class);
        try {
            String fileType = FileTypeTool.getFileType(fileResource.getInputStream());
            String contentType = MediaTypeFactory.getMediaType(fileResource.getFilename() + "." + fileType)
                    .orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
            upload(contentType, minioConfig.getBucketName(), fileResource.getFilename(),
                    fileResource.getInputStream(), fileResource.contentLength());
            return fileResource.getFilename();
        } catch (IOException e) {
            throw new ServiceProcessException("获取支付宝头像异常", e);
        }
    }

    /**
     * 方法<code>upload</code>作用为：
     * 最终的上传放法
     *
     * @param fileType
     * @param bucketName
     * @param fileName
     * @param inputStream
     * @param bytesLength
     * @return java.lang.Boolean
     * @throws
     * @author RainZiYu
     */

    @Override
    public Boolean upload(String fileType, String bucketName, String fileName, InputStream inputStream, Long bytesLength) {
        try (inputStream) {
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(bucketName).object(fileName)
                    .stream(inputStream, bytesLength, -1).contentType(fileType).build();
            // 文件名称相同会覆盖
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(objectArgs);
            // io流使用完之后记得close 不然tomcat无法删除该临时文件

        } catch (Exception e) {
            throw new ServiceProcessException(e);
        }
        return true;
    }

    /**
     * 文件下载
     *
     * @param bucketName 存储bucket名称
     * @param fileName   文件名称
     * @param res        response
     */
    @Override
    public void download(String bucketName, String fileName, HttpServletResponse res) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName)
                .object(fileName).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)) {
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
                while ((len = response.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();

                res.addHeader("Content-Disposition", "attachment;fileName=" +
                        new String(fileName.getBytes("GBK"), StandardCharsets.ISO_8859_1) + "");
                try (ServletOutputStream stream = res.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceProcessException("下载失败");
        }
    }

    /**
     * 文件下载
     *
     * @param bucketName 存储bucket名称
     * @param fileName   文件名称
     * @param res        response
     */
    @Override
    public void download(String bucketName, String fileName, String newFileName, HttpServletResponse res) {

        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName)
                .object(fileName).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)) {
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
                while ((len = response.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();

                res.addHeader("Content-Disposition", "attachment;fileName=" + new String(newFileName.getBytes("GBK"), StandardCharsets.ISO_8859_1) + "");
                try (ServletOutputStream stream = res.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceProcessException("下载失败");
        }
    }

    /**
     * 根据文件名获取url
     *
     * @param fileName
     * @return
     */
    @Override
    public String getUrl(String fileName) {
        String url = minioConfig.getServerUrl() + ":" + minioConfig.getPort() + "/" + minioConfig.getBucketName() + "/" + fileName;
        return url;
    }


    /**
     * 根据文件名获取文件流
     *
     * @param bucketName
     * @param fileName
     * @return
     */
    @Override
    public InputStream getObject(String bucketName, String fileName) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName)
                .object(fileName).build();
        try {
            GetObjectResponse object = minioClient.getObject(objectArgs);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 复制文件
     *
     * @param backupBucket
     * @param newName
     * @param oldBucket
     * @param fileName
     */
    @Override
    public void copyObject(String backupBucket, String newName, String oldBucket, String fileName) {
        CopyObjectArgs objectArgs = CopyObjectArgs.builder()
                .bucket(backupBucket).object(newName)
                .source(CopySource.builder().bucket(oldBucket).object(fileName).build())
                .build();
        try {
            minioClient.copyObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
