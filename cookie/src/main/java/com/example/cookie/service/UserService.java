package com.example.cookie.service;

import com.example.cookie.db.UserRepository;
import com.example.cookie.model.LoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    //실제 로그인 로직 구현
    public void login(
            LoginRequest loginRequest,
            HttpServletResponse httpServletResponse
    ){
        var id = loginRequest.getId();
        var pw = loginRequest.getPassword();

        var optionalUser = userRepository.findByName(id);

        if(optionalUser.isPresent()){
            var userDto = optionalUser.get();

            if(userDto.getPassword().equals(pw)){
                //cookie 해당 정보를 저장
                var cookie = new Cookie("authorization-cookie",userDto.getId());
                cookie.setDomain("localhost"); //해당 도메인에서만 활용 가능.
                cookie.setPath("/"); //경로 설정 (root)
                cookie.setMaxAge(-1); //세션이 유지되어 있을동안만 연결

                httpServletResponse.addCookie(cookie); //response에 쿠키를 설정할 수 있음.

            }

        }else{
            throw new RuntimeException("User Not Found");
        }
    }
}
