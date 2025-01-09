package com.kmc.main.room.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResveManageDto {


	private String userId;
	private String roomNo;
	private String year;
	private String month;
	private String day;
	private String ymd;
	private String yyyyMM;
	private int checkYn;
	private String currYmd;

	private int familyCnt;

	private String startYmd;
	private String endYmd;
	private Date startDate;
	private Date endDate;
	private Date resvDate;

	private String startMonthDay;
	private String endMonthDay;
	private String startMonth;
	private String endMonth;
	private String startDay;
	private String endDay;

	private String mCd;



}

