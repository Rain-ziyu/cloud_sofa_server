package asia.huayu.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResultDTO<T> {

    private List<T> records;

    private Long count;

    public PageResultDTO(List articleAdminDTOs, Integer integer) {
        this.count = Long.valueOf(integer);
        this.records = articleAdminDTOs;
    }
}
