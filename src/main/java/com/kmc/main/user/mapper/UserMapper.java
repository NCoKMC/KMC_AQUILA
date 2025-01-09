package com.kmc.main.user.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.kmc.main.user.dto.UserDto;


@Repository
@Mapper
public interface UserMapper {

	int selectUserListCnt() throws SQLException;
	List<UserDto> selectUserList() throws SQLException;
	UserDto selectUserDetail(UserDto user) throws SQLException;

	Long insertUserInfo(UserDto user) throws SQLException;
	Long updateUserInfo(UserDto userVo);
	Long updateUserStatus(UserDto userVo);
	Long updateUserPwd(UserDto userVo);

	void deleteUser(Long id);
	UserDto selectUserByEmail(String email);
	UserDto selectUserByKmcCd(String kmcCd);

	Long insertUserAuth(UserDto user) throws SQLException;


}
