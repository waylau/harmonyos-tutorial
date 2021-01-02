package com.waylau.hmos.dataabilityhelperaccessdatabase;

import ohos.data.orm.OrmObject;


public class User extends OrmObject {
    private Integer userId;
    private String userName;
    private Integer userAge;

    public String getUserName() {
        return userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }
}
