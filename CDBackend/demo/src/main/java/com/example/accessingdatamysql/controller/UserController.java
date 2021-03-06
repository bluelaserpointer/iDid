package com.example.accessingdatamysql.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.GlobalConstants;
import com.example.accessingdatamysql.Security.JwtUtil;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.UserLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.example.accessingdatamysql.service.MailBoxService;

import com.example.accessingdatamysql.service.UserService;

import static com.example.accessingdatamysql.GlobalConstants.general_page_size;

@CrossOrigin(origins = "*")
@RestController() // This means that this class is a Controller
@RequestMapping(value = "/user") // This means URL's start with /demo (after Application path)
@EnableAutoConfiguration
public class UserController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userService;

  @Autowired
  private MailBoxService mailBoxService;

  @Autowired
  private UserLoginRecordService userLoginRecordService;

  // 获取一个用户信息
  @GetMapping(value = "/getUser")
  public @ResponseBody User findUserByUserId(@RequestParam("userId") Integer userId) {
    GlobalConstants.printIfDoDebug("Class: UserController Method:finduserByUserId Param:" + userId);
    return userService.getOneUser(userId);
  }

  // 用用户名获取用户
  @RequestMapping(value = "/getUserByUserName")
  public @ResponseBody User findUserByUserName(@RequestParam("userName") String userName) {
    return userService.getOneUserByUserName(userName);
  }

  // 注册用户
  @PostMapping("/register")
  public @ResponseBody String register(@RequestBody User registerUser) {
    final User existedUser = userService.getOneUserByUserName(registerUser.getUserName());
    final JSONObject response = new JSONObject();
    if (existedUser != null) {
      response.put("failReason", "用户名已存在");
    } else {
      registerUser.setIdentity(User.ROLE_USER);
      final User createdUser = userService.addNewUser(registerUser);
      response.put("user", createdUser);
      mailBoxService.addNewMailBox(createdUser.getUserId());
    }
    return response.toString();
  }

  // 仅用于调试
  @PostMapping("/unitTest")
  public @ResponseBody String registerAdmin(@RequestBody User registerUser) {
    final User existedUser = userService.getOneUserByUserName(registerUser.getUserName());
    final JSONObject response = new JSONObject();
    if (existedUser != null) {
      response.put("failReason", "用户名已存在");
    } else {
      // registerUser.setIdentity(User.ROLE_ADMIN);
      response.put("user", userService.addNewUser(registerUser));
    }
    return response.toString();
  }

  // 更新一个用户信息
  @RequestMapping(value = "/updateUser")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public @ResponseBody User updateUser(@RequestBody User updateUser) {
    // User User = JSON.parseObject(updateUser.getString("User"), User.class);
    return userService.updateUser(updateUser);
  }

  // 获取所有用户信息
  @RequestMapping(value = "/getAllUsers")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<User> getAllUsers() {
    // System.out.print(request);
    return userService.getAllUsers();
  }

  // 获取指定页数的数据
  @RequestMapping(value = "/List")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public JSONObject ListPage(@RequestBody ListRequest ListRequest) {
    ListRequest.setPageSize(general_page_size);
    final String request = JSON.toJSONString(ListRequest);
    System.out.print(request);
    return userService.ListPage(ListRequest);
  }

  // 删除部分用户
  @RequestMapping(value = "/deleteUsers")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteUsers(@RequestParam("userIds") List<Integer> userIds) {
    return userService.deleteUsers(userIds);
  }

  // 删除所有用户
  @RequestMapping(value = "/deleteAllUsers")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteAll() {
    return userService.deleteAll();
  }

  // 确认用户身份
  @RequestMapping(value = "/confirmDelete")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Integer confirmDelete(@RequestParam("userName") String userName, @RequestParam("password") String password) {
    return userService.identifyUser(userName, password);
  }

  // 登录逻辑
  @PostMapping("/login")
  public String identifyUser(@RequestBody AuthRequest authRequest) {
    final String userName = authRequest.getUserName(), password = authRequest.getPassword();
    final String LOG_HEAD = "UserController: login request: " + userName + ", " + password + " -> ";
    final User user = userService.getOneUserByUserName(userName);
    final JSONObject response = new JSONObject();
    if (user == null) {
      GlobalConstants.printIfDoDebug(LOG_HEAD + "refused(wrong username)");
      response.put("failReason", "用户名或密码错误");
    } else if (!user.getPassword().equals(password)) {
      GlobalConstants.printIfDoDebug(LOG_HEAD + "refused(wrong password)");
      response.put("failReason", "用户名或密码错误");
    } else if (!user.getAccess()) {
      GlobalConstants.printIfDoDebug(LOG_HEAD + "refused(banned user)");
      response.put("failReason", "用户已被禁止登录，详见游戏官网");
    } else {
      GlobalConstants.printIfDoDebug(LOG_HEAD + "accepted");
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserId(), password));
      userLoginRecordService.userLogin(user.getUserId());
      response.put("token", jwtUtil.generateToken(String.valueOf(user.getUserId())));
      response.put("user", user);
    }
    return response.toString();
  }

  @RequestMapping(value = "/logout")
  public void userLogout(@RequestParam("userId") Integer userId, @RequestParam("type") Integer type) {
    // Type: 1 代表正常退出， 2 代表系统IDLE踢出
    userLoginRecordService.userLogout(userId, type);
  }

  // 删除一个指定用户
  @RequestMapping(value = "/deleteUser")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<User> deleteUser(@RequestParam("userId") Integer userId) {
    return userService.deleteUser(userId);
  }

  // 增加用户经验值(如果累计经验值超过升级所需经验值则升级后再返回user)
  @RequestMapping(value = "/addExp")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public @ResponseBody User addExp(@RequestParam("userId") Integer userId, @RequestParam("exp") Integer exp) {
    GlobalConstants.printIfDoDebug("Class: UserController Method: addExp Param: userId = " + userId + " exp = " + exp);
    return userService.addExp(userId, exp);
  }

  // 获取用户的游戏信箱
  @RequestMapping(value = "/getMailBox")
  @PreAuthorize("hasRole('ROLE_ADMIN') OR #userId.toString() == authentication.name")
  public @ResponseBody List<Mail> getUserMailBox(@RequestParam("userId") Integer userId) {
    return mailBoxService.getOneUserMails(userId);
  }
}
