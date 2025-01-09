package com.kmc.main.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	private String mCd;
	private String userId;

	private String birthYmd;
	private String email;
	private String phoneNum;
	private String userNm;
	private String countryCd;
	private String age;
	private String statusCd;
	private String stayCd;

	private int peopleNum;
	private String statusNm;

	private String startYmd;
	private String endYmd;

	private String password;
	private Long id;

	private String marriedYn; //결혼여


	private String spouseNm; //배우자이
	private String locationNm; //사역도시 지역


	private String ms1Cd; // 사역코드1
	private String ms2Cd; // 사역코드2
	private String ms3Cd; // 사역코드3

	private String msAgencyNm; 			//사역기관/단체
	private String msAgencyPhoneNum; 	//사역기관전화번호
	private String msAgencyAddr;		//사역기관주소
	//파송기관/단체
	private String dispatchAgencyNm;    //단체이름
	private String dispatchAgencyCd;  	//단체코드
	private String dispatchDmnNm;  		//교단이름
	private String dispatchDmnCd;  		//교단코드
	private String dispatchChurchNm;  	//교회이름

	private String dispatchAgencyPhoneNum1;	//단체연락처1
	private String dispatchAgencyPhoneNum2;	//단체연락처2
	private String dispatchAgencyAddr;		//단체주소


	private String dispatchYear;  	// 파송해
	private String agreeYn;			//동의여

	private String imsyPwd;

	public UserDto() {
		// TODO Auto-generated constructor stub
	}

	public UserDto(String mCd, String userId, String email, String phoneNum, String userNm, String countryCd) {
		this.mCd = mCd;
		this.userId = userId;
		this.email = email;
		this.phoneNum = phoneNum;
		this.userNm = userNm;
		this.countryCd = countryCd;
	}


}
