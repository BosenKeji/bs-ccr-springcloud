package cn.bosenkeji.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/employee")
    @ResponseBody
    public String getEmployee() {
        System.out.println(passwordEncoder.encode("secret"));
        return "success";
    }

    @GetMapping("/employee2")
    @ResponseBody
    public String getEmployee2() {
        return "success";
    }

    @GetMapping("/login2")
    @ResponseBody
    public String login2() {
        return "login2";
    }

}
