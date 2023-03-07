package asia.huayu.auth.entity;

import lombok.Data;

import java.util.List;

@Data
public class ResourceRole {

    private Integer id;

    private String url;

    private String requestMethod;

    private List<String> roleList;

}
