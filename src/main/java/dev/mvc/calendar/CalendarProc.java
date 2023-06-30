package dev.mvc.calendar;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.calendar.CalendarProc") // Controller가 사용하는 이름
public class CalendarProc implements CalendarProcInter {
  @Autowired
  private CalendarDAOInter calendarDAO; // Spring이 CateDAOInter.java를 구현하여 객체를 자동 생성후 할당
  
  @Override
  public int create(CalendarVO calendarVO) {
    int cnt = this.calendarDAO.create(calendarVO); // Spring이 자동으로 구현한 메소드를 호출
    return cnt;
  }

  @Override
  public ArrayList<CalendarVO> list_all() {
    ArrayList<CalendarVO>list=this.calendarDAO.list_all();
    return list;
  }

  @Override
  public ArrayList<CalendarVO> list_by_label(String labeldate) {
    ArrayList<CalendarVO>list=this.calendarDAO.list_by_label(labeldate);
    return list;
  }


}
 