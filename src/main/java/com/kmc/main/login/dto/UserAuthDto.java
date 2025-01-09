package com.kmc.main.login.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserAuthDto implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	
	private Long auth;
	private String id;
	private String email;
    private String password;
    
    @Builder
    public UserAuthDto(String email, String password, String auth) {
    	this.email = email;
    	this.password = password;
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 권한반
		return List.of(new SimpleGrantedAuthority("user"));
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO 계정 만료 여부 true X
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO 계정 잠금 여부 true X
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO 패스워드 만료 여부 반환 true X
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO 계정 사용 가능 여부 반환 true X
		return true;
	}
}
