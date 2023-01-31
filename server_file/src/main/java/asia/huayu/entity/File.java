package asia.huayu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录系统中上传的文件信息
 *
 * @author User
 * @TableName file
 */
@TableName(value = "file")
@Data
public class File implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 唯一主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件创建时间
     */
    private Date createTime;
    /**
     * 文件地址
     */
    private String fileUrl;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 记录文件大小,单位kb
     */
    private Long fileSize;

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
        File other = (File) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getFileUrl() == null ? other.getFileUrl() == null : this.getFileUrl().equals(other.getFileUrl()))
                && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
                && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getFileUrl() == null) ? 0 : getFileUrl().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        return result;
    }

    @Override
    public String toString() {
        String sb = getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", fileName=" + fileName +
                ", createTime=" + createTime +
                ", fileUrl=" + fileUrl +
                ", modifyTime=" + modifyTime +
                ", fileSize=" + fileSize +
                ", serialVersionUID=" + serialVersionUID +
                "]";
        return sb;
    }
}