package asia.huayu.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "相册")
public class PhotoAlbumVO {

    @Schema(description = "id", title = "相册id", required = true, type = "Integer")
    private Integer id;

    @NotBlank(message = "相册名不能为空")
    @Schema(description = "albumName", title = "相册名", required = true, type = "String")
    private String albumName;

    @Schema(description = "albumDesc", title = "相册描述", type = "String")
    private String albumDesc;

    @NotBlank(message = "相册封面不能为空")
    @Schema(description = "albumCover", title = "相册封面", required = true, type = "String")
    private String albumCover;

    @Schema(description = "status", title = "状态值", required = true, type = "Integer")
    private Integer status;

}
