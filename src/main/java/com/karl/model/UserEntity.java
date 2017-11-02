package com.karl.model;

import javax.persistence.*;

/**
 * Created by R on 2017/8/23.
 */
@Entity
@Table(name = "user", schema = "ETraveling", catalog = "")
public class UserEntity {
    private String openid;
    private String name;
    private String sex;
    private Integer age;
    private String orgin;
    private String headimg;

    @Id
    @Column(name = "openid", nullable = false, length = 50)
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "sex", nullable = true, length = 10)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "age", nullable = true)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "orgin", nullable = true, length = 30)
    public String getOrgin() {
        return orgin;
    }

    public void setOrgin(String orgin) {
        this.orgin = orgin;
    }

    @Basic
    @Column(name = "headimg", nullable = true, length = 50)
    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (openid != null ? !openid.equals(that.openid) : that.openid != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (age != null ? !age.equals(that.age) : that.age != null) return false;
        if (orgin != null ? !orgin.equals(that.orgin) : that.orgin != null) return false;
        if (headimg != null ? !headimg.equals(that.headimg) : that.headimg != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = openid != null ? openid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (orgin != null ? orgin.hashCode() : 0);
        result = 31 * result + (headimg != null ? headimg.hashCode() : 0);
        return result;
    }
}
