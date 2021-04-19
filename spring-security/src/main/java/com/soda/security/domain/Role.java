package com.soda.security.domain;

/**
 * @author soda
 * @date 2021/4/19
 */
public class Role {

    private Integer id;
    private String name;
    private String nameZh;

    public String getName() {
        return name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameZh='" + nameZh + '\'' +
                '}';
    }
}
