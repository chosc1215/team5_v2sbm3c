package dev.mvc.calendar;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.member.MemberProc;
import dev.mvc.member.MemberProcInter;

@Controller
public class CalendarCont {

  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;

  @Autowired
  @Qualifier("dev.mvc.calendar.CalendarProc")
  private CalendarProcInter calendarProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  public CalendarCont() {
    System.out.println("-> CalendarCont created.");
  }

  // 등록폼
  // http://localhost:9091/calendar/create.do
  @RequestMapping(value = "/calendar/create.do", method = RequestMethod.GET)
  public ModelAndView create() {
    // System.out.println("-> CalendarCont create()");
    ModelAndView mav = new ModelAndView(); 
    mav.setViewName("/calendar/create"); // /WEB-INF/views/calendar/create.jsp

    return mav;
  } 

  // 등록 처리
  // http://localhost:9091/calendar/create.do
  @RequestMapping(value = "/calendar/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpSession session, CalendarVO calendarVO) { // <form> 태그의 값이 자동으로 저장됨
    // request.getParameter("name"); 자동으로 실행
    // System.out.println("-> name: " + calendarVO.getName());

    ModelAndView mav = new ModelAndView();
    mav.setViewName("/calendar/msg"); // /WEB-INF/views/calendar/msg.jsp

    if (this.memberProc.isMember(session) == true) {
      
      int memberno = (int)session.getAttribute("memberno"); // 본인의 회원 정보 조회
      calendarVO.setMemberno(memberno);

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
    } else {
      mav.setViewName("/member/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    


    return mav;
  }

  // http://localhost:9091/calendar/list_all.do
  @RequestMapping(value = "/calendar/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();

    if (this.memberProc.isMember(session) == true) {
      mav.setViewName("/calendar/list_all"); // /WEB-INF/views/calendar/list_all.jsp

      ArrayList<CalendarVO> list = this.calendarProc.list_all();
      mav.addObject("list", list);
    } else {
      mav.setViewName("/member/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }

    return mav;
  }

}
