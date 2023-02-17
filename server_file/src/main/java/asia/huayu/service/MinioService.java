package asia.huayu.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URI;

/**
 * @author RainZiYu
 */
public interface MinioService {

    /**
     * 获取上传文件的文件名
     *
     * @param file 传入的文件
     * @return 返回一个文件名
     */
    static String getFilename(MultipartFile file) {
        // 获取上传的文件名称，并结合存放路径，构建新的文件名称
        String filename = file.getOriginalFilename();
        assert filename != null;
        int unixSep = filename.lastIndexOf('/');
        int winSep = filename.lastIndexOf('\\');
        int pos = (Math.max(winSep, unixSep));
        if (pos != -1) {
            filename = filename.substring(pos + 1);
        }
        return filename;
    }

    Boolean fileExists(String bucketName, String fileName);

    /**
     * 查看存储bucket是否存在
     *
     * @param bucketName 桶名字
     * @return 返回找到，true找到，false没有
     */
    Boolean bucketExists(String bucketName);

    /**
     * 根据文件路径得到预览文件绝对地址
     *
     * @param bucketName
     * @param fileName   文件路径
     * @return
     */
    String getPreviewFileUrl(String bucketName, String fileName) throws Exception;

    /**
     * 创建存储bucket
     *
     * @param bucketName 存储bucket名称
     * @return Boolean 成功true
     */
    Boolean makeBucket(String bucketName);

    /**
     * 删除存储bucket
     *
     * @param bucketName 存储bucket名称
     * @return Boolean 成功则true
     */
    Boolean removeBucket(String bucketName);

    /**
     * 删除存储bucket
     *
     * @param bucketName 存储bucket名称
     * @return Boolean 成功则true
     */
    Boolean removeObject(String bucketName, String name);

    /**
     * 方法<code>getPreviewFileUrl</code>作用为：
     * 根据默认存储桶获取预览链接
     *
     * @param fileName
     * @return java.lang.String
     * @throws
     * @author RainZiYu
     */
    String getPreviewFileUrl(String fileName) throws Exception;


    /**
     * 文件上传
     *
     * @param file       文件
     * @param bucketName 存储bucket
     * @return Boolean 成功true
     */
    Boolean upload(MultipartFile file, String bucketName, String fileName);

    Boolean upload(byte[] in, String fileType, String bucketName, String fileName);

    Boolean upload(String fileType, String bucketName, String fileName, InputStream inputStream, Long bytesLength);

    /**
     * 文件上传至默认存储桶
     *
     * @param file 文件
     * @return Boolean 成功true
     */
    Boolean upload(MultipartFile file, String fileName);

    /**
     * 文件下载
     *
     * @param bucketName 存储bucket名称
     * @param fileName   文件名称
     * @param res        response
     */
    void download(String bucketName, String fileName, HttpServletResponse res);

    /**
     * 文件下载
     *
     * @param bucketName 存储bucket名称
     * @param fileName   文件名称
     * @param res        response
     */
    void download(String bucketName, String fileName, String newFileName, HttpServletResponse res);

    /**
     * 根据文件名获取url
     *
     * @param fileName
     * @return
     */
    String getUrl(String fileName);


    /**
     * 根据文件名获取文件流
     *
     * @param bucketName
     * @param fileName
     * @return
     */
    InputStream getObject(String bucketName, String fileName);

    /**
     * 复制文件
     *
     * @param backupBucket
     * @param newName
     * @param oldBucket
     * @param fileName
     */
    void copyObject(String backupBucket, String newName, String oldBucket, String fileName);

    /**
     * 方法<code>upload</code>作用为：
     * 根据传递的userAvatar 字符串 base64 decode为byte进行上传
     *
     * @param userAvatar
     * @param fileName
     * @return java.lang.String
     * @throws
     * @author RainZiYu
     */
    String upload(String userAvatar, String fileName);

    /**
     * 方法<code>upload</code>作用为：
     * 根据URL对象进行文件上传
     *
     * @param url
     * @return java.lang.String
     * @throws
     * @author RainZiYu
     */
    String upload(URI url);
}
