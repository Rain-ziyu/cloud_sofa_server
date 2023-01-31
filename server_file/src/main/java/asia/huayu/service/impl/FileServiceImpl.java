package asia.huayu.service.impl;

import asia.huayu.entity.File;
import asia.huayu.mapper.FileMapper;
import asia.huayu.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author User
 * @description 针对表【file(记录系统中上传的文件信息)】的数据库操作Service实现
 * @createDate 2023-01-30 13:54:25
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File>
        implements FileService {

}




