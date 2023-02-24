package asia.huayu.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "网站配置")
public class WebsiteConfigVO {

    @Schema(description = "name", title = "网站名称", required = true, type = "String")
    private String name;

    @Schema(description = "nickName", title = "网站作者昵称", required = true, type = "String")
    private String englishName;

    @Schema(description = "author", title = "网站作者", required = true, type = "String")
    private String author;

    @Schema(description = "avatar", title = "网站头像", required = true, type = "String")
    private String authorAvatar;

    @Schema(description = "description", title = "网站作者介绍", required = true, type = "String")
    private String authorIntro;

    @Schema(description = "logo", title = "网站logo", required = true, type = "String")
    private String logo;

    @Schema(description = "multiLanguage", title = "多语言", required = true, type = "Integer")
    private Integer multiLanguage;

    @Schema(description = "notice", title = "网站公告", required = true, type = "String")
    private String notice;

    @Schema(description = "websiteCreateTime", title = "网站创建时间", required = true, type = "LocalDateTime")
    private String websiteCreateTime;

    @Schema(description = "beianNumber", title = "网站备案号", required = true, type = "String")
    private String beianNumber;

    @Schema(description = "qqLogin", title = "QQ登录", required = true, type = "Integer")
    private Integer qqLogin;

    @Schema(description = "github", title = "github", required = true, type = "String")
    private String github;

    @Schema(description = "gitee", title = "gitee", required = true, type = "String")
    private String gitee;

    @Schema(description = "qq", title = "qq", required = true, type = "String")
    private String qq;

    @Schema(description = "weChat", title = "微信", required = true, type = "String")
    private String weChat;

    @Schema(description = "weibo", title = "微博", required = true, type = "String")
    private String weibo;

    @Schema(description = "csdn", title = "csdn", required = true, type = "String")
    private String csdn;

    @Schema(description = "zhihu", title = "zhihu", required = true, type = "String")
    private String zhihu;

    @Schema(description = "juejin", title = "juejin", required = true, type = "String")
    private String juejin;

    @Schema(description = "twitter", title = "twitter", required = true, type = "String")
    private String twitter;

    @Schema(description = "stackoverflow", title = "stackoverflow", required = true, type = "String")
    private String stackoverflow;

    @Schema(description = "touristAvatar", title = "游客头像", required = true, type = "String")
    private String touristAvatar;

    @Schema(description = "userAvatar", title = "用户头像", required = true, type = "String")
    private String userAvatar;

    @Schema(description = "isCommentReview", title = "是否评论审核", required = true, type = "Integer")
    private Integer isCommentReview;

    @Schema(description = "isEmailNotice", title = "是否邮箱通知", required = true, type = "Integer")
    private Integer isEmailNotice;

    @Schema(description = "isReward", title = "是否打赏", required = true, type = "Integer")
    private Integer isReward;

    @Schema(description = "weiXinQRCode", title = "微信二维码", required = true, type = "String")
    private String weiXinQRCode;

    @Schema(description = "alipayQRCode", title = "支付宝二维码", required = true, type = "String")
    private String alipayQRCode;

}
