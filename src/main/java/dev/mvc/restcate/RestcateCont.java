package dev.mvc.restcate;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.restcontents.Restcontents;
import dev.mvc.restcontents.RestcontentsProcInter;
import dev.mvc.restcontents.RestcontentsVO;
import dev.mvc.tool.Tool;

@Controller
public class RestcateCont {
  @Autowired
  @Qualifier("dev.mvc.restcate.RestcateProc")  // @Component("dev.mvc.restcate.RestcateProc")
  private RestcateProcInter restcateProc; // RestcateProc 객체가 자동 생성되어 할당됨.
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") 
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.restcontents.RestcontentsProc") 
  private RestcontentsProcInter restcontentsProc;
  
  public RestcateCont() {
    System.out.println("-> RestcateCont created.");
  }
  
  // 등록폼
  // http://localhost:9091/restcate/create.do
  @RequestMapping(value="/restcate/create.do", method=RequestMethod.GET)
  public ModelAndView create(HttpSession session) {
    // System.out.println("-> RestcateCont create()");
    
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      // spring.mvc.view.prefix=/WEB-INF/views/
      // spring.mvc.view.suffix=.jsp
      mav.setViewName("/restcate/create"); // /WEB-INF/views/restcate/create.jsp      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }

    return mav;
  }

