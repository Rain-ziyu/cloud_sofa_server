package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.pic.generate.GeneratePic;
import asia.huayu.util.BaseVariableUtil;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * @author RainZiYu
 * @Date 2023/1/31
 */
@RestController
@RequestMapping("/pic")
public class PicController extends BaseController {
    @GetMapping
    public void generatePicByKeyword(String keyword, HttpServletResponse response) {
        restProcessor(() -> {

            String picPath = GeneratePic.create(keyword, BaseVariableUtil.TMP_PIC_FOLDER);

            Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(picPath);
            String contentType = mediaType.orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
            // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。设置文件流的ContentType
            response.setContentType(contentType);
            // TODO:待验证原因
            // java web中下载文件时,我们一般设置Content-Disposition告诉浏览器下载文件的名称,是否在浏览器中内嵌显示.
            // Content-disposition: inline; filename=foobar.pdf
            // 表示浏览器内嵌显示一个文件
            // Content-disposition: attachment; filename=foobar.pdf
            // 在响应头中设置文件名
            response.setHeader("Content-Disposition", "inline; filename=" + new File(picPath).getName());
            try (FileChannel channel = new FileInputStream(picPath).getChannel();
                 ServletOutputStream outputStream = response.getOutputStream()) {
                // 申请1M的ByteBuffer从头像的channel中读取ByteBuffer
                ByteBuffer allocate = ByteBuffer.allocate(1024 * 1024);
                while (channel.read(allocate) > 0) {
                    outputStream.write(allocate.array());
                }
                response.flushBuffer();
            } catch (IOException e) {
                throw new ServiceProcessException("返回文件出现问题", e);
            }


            // 删除创建的文件
            try {
                Files.delete(Paths.get(picPath));
            } catch (IOException e) {
                throw new ServiceProcessException("临时文件删除异常", e);
            }
            return Result.OK();
        });
    }

}
