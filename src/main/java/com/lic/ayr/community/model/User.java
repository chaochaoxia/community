package com.lic.ayr.community.model;

public class User {
    private Integer id;
    private String name;
    private String account_id;
    private String token;
    private Long gmt_Create;
    private Long gmt_Modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getGmt_Create() {
        return gmt_Create;
    }

    public void setGmt_Create(Long gmt_Create) {
        this.gmt_Create = gmt_Create;
    }

    public Long getGmt_Modified() {
        return gmt_Modified;
    }

    public void setGmt_Modified(Long gmt_Modified) {
        this.gmt_Modified = gmt_Modified;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account_id='" + account_id + '\'' +
                ", token='" + token + '\'' +
                ", gmt_Create=" + gmt_Create +
                ", gmt_Modified=" + gmt_Modified +
                '}';
    }
}
