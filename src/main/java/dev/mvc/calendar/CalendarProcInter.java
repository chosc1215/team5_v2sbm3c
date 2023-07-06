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
   * 조회, 읽기
   * @param calendarno
   * @return
   */
  public CalendarVO read(int calendarno);
  
  /**
   * 6월달만 출력
   * @return
   */
  public ArrayList<CalendarVO>list_by_label(String labeldate);
  
  /**
   * 삭제
   * @param calendarno
   * @return 삭제된 레코드 갯수를 리턴
   */
  public int delete(int calendarno);

}
