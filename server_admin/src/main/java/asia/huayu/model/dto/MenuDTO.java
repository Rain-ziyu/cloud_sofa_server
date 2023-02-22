package asia.huayu.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {

    private Integer id;

    private String name;

    private String path;

    private String component;

    private String icon;

    private LocalDateTime createTime;

    private Integer rank;


    private Integer status;

    private List<MenuDTO> children;

}
