package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.pic.generate.GeneratePic;
import asia.huayu.util.BaseVariableUtil;
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
            try (FileChannel channel = new FileInputStream(picPath).getChannel();
                 ServletOutputStream outputStream = response.getOutputStream()) {
                // 申请1M的ByteBuffer从头像的channel中读取ByteBuffer
                ByteBuffer allocate = ByteBuffer.allocate(1024 * 1024);
                while (channel.read(allocate) > 0) {
                    outputStream.write(allocate.array());
                }
                // 设置文件流的ContentType以及在响应头中设置文件名
                response.setHeader("Content-Disposition", "attachment; filename=" + new File(picPath).getName());
                response.setContentType("application/octet-stream");
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
