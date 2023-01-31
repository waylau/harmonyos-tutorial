package com.waylau.hmos.shortvideo.bean;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.Index;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "user_info_t",
        indices = {@Index(value = {"username"}, name = "name_index", unique = true)})
public class UserInfo extends OrmObject {
    // 此处将userId设为了自增的主键。注意只有在数据类型为包装类型时，自增主键才能生效。
    @PrimaryKey(autoGenerate = true)
    private Integer userId;

    // 账号
    private String username;

    // 密码
    private String password;

    // 头像路径
    private String portraitPath;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getPortraitPath() {
        return portraitPath;
    }

    public void setPortraitPath(String portraitPath) {
        this.portraitPath = portraitPath;
    }

}
