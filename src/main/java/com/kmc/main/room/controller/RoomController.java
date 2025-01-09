package com.kmc.main.room.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kmc.common.KMCDateUtils;
import com.kmc.main.room.dto.ResveManageDto;
import com.kmc.main.room.dto.RoomCodeDto;
import com.kmc.main.room.dto.RoomDto;
import com.kmc.main.room.service.RoomService;
import com.kmc.main.user.dto.UserDto;

import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/room")
public class RoomController {
	
	@Autowired
	private RoomService service;
	
	@GetMapping("/resveRoomList")
	public String getResveManageList(Model model,
			@RequestParam(value="startYmd", required=false) String startYmd,
			@RequestParam(value="endYmd", required=false) String endYmd
			) throws Exception {
				return "room/resveList";
				
		
		
	}
	
	@PostMapping("/resveRoom")
	public String saveUser(UserDto userVo) { // 회원 가입
        try {
        	//System.out.print(reqMap.toString());
//            userService.signup(userVo);
      
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/signup?error_code=-99";
        }
        return "redirect:/login";
    }
	
	@GetMapping("/roomList")
	public String getRoomList(Model model,
			@RequestParam(value="yyyyMM", required=false) String yyyyMM
			) throws Exception {
		
		
		if(yyyyMM == null) {
			yyyyMM = KMCDateUtils.getTodayTime("yyyyMM");
		}
				
		String currYmd = KMCDateUtils.getTodayTime("yyyyMMdd");
		System.out.println(currYmd);
		
		int endDay = KMCDateUtils.getLastDayOfMonth(yyyyMM);
		List<String> dayList = new ArrayList<String>();
		for (int i = 1; i <= endDay; i++) {
			dayList.add(i+"");
		}
		
		String startYmd = yyyyMM +"01";
		String endYmd = yyyyMM + endDay;
		
		ResveManageDto rVo = new ResveManageDto();
		rVo.setYyyyMM(yyyyMM);
		rVo.setStartYmd(startYmd);
		rVo.setEndYmd(endYmd);
		rVo.setCurrYmd(currYmd);
		
		
		List<ResveManageDto> rvlist = service.getResveManageList(rVo);
//		List<RoomDto> roomList = service.getRoomList();
		
		
		
		List<RoomDto> list = service.getRoomList(endDay);
		
		List<RoomCodeDto> ymList = service.getRoomCodeList("YMLIST");
		
		model.addAttribute("list", list);
		model.addAttribute("rvlist", rvlist);
		model.addAttribute("dateInfo",rVo);
		model.addAttribute("ymList",ymList);
		model.addAttribute("endDay",endDay);
		model.addAttribute("currYmd",currYmd);
		
		
		
		return "room/roomList";
	}
	
	
	@GetMapping("/roomDetail")
	public String getRoomDetail(Model model,
			@RequestParam(value="roomNo") String roomNo,
			@RequestParam(value="yyyyMM") String yyyyMM
			
			) throws Exception {
		
		
		
		RoomDto vo = new RoomDto();
		vo.setRoomNo(roomNo);
		
		
		RoomDto dto = service.getRoomDetail(vo);
		
		ResveManageDto rvVo = new ResveManageDto();
		rvVo.setRoomNo(roomNo);
		rvVo.setYyyyMM(yyyyMM);
		
		
		List<ResveManageDto> rvList = service.getRoomResveManageList(rvVo);
		List<RoomCodeDto> ruList = service.getRoomCodeList("ROOM_USE_ST");	
		model.addAttribute("yyyyMM", yyyyMM);
		model.addAttribute("room", dto);
		model.addAttribute("rvList", rvList);
		model.addAttribute("ruList", ruList);
		return "room/roomDetail";
	}
	
	@GetMapping("/excelDownload")
	public void excelDownUserList(HttpServletResponse res,
			@RequestParam(value="yyyyMM", required=false) String yyyyMM
			) throws Exception {
		if(yyyyMM == null) {
			yyyyMM = KMCDateUtils.getTodayTime("yyyyMM");
		}
				
		
		int endDay = KMCDateUtils.getLastDayOfMonth(yyyyMM);
		List<String> dayList = new ArrayList<String>();
		for (int i = 1; i <= endDay; i++) {
			dayList.add(i+"");
		}
		
		String startYmd = yyyyMM +"01";
		String endYmd = yyyyMM + endDay;
		
		ResveManageDto rVo = new ResveManageDto();
		rVo.setYyyyMM(yyyyMM);
		rVo.setStartYmd(startYmd);
		rVo.setEndYmd(endYmd);
		List<ResveManageDto> list = service.getResveManageList(rVo);
		
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("방문자 목록");
		
		Row row = null;
		Cell cell = null;
		int rowNo = 0;
		
		//테이블 헤더용 스타일 
		CellStyle hs = wb.createCellStyle();
		hs.setBorderTop(BorderStyle.THIN);
		hs.setBorderBottom(BorderStyle.THIN);
		hs.setBorderLeft(BorderStyle.THIN);
		hs.setBorderRight(BorderStyle.THIN);
		
		//배경색
		hs.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
		hs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		//가운데 정렬
		hs.setAlignment(HorizontalAlignment.CENTER);
		
		//데이터용 경계 스타일 테두리만 지정
		CellStyle bs = wb.createCellStyle();
		bs.setBorderTop(BorderStyle.THIN);
		bs.setBorderBottom(BorderStyle.THIN);
		bs.setBorderLeft(BorderStyle.THIN);
		bs.setBorderRight(BorderStyle.THIN);
		
		//헤더생성
		
		for (ResveManageDto vo : list) {
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getRoomNo());
			cell = row.createCell(1);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getUserId());
			cell = row.createCell(2);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getStartDate());
			cell = row.createCell(3);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getEndDate());
			cell = row.createCell(4);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getResvDate());
			
		}
				
		
		//컨텐트 타이입과 파일명 지정
		res.setContentType("ms-vnd/excel");
		res.setHeader("Content-Dispostion", "attachment;filename=reserveManageList.xmls");
		
		
		//엑셀출력
		wb.write(res.getOutputStream());
		wb.close();
	
		
	
	}

}
