package com.kmc.main.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kmc.main.login.dto.EmailAuthRequestDto;
import com.kmc.main.login.service.EmailService;
import com.kmc.main.room.dto.ResveManageDto;
import com.kmc.main.room.service.RoomService;
import com.kmc.main.user.dto.UserDto;
import com.kmc.main.user.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private EmailService emailService;




	@GetMapping("/userList")
	public String getUserList(Model model) throws Exception {


		List<UserDto> list = userService.getUserList();
		int cnt = userService.getUserListCnt();


		model.addAttribute("cnt", cnt);
		model.addAttribute("list", list);
		return "user/userList";
	}


	@GetMapping("/userDetail")
	public String getUserDetail(Model model,
			@RequestParam(value="mCd") String mCd
			,@RequestParam(value="pageCd" ,required=false) String pageCd
			) throws Exception {



		UserDto vo = new UserDto();

		vo.setMCd(mCd);

		UserDto user = userService.getUserDetail(vo);
		int imgInt =Integer.parseInt(user.getStatusCd())/10%3;

		ResveManageDto rvVo = new ResveManageDto();
		rvVo.setMCd(mCd);

		List<ResveManageDto> rvList = roomService.getRoomResveManageList(rvVo);

		model.addAttribute("user", user);
		model.addAttribute("rvList", rvList);
		model.addAttribute("photoCd",imgInt);
		model.addAttribute("pageCd",pageCd);

		return "user/userDetail";
	}

//	@GetMapping("/update")
//    public String editPage(Model model) throws Exception { // 회원 정보 수정 페이지
//        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDto userVo = userService.getUserById(id);
//        model.addAttribute("user", userVo);
//        return "editPage";
//    }

    @PostMapping("/userUpdate")
    public String edit(@ModelAttribute UserDto userVo) { // 회원 정보 수정
//        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        userVo.setId(id);
        userService.updateUserInfo(userVo);


        String statusCd = userVo.getStatusCd();
        String email = userVo.getEmail();

        if("10".equals(statusCd)) {
        	EmailAuthRequestDto emailDto = new EmailAuthRequestDto();
        	String authCode = "";
        	emailDto.setEmail(email);
            try {
				authCode = emailService.sendEmail(emailDto.getEmail());

				if(authCode != null && !authCode.isEmpty()) {
					userVo.setImsyPwd(authCode);
					userService.updateUserPwd(userVo);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}





        }

        return "redirect:/user/usrList";

    }

    @PostMapping("/delete")
    public String withdraw(HttpSession session) { // 회원 탈퇴
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id != null) {
            userService.withdraw(id);
        }
        SecurityContextHolder.clearContext(); // SecurityContextHolder에 남아있는 token 삭제
        return "redirect:/";
    }

	@GetMapping("/excelDownload")
	public void excelDownUserList(HttpServletResponse res) throws Exception {

		List<UserDto> list = userService.getUserList();

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

		for (UserDto vo : list) {
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getUserId());
			cell = row.createCell(1);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getMCd());
			cell = row.createCell(2);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getEmail());
			cell = row.createCell(3);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getPhoneNum());
			cell = row.createCell(4);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getUserNm());
			cell = row.createCell(5);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getCountryCd());
			cell = row.createCell(6);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getBirthYmd());
			cell = row.createCell(7);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getStatusCd());
			cell = row.createCell(8);
			cell.setCellStyle(bs);
			cell.setCellValue(vo.getStayCd());
		}


		//컨텐트 타이입과 파일명 지정
		res.setContentType("ms-vnd/excel");
		res.setHeader("Content-Dispostion", "attachment;filename=visitor.xmls");


		//엑셀출력
		wb.write(res.getOutputStream());
		wb.close();



	}

}
