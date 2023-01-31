package asia.huayu.mapper;

import asia.huayu.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author User
 * @description 针对表【file(记录系统中上传的文件信息)】的数据库操作Mapper
 * @createDate 2023-01-30 13:54:25
 * @Entity asia.huayu.entity.File
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {

}




