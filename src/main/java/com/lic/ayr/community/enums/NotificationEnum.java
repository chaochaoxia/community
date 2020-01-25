package com.lic.ayr.community.enums;

public enum  NotificationEnum {
    REPLY_QUESTION(1,"回复问题"),
    REPLY_COMMENT(2,"回复评论");


    private int type;
    private String name;

    public int getTypes() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
