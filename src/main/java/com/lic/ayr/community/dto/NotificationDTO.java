package com.lic.ayr.community.dto;

import com.lic.ayr.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {

    private Integer id;
    private Long gmt_create;
    private Integer status;
    private User notifier;
    private String outerTitle;
    private Integer receiver;
    private Integer type;
    private Integer outerId;
}
