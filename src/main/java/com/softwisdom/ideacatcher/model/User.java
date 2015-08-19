package com.softwisdom.ideacatcher.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 6444998586443102467L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 10)
    private Long id;

    @Column(name = "username", length = 32)
    private String username;

    @Column(name = "password", length = 32)
    private String password;

    @Column(name = "nick", length = 100, nullable = false)
    private String nick;

    @Column(name = "icon", length = 100)
    private String icon;

    @Column(name = "gender", length = 3)
    private String gender;

    @Column(name = "birthday")
    private Long birthday;

    @Column(name = "status")
    private byte status;

    @Column(name = "createtime")
    private Long createTime;

    @Column(name = "lastmodified")
    private Long lastModified;

    public User() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
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
