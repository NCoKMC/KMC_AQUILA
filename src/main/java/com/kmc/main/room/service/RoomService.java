package com.kmc.main.room.service;

import java.util.List;
import java.util.Map;

import com.kmc.main.room.dto.ResveManageDto;
import com.kmc.main.room.dto.RoomCodeDto;
import com.kmc.main.room.dto.RoomDto;

public interface RoomService {
	public int getRoomListCnt() throws Exception ;
	public List<RoomDto> getRoomList(int endDay) throws Exception ;
//	public List<RoomDto> getRoomList2(int endDay) throws Exception ;
	public RoomDto getRoomDetail(RoomDto vo) throws Exception;
	
	public List<ResveManageDto> getRoomResveManageList(ResveManageDto vo) throws Exception;
	public List<ResveManageDto> getResveManageList(ResveManageDto rVo) throws Exception;
	
	public List<RoomCodeDto> getRoomCodeList(String group) throws Exception;
	
	
}
