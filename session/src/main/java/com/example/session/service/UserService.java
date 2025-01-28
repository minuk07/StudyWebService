package com.example.session.service;

import com.example.session.db.UserRepository;
import com.example.session.model.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired //필드 주입을 통한 의존성 주입
    private UserRepository userRepository;

    public void login(
            LoginRequest loginRequest,
            HttpSession httpSession //세션 객체. 서버가 클라이언트 상태 정보를 관리하기 위해 사용.
    ){
        var id = loginRequest.getId();
        var pw = loginRequest.getPassword();

        var optionalUser = userRepository.findByName(id); //id를 기반으로 사용자를 데이터베이스에서 조회

        if(optionalUser.isPresent()){
            var userDto = optionalUser.get();

            if(userDto.getPassword().equals(pw)){
                //세션에 정보를 저장 (USER라는 key로 사용자 정보 저장)
                httpSession.setAttribute("USER", userDto);
            }else{
                throw new RuntimeException("Password Not Match");
            }


        }else{ //없는 유저
            throw new RuntimeException("User Not Found");
        }
    }
}
