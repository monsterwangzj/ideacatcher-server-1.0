package com.softwisdom.ideacatcher.model;

import javassist.SerialVersionUID;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "idea")
public class Idea implements Serializable {
    private static final long serialVersionUID = -4345182261365463161L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 10)
    private Long id;

    @Column(name = "userid", length = 10, nullable = false)
    private String userId;

    @Column(name = "content")
    private String content;

    @Column(name = "createtime")
    private Long createTime;

    @Column(name = "lastmodified")
    private Long lastModified;

    public Idea() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

}
