package com.lic.ayr.community.dto;

public class ReturnAccessToKenDTO {
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "ReturnAccessToKenDTO{" +
                "access_token='" + access_token + '\'' +
                '}';
    }
}
