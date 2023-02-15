package asia.huayu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章，用于存储文章内容
 *
 * @TableName article
 */
@TableName(value = "article")
@Data
@Document(indexName = "article", shards = 3, replicas = 1)
public class Article implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;
    /**
     * 文章标题
     */
    @Field(type = FieldType.Text)
    private String title;
    /**
     * 文章内容
     */
    @Field(type = FieldType.Text)
    private String content;
    /**
     * 文章类型
     */
    @Field(type = FieldType.Keyword)
    private Integer type;
    /**
     * 创建时间
     */
    @Field(type = FieldType.Date)
    private Date createTime;
    /**
     * 修改时间
     */
    @Field(type = FieldType.Date)
    private Date modifiedTime;
    /**
     * 创建用户的id
     */
    @Field(type = FieldType.Keyword)
    private Integer userId;

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
        Article other = (Article) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
                && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}