package dev.mvc.calendar;

import java.util.ArrayList;

public interface CalendarDAOInter {
  // <insert id="create" parameterType="dev.mvc.cate.CateVO">
  // 개발자는 create 메소드 구현을 SpringFramework에서 미룸, 위임.
  
  /**
   * 등록
   * @param cateVO
   * @return 등록된 갯수
   */
  public int create(CalendarVO calendarVO); // 추상 메소드
  
  /**
   * 목록
   * @return
   */
  public ArrayList<CalendarVO>list_all();
  
  /**
   * 6월달만 출력
   * @return
   */
  public ArrayList<CalendarVO>list_by_label(String labeldate);
  
  
  

}