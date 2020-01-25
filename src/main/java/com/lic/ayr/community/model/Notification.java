package com.lic.ayr.community.model;

import lombok.Data;

@Data
public class Notification {

    public Integer id;
    public Integer notifier;
    public Integer receiver;
    public Integer type;
    public Long gmt_create;
    public Integer status;
    public Integer outerId;
}
