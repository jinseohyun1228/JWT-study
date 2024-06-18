package com.jwt.jwtstudy_youtube.service;

import com.jwt.jwtstudy_youtube.dto.JoinDto;
import com.jwt.jwtstudy_youtube.entity.UserEntity;
import com.jwt.jwtstudy_youtube.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void joinProcess(JoinDto joinDto) {
        String username = joinDto.getUsername();
        String rowPassword = joinDto.getPassword();

        if (userRepository.existsByUsername(username)) {
            //존재하는 경우
            return;
        }

        UserEntity user = new UserEntity();

        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(rowPassword));
        user.setRole("ROLE_ADMIN");

        userRepository.save(user);

    }
}
