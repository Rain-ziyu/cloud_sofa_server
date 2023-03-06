package asia.huayu.model.dto;

import lombok.Data;

/**
 * @author RainZiYu
 * @Date 2023/3/6
 */
@Data
public class TheWordOfTheDayDTO {
    private int id;
    private String uuid;
    private String hitokoto;
    private String type;
    private String from;

    private String fromWho;
    private String creator;

    private int creatorUid;
    private int reviewer;

    private String commitFrom;

    private String createdAt;
    private int length;
}
