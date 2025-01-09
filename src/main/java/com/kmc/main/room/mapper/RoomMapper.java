package com.kmc.main.room.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.kmc.main.room.dto.ResveManageDto;
import com.kmc.main.room.dto.RoomCodeDto;
import com.kmc.main.room.dto.RoomDto;
import com.kmc.main.user.dto.UserDto;


@Repository
@Mapper
public interface RoomMapper {

	int selectRoomListCnt() throws SQLException;
	List<RoomDto> selectRoomList(List<String> list) throws SQLException;
	RoomDto selectRoomDetail(RoomDto vo) throws SQLException;
	List<ResveManageDto> selectRoomResveManageList(ResveManageDto vo) throws SQLException;
	
//	List<RoomDto> selectRoomDtoList(List<String> list) throws SQLException;
	List<ResveManageDto> selectResveManageList(ResveManageDto vo) throws SQLException;
	
	List<RoomCodeDto> selectRoomCodeList() throws SQLException;
	List<RoomCodeDto> selectCommonCodeList(RoomCodeDto vo) throws SQLException;
	
	
}
