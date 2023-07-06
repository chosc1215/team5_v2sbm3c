package dev.mvc.team5;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.notescate.NotescateProcInter;
import dev.mvc.notescate.NotescateVO;
import dev.mvc.recommend.RecommendProcInter;
import dev.mvc.recommend.RecommendVO;
import dev.mvc.restcate.RestcateProcInter;
import dev.mvc.restcate.RestcateVO;

// Setvlet으로 작동함, GET/POST등의 요청을 처리함.
@Controller
public class HomeCont {
  @Autowired
  @Qualifier("dev.mvc.restcate.RestcateProc")  // @Component("dev.mvc.restcate.RestcateProc")
  private RestcateProcInter restcateProc; // RestcateProc 객체가 자동 생성되어 할당됨.
  
  @Autowired
  @Qualifier("dev.mvc.notescate.NotescateProc")  // @Component("dev.mvc.restcate.RestcateProc")
  private NotescateProcInter notscateProc; // RestcateProc 객체가 자동 생성되어 할당됨.
  
  @Autowired
  @Qualifier("dev.mvc.recommend.RecommendProc")
  private RecommendProcInter recommendProc;
  
  public HomeCont() {
    System.out.println("-> HomeCont created.");
  }
  
  // http://localhost:9091/
  // http://localhost:9091/index.do
  @RequestMapping(value= {"/", "/index.do"}, method=RequestMethod.GET)
  public ModelAndView home(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    // spring.mvc.view.prefix=/WEB-INF/views/
    // spring.mvc.view.suffix=.jsp
    boolean recommend_sw = false; // 추천 여부
    
    if(session.getAttribute("memberno") != null) {
      int memberno = (int)(session.getAttribute("memberno"));
      System.out.println("-> memberno:" + memberno);

      //회원 추천 여부
      RecommendVO recommendVO = this.recommendProc.recommend_read(memberno);
      if(recommendVO == null) {
        recommend_sw = true;
      }
    }
    
    mav.addObject("recommend_sw", recommend_sw);
    
    mav.setViewName("/index"); // /WEB-INF/views/index.jsp
    
    return mav;
  }
  
  // http://localhost:9091/menu/top.do
  @RequestMapping(value= {"/menu/top.do"}, method=RequestMethod.GET)
  public ModelAndView top() {
    ModelAndView mav = new ModelAndView();

    ArrayList<RestcateVO> list = this.restcateProc.list_all_y();
    mav.addObject("list", list);
    
    
    
    
    
    mav.setViewName("/menu/top"); // /WEB-INF/views/menu/top.jsp
    
    return mav;
  }
  
  
}

