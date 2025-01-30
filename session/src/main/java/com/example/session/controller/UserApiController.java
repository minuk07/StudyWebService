package com.example.session.controller;

import com.example.session.model.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @GetMapping("/me")
    public UserDto me(
        HttpSession httpSession
    ){
        var userObject = httpSession.getAttribute("USER"); //USER라는 이름을 가진 데이터를 가져옴.
        //저장해놨던 세션에서 값을 꺼내서 사용.

        if(userObject != null){ //세션이 만료되면 null이 리턴될 수도 있음.
            var userDto = (UserDto) userObject; //userDto로 형변환하여 userDto로 리턴.
            return userDto;
        }else{
            return null;
        }
    }

}