  // 등록 처리
  // http://localhost:9091/restcate/create.do
  @RequestMapping(value="/restcate/create.do", method=RequestMethod.POST)
  public ModelAndView create(HttpSession session, RestcateVO restcateVO) { // <form> 태그의 값이 자동으로 저장됨
    // request.getParameter("name"); 자동으로 실행
    // System.out.println("-> name: " + restcateVO.getName());
    
    ModelAndView mav = new ModelAndView();
    
    if (session.getAttribute("admin_id") != null) {
      int cnt = this.restcateProc.create(restcateVO);
      
      if (cnt == 1) {
        // request.setAttribute("code", "create_success"); // 고전적인 jsp 방법 
        // mav.addObject("code", "create_success");
        mav.setViewName("redirect:/restcate/list_all.do");     // 목록으로 자동 이동
        
      } else {
        // request.setAttribute("code", "create_fail");
        mav.addObject("code", "create_fail");
        mav.setViewName("/restcate/msg"); // /WEB-INF/views/restcate/msg.jsp // 등록 실패 메시지 출력

      }
      
      // request.setAttribute("cnt", cnt);
      mav.addObject("cnt", cnt);
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  // http://localhost:9091/restcate/list_all.do
  @RequestMapping(value="/restcate/list_all.do", method=RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/restcate/list_all"); // /WEB-INF/views/restcate/list_all.jsp
      
      ArrayList<RestcateVO> list = this.restcateProc.list_all();
      mav.addObject("list", list);      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  // http://localhost:9091/restcate/read.do?restcateno=1
  @RequestMapping(value="/restcate/read.do", method=RequestMethod.GET)
  public ModelAndView read(HttpSession session, int restcateno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/restcate/read"); // /WEB-INF/views/restcate/read.jsp
      
      RestcateVO restcateVO = this.restcateProc.read(restcateno);
      mav.addObject("restcateVO", restcateVO);
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  // 수정폼
  // http://localhost:9091/restcate/read_update.do?restcateno=1
  // http://localhost:9091/restcate/read_update.do?restcateno=2
  // http://localhost:9091/restcate/read_update.do?restcateno=3
  @RequestMapping(value="/restcate/read_update.do", method=RequestMethod.GET)
  public ModelAndView read_update(HttpSession session, int restcateno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/restcate/read_update"); // /WEB-INF/views/restcate/read_update.jsp
      
      RestcateVO restcateVO = this.restcateProc.read(restcateno); // 수정용 데이터
      mav.addObject("restcateVO", restcateVO);
      
      ArrayList<RestcateVO> list = this.restcateProc.list_all(); // 목록 출력용 데이터
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      
    }
    
    return mav;
  }
  
  // 수정 처리
  @RequestMapping(value="/restcate/update.do", method=RequestMethod.POST)
  public ModelAndView update(HttpSession session, RestcateVO restcateVO) { // <form> 태그의 값이 자동으로 저장됨
//    System.out.println("-> restcateno: " + restcateVO.getRestcateno());
//    System.out.println("-> name: " + restcateVO.getName());
    
    ModelAndView mav = new ModelAndView();

    if (this.adminProc.isAdmin(session) == true) {
      int cnt = this.restcateProc.update(restcateVO);
      
      if (cnt == 1) {
        // request.setAttribute("code", "update_success"); // 고전적인 jsp 방법 
        // mav.addObject("code", "update_success");
        mav.setViewName("redirect:/restcate/list_all.do");       // 자동 주소 이동, Spring 재호출
        
      } else {
        // request.setAttribute("code", "update_fail");
        mav.addObject("code", "update_fail");
        mav.setViewName("/restcate/msg"); // /WEB-INF/views/restcate/msg.jsp
      }
      
      // request.setAttribute("cnt", cnt);
      mav.addObject("cnt", cnt);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  // 삭제폼, 수정폼을 복사하여 개발 
  // http://localhost:9091/cate/read_delete.do?cateno=1
  @RequestMapping(value="/cate/read_delete.do", method=RequestMethod.GET)
  public ModelAndView read_delete(HttpSession session, int restcateno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/restcate/read_delete"); // /WEB-INF/views/cate/read_delete.jsp
      
      RestcateVO restcateVO = this.restcateProc.read(restcateno); // 수정용 데이터
      mav.addObject("restcateVO", restcateVO);
      
      ArrayList<RestcateVO> list = this.restcateProc.list_all(); // 목록 출력용 데이터
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  // 삭제 처리, 수정 처리를 복사하여 개발 
  @RequestMapping(value="/restcate/delete.do", method=RequestMethod.POST)
  public ModelAndView delete(HttpSession session, int restcateno) { // <form> 태그의 값이 자동으로 저장됨
//    System.out.println("-> cateno: " + cateVO.getCateno());
//    System.out.println("-> name: " + cateVO.getName());
    
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      int cnt = this.restcateProc.delete(restcateno);
      
      if (cnt == 1) {
        mav.setViewName("redirect:/restcate/list_all.do");       // 자동 주소 이동, Spring 재호출
        
      } else {
        mav.addObject("code", "delete_fail");
        mav.setViewName("/restcate/msg"); // /WEB-INF/views/cate/msg.jsp
      }
      
      mav.addObject("cnt", cnt);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }  
 
//  // 삭제폼, 수정폼을 복사하여 개발 
//  // http://localhost:9091/restcate/read_delete.do?restcateno=1
//  @RequestMapping(value="/restcate/read_delete.do", method=RequestMethod.GET)
//  public ModelAndView read_delete(HttpSession session, int restcateno) {
//    ModelAndView mav = new ModelAndView();
//    
//    if (this.adminProc.isAdmin(session) == true) {
//      RestcateVO restcateVO = this.restcateProc.read(restcateno); // 수정용 데이터
//      mav.addObject("restcateVO", restcateVO);
//      
//      ArrayList<RestcateVO> list = this.restcateProc.list_all(); // 목록 출력용 데이터
//      mav.addObject("list", list);
//      
//      // 특정 카테고리에 속한 레코드 갯수를 리턴
//      int count_by_restcateno = this.restcontentsProc.count_by_restcateno(restcateno);
//      mav.addObject("count_by_restcateno", count_by_restcateno);
//      
//      mav.setViewName("/restcate/read_delete"); // /WEB-INF/views/restcate/read_delete.jsp
//      
//    } else {
//      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
//    }
//    
//    return mav;
//  }
//   
//  // 삭제 처리, 수정 처리를 복사하여 개발
//  // 자식 테이블 레코드 삭제 -> 부모 테이블 레코드 삭제
//  /**
//   * 카테고리 삭제
//   * @param session
//   * @param restcateno 삭제할 카테고리 번호
//   * @return
//   */
//  @RequestMapping(value="/restcate/delete.do", method=RequestMethod.POST)
//  public ModelAndView delete(HttpSession session, int restcateno) { // <form> 태그의 값이 자동으로 저장됨
////    System.out.println("-> restcateno: " + restcateVO.getRestcateno());
////    System.out.println("-> name: " + restcateVO.getName());
//    
//    ModelAndView mav = new ModelAndView();
//    
//    if (this.adminProc.isAdmin(session) == true) {
//      ArrayList<RestcontentsVO> list = this.restcontentsProc.list_by_restcateno(restcateno); // 자식 레코드 목록 읽기
//      
//      for(RestcontentsVO restcontentsVO : list) { // 자식 레코드 관련 파일 삭제
//        // -------------------------------------------------------------------
//        // 파일 삭제 시작
//        // -------------------------------------------------------------------
//        String file1saved = restcontentsVO.getFile1saved();
//        String thumb1 = restcontentsVO.getThumb1();
//        
//        String uploadDir = Restcontents.getUploadDir();
//        Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
//        Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
//        // -------------------------------------------------------------------
//        // 파일 삭제 종료
//        // -------------------------------------------------------------------
//      }
//      
//      this.restcontentsProc.delete_by_restcateno(restcateno); // 자식 레코드 삭제     
//            
//      int cnt = this.restcateProc.delete(restcateno); // 카테고리 삭제
//      
//      if (cnt == 1) {
//        mav.setViewName("redirect:/restcate/list_all.do");       // 자동 주소 이동, Spring 재호출
//        
//      } else {
//        mav.addObject("code", "delete_fail");
//        mav.setViewName("/restcate/msg"); // /WEB-INF/views/restcate/msg.jsp
//      }
//      
//      mav.addObject("cnt", cnt);
//      
//    } else {
//      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
//    }
//    
//    return mav;
//  }
//  
//  /**
//   * 출력 순서 올림(상향, 10 등 -> 1 등), seqno: 10 -> 1
//   * http://localhost:9091/restcate/update_seqno_decrease.do?restcateno=1
//   * http://localhost:9091/restcate/update_seqno_decrease.do?restcateno=2
//   * @param restcateno
//   * @return
//   */
  @RequestMapping(value = "/restcate/update_seqno_decrease.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_decrease(int restcateno) {
    ModelAndView mav = new ModelAndView();
    
    // seqno 컬럼의 값이 1 초과(1<)일때만 감소를 할 수 있다.
    // 1) 특정 restcateno에 해당하는 seqno 컬럼의 값을 알고 싶다. -> 출력하세요.
    RestcateVO restcateVO = this.restcateProc.read(restcateno);
    int seqno = restcateVO.getSeqno();
    System.out.println("-> restcateno: " + restcateno + " seqno: " + seqno);

    // 2) if 문을 이용한 분기
    if (seqno > 1) {
      int cnt = this.restcateProc.update_seqno_decrease(restcateno); 
      mav.addObject("cnt", cnt);
      
      if (cnt == 1) {
        mav.setViewName("redirect:/restcate/list_all.do");

      } else {
        mav.addObject("code", "update_seqno_decrease_fail");
        mav.setViewName("/restcate/msg"); 
      }
      
    } else {
      mav.setViewName("redirect:/restcate/list_all.do");
    }
    
    return mav;
  }
  
  /**
   * 출력 순서 내림(상향, 1 등 -> 10 등), seqno: 1 -> 10
   * http://localhost:9091/restcate/update_seqno_increase.do?restcateno=1
   * http://localhost:9091/restcate/update_seqno_increase.do?restcateno=2
   * @param restcateno
   * @return
   */
  @RequestMapping(value = "/restcate/update_seqno_increase.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_increase(int restcateno) {
    ModelAndView mav = new ModelAndView();
    int cnt = this.restcateProc.update_seqno_increase(restcateno); 
    mav.addObject("cnt", cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/restcate/list_all.do");

    } else {
      mav.addObject("code", "update_seqno_increase_fail");
      mav.setViewName("/restcate/msg"); 
    }
    
    return mav;
  }

  /**
   * 공개
   * http://localhost:9091/restcate/update_visible_y.do?restcateno=1
   * @param restcateno
   * @return
   */
  @RequestMapping(value = "/restcate/update_visible_y.do", method = RequestMethod.GET)
  public ModelAndView update_visible_y(int restcateno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/restcate/list_all.do");
    
    this.restcateProc.update_visible_y(restcateno);
    
    return mav;
  }
  
  /**
   * 비공개
   * http://localhost:9091/restcate/update_visible_n.do?restcateno=1
   * @param restcateno
   * @return
   */
  @RequestMapping(value = "/restcate/update_visible_n.do", method = RequestMethod.GET)
  public ModelAndView update_visible_n(int restcateno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/restcate/list_all.do");
    
    this.restcateProc.update_visible_n(restcateno);
    
    return mav;
  }
  
  /**
   * 글수 증가
   * http://localhost:9091/restcate/update_cnt_add.do?restcateno=1
   * @param restcateno
   * @return 변경된 레코드 수
   */
  @RequestMapping(value = "/restcate/update_cnt_add.do", method = RequestMethod.GET)
  public ModelAndView update_cnt_add(int restcateno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/restcate/list_all.do");
    
    this.restcateProc.update_cnt_add(restcateno);
    
    return mav;
  }
  
  /**
   * 글수 감소
   * http://localhost:9091/restcate/update_cnt_sub.do?restcateno=1
   * @param restcateno
   * @return
   */  
  @RequestMapping(value = "/restcate/update_cnt_sub.do", method = RequestMethod.GET)
  public ModelAndView update_cnt_sub(int restcateno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/restcate/list_all.do");
    
    this.restcateProc.update_cnt_sub(restcateno);
    
    return mav;
  }
  
}






