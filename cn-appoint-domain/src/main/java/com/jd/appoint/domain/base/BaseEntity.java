package com.jd.appoint.domain.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 基本持久化实体类
 *
 * @author lijizhen1@jd.com
 * @date 2018/5/7 10:30
 */
public class BaseEntity implements Serializable {
    protected Long id;  //主键
    /**
     * 创建时间
     */
    protected Date created;

    /**
     * 修改时间
     */
    protected Date modified;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
