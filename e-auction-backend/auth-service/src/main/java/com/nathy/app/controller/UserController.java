package com.nathy.app.controller; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nathy.app.entity.User;
import com.nathy.app.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

//   @GetMapping
//    public ResponseTemplateVO getUser(
//            @RequestHeader(value = "id") String userId,
//            @RequestHeader(value = "role") String role) {
//        //return userService.getUserWithDepartment(userId);
//	   return null;
//    }

    @GetMapping(value = "/secure")
    public String getSecure() {
        return "Secure endpoint available";
    }
}
