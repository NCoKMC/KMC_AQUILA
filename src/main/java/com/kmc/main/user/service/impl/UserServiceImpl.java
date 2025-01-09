package com.kmc.main.user.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kmc.common.util.KMCeMailUtils;
import com.kmc.main.login.dto.EmailAuthRequestDto;
import com.kmc.main.user.dto.UserDto;
import com.kmc.main.user.mapper.UserMapper;
import com.kmc.main.user.service.UserService;

import jakarta.mail.MessagingException;




@Service
public class UserServiceImpl implements UserService{

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private KMCeMailUtils mailUtil;

	@Override
	public int getUserListCnt() throws Exception {
		// TODO Auto-generated method stub
		return userMapper.selectUserListCnt();
	}

	@Override
	public List<UserDto> getUserList() throws Exception {
		// TODO Auto-generated method stub
		return userMapper.selectUserList();
	}

	@Override
	public UserDto getUserDetail(UserDto user) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.selectUserDetail(user);
	}

	@Override
	public Long saveUser(UserDto user) throws Exception {
		return null;


	}

	@Override
	public UserDto getUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }



    public void signup(UserDto userVo) throws SQLException { // 회원 가입
        if (!userVo.getUserNm().equals("") && !userVo.getEmail().equals("")) {
		    // password는 암호화해서 DB에 저장
            userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
            userMapper.insertUserInfo(userVo);

            userMapper.insertUserAuth(userVo);
        }
    }

    public void updateUserInfo(UserDto userVo) { // 회원 정보 수정

        userMapper.updateUserInfo(userVo);
        userMapper.updateUserStatus(userVo);



    }

    public void withdraw(Long id) { // 회원 탈퇴
        userMapper.deleteUser(id);
    }

    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }

	@Override
	public UserDto getUserById(Long id) throws SQLException {
		// TODO Auto-generated method stub
		UserDto vo = new UserDto();
		vo.setId(id);
		return userMapper.selectUserDetail(vo);
	}

	@Override
	public UserDto getUserByKmcCd(String kmcCd) {
		// TODO Auto-generated method stub
		return userMapper.selectUserByKmcCd(kmcCd);
	}

	@Override
	public void updateUserPwd(UserDto userVo) {
		// TODO Auto-generated method stub
		userMapper.updateUserPwd(userVo);
	}

}
