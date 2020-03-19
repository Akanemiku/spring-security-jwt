package github.akanemiku.springsecurityjwt.security.entity;

import github.akanemiku.springsecurityjwt.security.service.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 获取当前请求的用户
 *
 * 当认证成功的用户访问系统的时候，它的认证信息会被设置在 Spring Security 全局中
 * 可通过SecurityContextHolder.getContext().getAuthentication();获得当前登录用户的授权信息
 */
@Component
public class CurrentUser {

    private final UserDetailsServiceImpl userDetailsService;

    public CurrentUser(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public JwtUser getCurrentUser() {
        return (JwtUser) userDetailsService.loadUserByUsername(getCurrentUserName());
    }

    /**
     * TODO:由于在JWTAuthorizationFilter这个类注入UserDetailsServiceImpl一致失败，
     * 导致无法正确查找到用户，所以存入Authentication的Principal为从 token 中取出的当前用户的姓名
     */
    private static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
