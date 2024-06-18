package com.jwt.jwtstudy_youtube.dto;

import com.jwt.jwtstudy_youtube.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    /*
    getAuthorities() 메서드
        : 인증된 사용자가 가진 권한을 반환하는 메서드다.
        보통 어떤 URL에 대한 접근을 허용 여부 결정에 쓰인다.
        따라서 Spring Security는 이 메서드를 사용하여 사용자가 특정 리소스, 기능에 접근 권한이 있는지 확인할 수 있다.

        반환값 : Collection<? extends GrantedAuthority>
            - 사용자가 가진 모든 권한 ("ROLE_USER", "ROLE_ADMIN"...) 모두를 반환한다.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userEntity.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
