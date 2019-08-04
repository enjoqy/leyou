package org.junhi.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Length(min = 4, max = 30, message = "用户名必须在4-30位之间")
    private String username;

    /**
     * 密码
     */
    @Length(min = 4, max = 30, message = "密码必须在4-30位之间")
    @JsonIgnore  //对象序列化为json字符串时，忽略该属性
    private String password;

    /**
     * 电话
     */
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$",message = "手机格式不正确")
    private String phone;

    private Date created;// 创建时间

    @JsonIgnore
    private String salt;// 密码的盐值
}