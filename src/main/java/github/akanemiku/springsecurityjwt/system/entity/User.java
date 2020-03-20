package github.akanemiku.springsecurityjwt.system.entity;

import github.akanemiku.springsecurityjwt.system.enums.UserStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
@Data
@Table(name = "jwtuser")
@ApiModel(value = "用户")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "用户id")
    private Integer id;

    @Column(name = "username")
    @ApiModelProperty(value = "用户名")
    private String username;

    @Column(name = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(value = "状态")
    private UserStatus status;

    @Column(name = "role")
    @ApiModelProperty(value = "角色")
    private String roles;

    public List<SimpleGrantedAuthority> getRoles() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(roles.split(",")).forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        return authorities;
    }

}
