package asia.huayu.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "照片")
public class PhotoVO {

    @NotNull(message = "相册id不能为空")
    @Schema(name = "id", title = "相册id", required = true, type = "Integer")
    private Integer albumId;

    @Schema(name = "photoUrlList", title = "照片列表", required = true, type = "List<String>")
    private List<String> photoUrls;

    @Schema(name = "photoIdList", title = "照片id列表", required = true, type = "List<Integer>")
    private List<Integer> photoIds;

}
