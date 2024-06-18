package com.jwt.jwtstudy_youtube.service;


import com.jwt.jwtstudy_youtube.dto.CustomUserDetails;
import com.jwt.jwtstudy_youtube.entity.UserEntity;
import com.jwt.jwtstudy_youtube.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
        UserDetailsService의 loadUserByUsername!
         - username을 기반으로 사용자 정보를 로드하고 이를 UserDetail로 반환해 리턴한다.

        만약 jwt가 아니라 세션을 사용하는 프로젝트라면?!
         - UserDetailsService의 loadUserByUsername의 반환값을 세션에 저장할 것이다. (로그인 완료)

        그러나 이 프로젝트는 jwt를 사용하는 프로젝트 🪄
         - 세션을 서버에서 유지하지 않는다. (클라이언트에서 상태를 유지))
         - 유효한 사용자라고 판단한 경우 클라이언트에게 JWT를 반환한다.
         - 이후 클라이언트가 재 요청을 할때, 다시 토큰을 전달받아 사용자를 인증한다.

         (이후 클라이언트는 서버에 요청을 보낼 때, JWT를 포함해서 보낸다.
         서버는 JWT를 검증하고, 토큰이 유효한 경우 토큰에 포함된 사용자 정보를 기반으로 UserDetails 객체를 생성한다.)
        */
    @Override //필수
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //구현해야할 동작
        // 1. 사용자 유효한지 검사
        // -> 유효한 경우 UserDetails 반환

        UserEntity userData = userRepository.findByUsername(username);
        if (userData != null) {
            return new CustomUserDetails(userData);
        }
        return null;
    }
}

