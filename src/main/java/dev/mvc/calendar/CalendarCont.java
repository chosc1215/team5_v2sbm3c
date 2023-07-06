package dev.mvc.calendar;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.member.MemberProc;
import dev.mvc.member.MemberProcInter;
import dev.mvc.restcate.RestcateVO;
import dev.mvc.restcontents.Restcontents;
import dev.mvc.restcontents.RestcontentsVO;
import dev.mvc.tool.Tool;

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
  
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * @return
   */
  @RequestMapping(value="/calendar/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
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
    
   
    mav.setViewName("/calendar/msg"); // /WEB-INF/views/calendar/msg.jsp  ---------

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
      mav.addObject("url", "/member/login_need"); // login_need.jsp, redirect parameter 적용---------
      mav.setViewName("redirect:/calendar/msg.do"); // GET
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
  
@RequestMapping(value="/calendar/list_by_label.do", method=RequestMethod.GET)
public ModelAndView list_by_label(String labeldate) {
  ModelAndView mav = new ModelAndView();
  
  
  // 검색 목록: 검색된 레코드를 페이지 단위로 분할하여 가져옴 
  ArrayList<CalendarVO> list = calendarProc.list_by_label(labeldate);
  mav.addObject("list", list);
//  
//  RestcateVO restcateVO = restcateProc.read(restcontentsVO.getRestcateno());
//  mav.addObject("restcateVO", restcateVO);
//  
  /*
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
   * 18 19 20 [다음]
   * @param cateno 카테고리번호
   * @param now_page 현재 페이지
   * @param word 검색어
   * @return 페이징용으로 생성된 HTML/CSS tag 문자열
   */    
//  String paging = restcontentsProc.pagingBox(restcontentsVO.getRestcateno(), restcontentsVO.getNow_page(), restcontentsVO.getWord(), "list_by_restcateno.do");
//  mav.addObject("paging", paging);
  
  mav.setViewName("/calendar/list_by_label");  // /restcontents/list_by_restcateno_search_paging.jsp
  
  return mav;
}

//http://localhost:9091/calendar/read.do?calendarno=1
@RequestMapping(value="/calendar/read.do", method=RequestMethod.GET)
public ModelAndView read(HttpSession session, int calendarno) {
 ModelAndView mav = new ModelAndView();
 
 if (this.adminProc.isAdmin(session) == true) {
   mav.setViewName("/calendar/read"); // /WEB-INF/views/calendar/read.jsp
   
   CalendarVO calendarVO = this.calendarProc.read(calendarno);
   mav.addObject("calendarVO", calendarVO);
 } else {
   mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
 }
 
 return mav;
}



  // 삭제폼, 수정폼을 복사하여 개발 
  // http://localhost:9091/cate/read_delete.do?cateno=1
  @RequestMapping(value="/calendar/read_delete.do", method=RequestMethod.GET)
  public ModelAndView read_delete(HttpSession session, int calendarno) {
  ModelAndView mav = new ModelAndView();
   
  if (this.adminProc.isAdmin(session) == true) {
    mav.setViewName("/calendar/read_delete"); // /WEB-INF/views/cate/read_delete.jsp
    
    CalendarVO calendarVO = this.calendarProc.read(calendarno); // 수정용 데이터
    mav.addObject("calendarVO", calendarVO);
    
    ArrayList<CalendarVO> list = this.calendarProc.list_all(); // 목록 출력용 데이터
    mav.addObject("list", list);
    
  } else {
    mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
  }
  
  return mav;
  }
  
  
  
  // 삭제 처리, 수정 처리를 복사하여 개발 
  @RequestMapping(value="/calendar/delete.do", method=RequestMethod.POST)
  public ModelAndView delete(HttpSession session, int calendarno) { // <form> 태그의 값이 자동으로 저장됨
  //System.out.println("-> cateno: " + cateVO.getCateno());
  //System.out.println("-> name: " + cateVO.getName());
  
  ModelAndView mav = new ModelAndView();
  
  if (this.adminProc.isAdmin(session) == true) {
    int cnt = this.calendarProc.delete(calendarno);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/calendar/list_all.do");       // 자동 주소 이동, Spring 재호출
      
    } else {
      mav.addObject("code", "delete_fail");
      mav.setViewName("/calendar/msg"); // /WEB-INF/views/cate/msg.jsp
    }
    
    mav.addObject("cnt", cnt);
    
  } else {
    mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
  }
  
  return mav;
  }


 
}
