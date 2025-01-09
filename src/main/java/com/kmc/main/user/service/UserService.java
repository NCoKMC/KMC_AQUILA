package com.kmc.main.user.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.kmc.main.user.dto.UserDto;


public interface UserService {
	public int getUserListCnt() throws Exception ;
	public List<UserDto> getUserList() throws Exception ;
	public UserDto getUserDetail(UserDto user) throws Exception;
	Long saveUser(UserDto user) throws Exception;

	public void signup(UserDto userVo) throws Exception; // 회원 가입


    public void updateUserInfo(UserDto userVo); // 회원 정보 수정
    public void updateUserPwd(UserDto userVo); // 회원 정보 수정


    public void withdraw(Long id); // 회원 탈퇴

    public PasswordEncoder passwordEncoder();
	public UserDto getUserByEmail(String email);
	public UserDto getUserById(Long id) throws Exception;
	public UserDto getUserByKmcCd(String kmcCd);
}
