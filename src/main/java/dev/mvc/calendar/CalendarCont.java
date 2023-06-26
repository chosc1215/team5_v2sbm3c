package dev.mvc.calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CalendarCont {
  
  @Autowired
  @Qualifier("dev.mvc.calendar.CalendarProc")
  private CalendarProcInter calendarProc;
  
  public CalendarCont() {
    System.out.println("-> CalendarCont created.");
  }
  
  // 등록폼
  // http://localhost:9091/calendar/create.do
  @RequestMapping(value="/calendar/create.do", method=RequestMethod.GET)
  public ModelAndView create() {
    // System.out.println("-> CalendarCont create()");  
    ModelAndView mav = new ModelAndView();
      mav.setViewName("/calendar/create"); // /WEB-INF/views/calendar/create.jsp      

    return mav;
  }
  
//등록 처리
 // http://localhost:9091/calendar/create.do
 @RequestMapping(value="/calendar/create.do", method=RequestMethod.POST)
 public ModelAndView create(CalendarVO calendarVO) { // <form> 태그의 값이 자동으로 저장됨
   // request.getParameter("name"); 자동으로 실행
   // System.out.println("-> name: " + calendarVO.getName());
   
   ModelAndView mav = new ModelAndView();
   mav.setViewName("/calendar/msg"); // /WEB-INF/views/calendar/msg.jsp
   
   int cnt = this.calendarProc.create(calendarVO);
   
   if (cnt == 1) {
     // request.setAttribute("code", "create_success"); // 고전적인 jsp 방법 
     mav.addObject("code", "create_success");
   } else {
     // request.setAttribute("code", "create_fail");
     mav.addObject("code", "create_fail");
   }
   
   // request.setAttribute("cnt", cnt);
   mav.addObject("cnt", cnt);
   
   return mav;
 }

}
 