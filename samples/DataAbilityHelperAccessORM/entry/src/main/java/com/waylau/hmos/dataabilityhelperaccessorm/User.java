package com.waylau.hmos.dataabilityhelperaccessorm;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.Index;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "user",
        indices = {@Index(value = {"userName"}, name = "name_index", unique = true)})
public class User extends OrmObject {
    // 此处将userId设为了自增的主键。注意只有在数据类型为包装类型时，自增主键才能生效。
    @PrimaryKey(autoGenerate = true)
    private Integer userId;
    private String userName;
    private int age;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}