package asia.huayu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户收藏
 *
 * @TableName user_collection
 */
@TableName(value = "user_collection")
@Data
public class UserCollection implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 收藏者id
     */
    private Integer userId;
    /**
     * 收藏内容，记录收藏的文章商品之类的id
     */
    private Integer collectionContent;
    /**
     * 收藏类型，用于分辨去哪个表查询
     */
    private Integer collectionType;

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
        UserCollection other = (UserCollection) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getCollectionContent() == null ? other.getCollectionContent() == null : this.getCollectionContent().equals(other.getCollectionContent()))
                && (this.getCollectionType() == null ? other.getCollectionType() == null : this.getCollectionType().equals(other.getCollectionType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCollectionContent() == null) ? 0 : getCollectionContent().hashCode());
        result = prime * result + ((getCollectionType() == null) ? 0 : getCollectionType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        String sb = getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", userId=" + userId +
                ", collectionContent=" + collectionContent +
                ", collectionType=" + collectionType +
                ", serialVersionUID=" + serialVersionUID +
                "]";
        return sb;
    }
}