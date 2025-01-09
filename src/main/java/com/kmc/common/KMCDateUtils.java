package com.kmc.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class KMCDateUtils {
	 /**
		 * 오늘날짜 가져오는 함수(YYYY-MM-DD 포맷)
		 * @return YYYY-MM-DD 형태의 오늘 날짜
	     * ex> '2020-11-17'
		 */
		public static String getToday(){ 
			SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREAN);
			Date currentTime = new Date ();
			String today = sdf.format ( currentTime );

			return today;
		}

		/**
		 * 오늘날짜 가져오는 함수 -시간까지(YYYY-MM-DD HH24:MI:ss포맷)
		 * @return yyyy-MM-dd HH:mm:ss 형태의 오늘 날짜
	     * ex> '2020-11-17 23:59:59'
		 */
		public static String getTodayTime(){ 
			SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
			Date currentTime = new Date ();
			String today = sdf.format ( currentTime );

			return today;
		}

		/**
		 * 오늘날짜 가져오는 함수(timestamp 형태)
		 * @return java.sql.Timestamp 형태의 오늘 날짜
	     * ex> 2020-11-17 11:32:54.341
		 */
	   public static java.sql.Timestamp getTodayTimeStamp(){ 
			Date today = new Date();  /*오늘날짜*/

	        /*java.util.Date 를 java.sql.Timestamp 로 변환한다.*/
			Timestamp tsTimestamp = new Timestamp(today.getTime()) ;   

			return tsTimestamp;
		}

		/**
		 * 오늘날짜 가져오는 함수- 시간까지(USER 포맷)
		 * @param format : 시간포맷
		 * @return 파라미터 형태의 오늘날짜
	     * ex> getTodayTime("yyyyMMdd")    >  '20201117'
	     *     getTodayTime("yyyy-MM-dd")    >  '2020-11-17'
		 */
		public static String getTodayTime(String format){ 
			
			SimpleDateFormat sdf = new SimpleDateFormat (format, Locale.KOREAN);
			Date currentTime = new Date();
			String today = sdf.format (currentTime);

			return today;
		}
		
		
		/**
		 * 전월 구해오기 
		 * @param yyyymm : 년월
		 * @return  String : 전월
	     * ex> getLastMonth('202008')  > '202007'
		 */
		
		public static String getLastMonth(String yyyymm){ 

			String lastMonth = "";

			try {
				SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMM", Locale.KOREAN);
				Calendar cal = Calendar.getInstance();
				Date sMonth = sdf.parse(yyyymm);
				
				cal.setTime(sMonth);
				cal.add(Calendar.MONTH, -1);
				
				lastMonth = sdf.format(cal.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lastMonth;
		}

		/**
		 * 전년 구해오기 
		 * @param yyyy : 일자
	     * @param format : 날짜 포맷
		 * @return 전년도 날짜
	     * ex> getLastYear("20200101", "yyyyMMdd") > '20190101'
	     *     getLastYear("2020", "yyyy") > '2019'
		 */
		
		public static String getLastYear(String yyyyMMdd, String format){ 

			String lastYear = "";

			try {
				SimpleDateFormat sdf = new SimpleDateFormat (format, Locale.KOREAN);
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdf.parse(yyyyMMdd));
				cal.add(Calendar.YEAR, -1);
				
				lastYear = sdf.format(cal.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lastYear;
		}
		
		/**
		 * 월의 마지막일자를 구해오는함수 
		 * @param yyyymm : 년월
		 * @return 해당월의 마지막 날짜
		 * ex> getLastDayOfMonth("202001")  > "20200131"
	     *     getLastDayOfMonth("202004")  > "20200430"
		 */
		
		public static int getLastDayOfMonth(String yyyymm){ 
			//String endOfMonth = "";
			
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.valueOf(yyyymm.substring(0,4)), Integer.valueOf(yyyymm.substring(4,6))-1, 1);
			int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			//endOfMonth = yyyymm + String.valueOf(endDay);

			return endDay;
		}
		
		/**
		 * 파라미터로 넘기는 수 이후의 월 구해오기 
		 * @param yyyymm 년월
	     * @param month 개월수 
		 * @return String : yyyymm으로 부터 month가 지난 년월
		 * ex> getMonthByDiffMonth("202012", 3) > "202103"
	     *     getMonthByDiffMonth("202012", -3) > "202009"
		 */
		public static String getMonthByDiffMonth(String yyyymm, int month){ 
		    String lastMonth = "";

			try {
				SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMM", Locale.KOREAN);
				Calendar cal = Calendar.getInstance();
				Date sMonth = sdf.parse(yyyymm);
				
				cal.setTime(sMonth);
				cal.add(Calendar.MONTH, month);
				
				lastMonth = sdf.format(cal.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lastMonth;
		}

	   /**
		 * 해당일자로 부터 몇일 뒤 또는 몇일 후 를 구하는 함수(yyyyMMdd)
		 * @author swoh1227
		 * @param baseDate 기준일자
		 * @param diffDate 일수
		 * @return
		 */
		public static String getDateByDiffDate(String baseDate, int diffDate){ 
			String result = "";
			Date d = new Date();
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			try {
				d = sdf.parse(baseDate);
				c.setTime(d);
				c.add(Calendar.DAY_OF_MONTH, diffDate);
				d = c.getTime();
				result = sdf.format(d);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return result;
		} 




}
