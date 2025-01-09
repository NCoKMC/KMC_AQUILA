package com.kmc.main.room.service.impl;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmc.common.KMCDateUtils;
import com.kmc.main.room.dto.ResveManageDto;
import com.kmc.main.room.dto.RoomCodeDto;
import com.kmc.main.room.dto.RoomDto;
import com.kmc.main.room.mapper.RoomMapper;
import com.kmc.main.room.service.RoomService;





@Service
public class RoomServiceImpl implements RoomService{
	
	@Autowired
	private RoomMapper mapper;

	@Override
	public int getRoomListCnt() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectRoomListCnt();
	}

	@Override
	public List<RoomDto> getRoomList(int endDay) throws Exception {
		
		List<String> dayList = new ArrayList<String>();
		
		for (int i = 1; i <= endDay; i++) {
			dayList.add(i+"");
		}
		
		List<RoomDto> list = mapper.selectRoomList(dayList);
		
		
		return list;
	}
	@Override
	public List<ResveManageDto> getResveManageList(ResveManageDto vo) throws Exception {
	return mapper.selectResveManageList(vo);
	}
	
	
	
	@Override
	public List<ResveManageDto> getRoomResveManageList(ResveManageDto vo) throws Exception {
		
		
		List<ResveManageDto> list = new ArrayList<ResveManageDto>();
		
		
		
		list = mapper.selectRoomResveManageList(vo);
//			
		
		
		return list;
	}

	@Override
	public RoomDto getRoomDetail(RoomDto vo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectRoomDetail(vo);
	}

//	@Override
//	public List<RoomDto> getRoomList2(int endDay) throws Exception {
//		// TODO Auto-generated method stub
//		List<String> dayList = new ArrayList<String>();
//		
//		for (int i = 1; i <= endDay; i++) {
//			dayList.add(i+"");
//		}
//		return mapper.selectRoomDtoList(dayList);
//	}

	@Override
	public List<RoomCodeDto> getRoomCodeList(String group) throws Exception {
		List<RoomCodeDto> list = null;
		if("YMLIST".equals(group)) {
			list = mapper.selectRoomCodeList();
		}else if("ROOM_USE_ST".equals(group)) {
			RoomCodeDto vo = new RoomCodeDto();
			vo.setComCd("ROOM_USE_ST");
			list = mapper.selectCommonCodeList(vo);
		}
		
		return list;
	}

	
	

}

