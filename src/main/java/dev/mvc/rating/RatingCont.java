package dev.mvc.rating;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.calendar.CalendarVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.restcontents.RestcontentsProcInter;

@Controller
public class RatingCont {
  @Autowired
  @Qualifier("dev.mvc.rating.RatingProc")
  private RatingProcInter ratingProc;
  
  @Autowired
  @Qualifier("dev.mvc.restcontents.RestcontentsProc")
  private RestcontentsProcInter restcontentsProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * @return
   */
  @RequestMapping(value="/rating/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }

  // 등록폼
  // http://localhost:9093/rating/create.do
  @RequestMapping(value = "/rating/create.do", method = RequestMethod.GET)
  public ModelAndView create() {
    // System.out.println("-> CalendarCont create()");
    ModelAndView mav = new ModelAndView(); 
    mav.setViewName("/rating/create"); // /WEB-INF/views/calendar/create.jsp

    return mav;
  } 

  // 등록 처리
  // http://localhost:9093/rating/create.do
  @RequestMapping(value = "/rating/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpSession session, RatingVO ratingVO) { // <form> 태그의 값이 자동으로 저장됨
    // request.getParameter("name"); 자동으로 실행
    // System.out.println("-> name: " + calendarVO.getName());

    ModelAndView mav = new ModelAndView();
   
    mav.setViewName("/rating/msg"); // /WEB-INF/views/calendar/msg.jsp  ---------

    if (this.memberProc.isMember(session) == true) {
      
      int memberno = (int)session.getAttribute("memberno"); // 본인의 회원 정보 조회
      ratingVO.setMemberno(memberno);

      int cnt = this.ratingProc.create(ratingVO);

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
  
  
}
