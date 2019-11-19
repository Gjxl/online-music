package com.groupten.online_music.web;

import com.groupten.online_music.common.utils.ApplicationException;
import com.groupten.online_music.common.utils.ResponseEntity;
import com.groupten.online_music.entity.User;
import com.groupten.online_music.service.impl.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户操作相关接口")
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @ApiOperation("用户登录接口")
    @GetMapping("/login")
    public @ResponseBody ResponseEntity login(@RequestBody User user) {
        boolean result = userService.login(user);
        System.out.println("user:" +user);
        System.out.println("result:" +result);
        return ResponseEntity.ofSuccess(result).message(result?"登录成功":"登录失败");
    }


    @ApiOperation("用户注册接口")
    @PostMapping("/register")
    public @ResponseBody ResponseEntity register(@RequestBody User user) {
        boolean result = userService.register(user);
        return ResponseEntity.ofSuccess(result).message(result?"注册成功":"注册失败");
    }

    @ApiOperation("用户信息更改接口")
    @PutMapping(value="/{id}")
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody User source) {
        try {
            User target = userService.findById(id);
            if(target!=null) {
                BeanUtils.copyProperties(source, target);
                userService.save(target);//update
            }else {
                userService.save(source);//save
            }
        } catch (ApplicationException ex) {
            return ResponseEntity.ofSuccess(false).message("修改用户信息 error.");
        }
        return ResponseEntity.ofSuccess(true).message("修改用户信息 Success.");
    }

    @ApiOperation("删除用户接口")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity delete(@PathVariable int id) {
        boolean result = userService.deleteUser(id);
        return ResponseEntity.ofSuccess(result).message(result?"删除成功":"删除失败");
    }
}
