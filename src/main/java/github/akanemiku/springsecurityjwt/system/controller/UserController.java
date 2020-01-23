package github.akanemiku.springsecurityjwt.system.controller;

import github.akanemiku.springsecurityjwt.security.entity.CurrentUser;
import github.akanemiku.springsecurityjwt.system.entity.User;
import github.akanemiku.springsecurityjwt.system.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author shuang.kou
 */
@RestController
@RequestMapping("/api")
//@Api(tags = "操作接口")
public class UserController {

    private final UserService userService;

    private final CurrentUser currentUser;

    public UserController(UserService userService, CurrentUser currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @GetMapping("/users")
//    @ApiOperation("获得所有用户")
    @PreAuthorize("hasAnyRole('ROLE_DEV','ROLE_PM','ROLE_ADMIN')")
    public ResponseEntity<Page<User>> getAllUser(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        System.out.println("当前访问该接口的用户为：" + currentUser.getCurrentUser().toString());
        Page<User> allUser = userService.getAllUser(pageNum, pageSize);
        return ResponseEntity.ok().body(allUser);
    }


    @DeleteMapping("/user")
//    @ApiOperation("删除某个用户")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<User> deleteUserById(@RequestParam("username") String username) {
        userService.deleteUserByUserName(username);
        return ResponseEntity.ok().build();
    }
}
