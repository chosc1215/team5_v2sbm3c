package dev.mvc.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.member.MemberProcInter;
import dev.mvc.restcontents.RestcontentsProcInter;
import dev.mvc.restcontents.RestcontentsVO;

@Controller
public class ReviewCont {
  @Autowired
  @Qualifier("dev.mvc.review.ReviewProc")
  private ReviewProcInter reviewProc;
  @Autowired
  @Qualifier("dev.mvc.restcontents.RestcontentsProc")
  private RestcontentsProcInter restcontentsProc;
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  public ReviewCont() {
    System.out.println("-> ReviewCont created.");
  }
  
  /**
   * 등록 폼 
   * http://localhost:9093/review/create.do?restcontents=2
   * @param restcontentsno
   * @return
   */
  @RequestMapping(value = "/review/create.do", method = RequestMethod.GET)
  public ModelAndView create(int restcontentsno) {
    ModelAndView mav = new ModelAndView();
    
    RestcontentsVO restcontentsVO = this.restcontentsProc.read(restcontentsno);
    mav.addObject("restcontentsVO", restcontentsVO);
     
    mav.setViewName("/review/create");
    
    return mav;
  }
  
  

}
