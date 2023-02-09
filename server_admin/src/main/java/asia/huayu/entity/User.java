package asia.huayu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
@Document(indexName = "user", shards = 3, replicas = 1)
public class User implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @Id
    /**
     * 唯一id
     */
    @TableId
    private String id;
    @Field(type = FieldType.Text)
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "用户密码是必须的")
    private String password;
    @Field(type = FieldType.Text)
    /**
     * 昵称
     */
    private String nickName;
    @Field(type = FieldType.Text)
    /**
     * 用户头像
     */
    private String salt;
    @Field(type = FieldType.Text)
    /**
     * 用户签名
     */
    private String token;
    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @Field(type = FieldType.Text)
    private Integer isDeleted;
    /**
     * 创建时间
     */
    @Field(type = FieldType.Date)
    private Date gmtCreate;
    /**
     * 更新时间
     */
    @Field(type = FieldType.Date)
    private Date gmtModified;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
                && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
                && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
                && (this.getSalt() == null ? other.getSalt() == null : this.getSalt().equals(other.getSalt()))
                && (this.getToken() == null ? other.getToken() == null : this.getToken().equals(other.getToken()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        result = prime * result + ((getSalt() == null) ? 0 : getSalt().hashCode());
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        return result;
    }

    @Override
    public String toString() {
        String sb = getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", username=" + username +
                ", password=" + password +
                ", nickName=" + nickName +
                ", salt=" + salt +
                ", token=" + token +
                ", isDeleted=" + isDeleted +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", serialVersionUID=" + serialVersionUID +
                "]";
        return sb;
    }
}