package dev.mvc.calendar;

import java.util.ArrayList;

public interface CalendarProcInter {
  
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
  public ArrayList<CalendarVO>list_by_label();

}
