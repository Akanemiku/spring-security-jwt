package github.akanemiku.springsecurityjwt.security.utils;

import github.akanemiku.springsecurityjwt.security.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenUtils {


    /**
     * 生成足够的安全随机密钥，以适合符合规范的签名
     */
    private static byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);
    private static SecretKey secretKey = Keys.hmacShaKeyFor(apiKeySecretBytes);

    public static String createToken(String username, List<String> roles, boolean isRememberMe) {
        //判断是否勾选记住我，勾选后过期时间变长
        long expiration = isRememberMe ? SecurityConstants.EXPIRATION_REMEMBER : SecurityConstants.EXPIRATION;

        String tokenPrefix = Jwts.builder()
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .claim(SecurityConstants.ROLE_CLAIMS, String.join(",", roles))
                .setIssuer("Akane")
                .setIssuedAt(new Date())
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
        return SecurityConstants.TOKEN_PREFIX + tokenPrefix;
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getTokenBody(token).getExpiration();
        return expiredDate.before(new Date());
    }

    /**
     * 通过token获取用户名
     * @param token
     * @return
     */
    public static String getUsernameByToken(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * 通过token获取改用户所有角色
     * @param token
     * @return
     */
    public static List<SimpleGrantedAuthority> getUserRolesByToken(String token) {
        String role = (String) getTokenBody(token)
                .get(SecurityConstants.ROLE_CLAIMS);
        return Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * 通过token反解析出内容
     * @param token
     * @return
     */
    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
