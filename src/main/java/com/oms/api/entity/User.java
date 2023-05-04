package com.oms.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @TableField("username")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度必须在6到20位之间")
    @TableField("password")
    private String password;

    @TableField("real_name")
    private String realName;

    @TableField("mobile")
    private String mobile;

    @Email(message = "邮箱格式错误")
    @TableField("email")
    private String email;

    @TableField("avatar")
    private String avatar;

    @TableField("remark")
    private String remark;

    @TableField("last_login_at")
    private Integer lastLoginAt;

    @TableField("status")
    private Short status;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Long createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private Long updatedAt;

    @TableField("deleted_at")
    private LocalDateTime deletedAt;
}
