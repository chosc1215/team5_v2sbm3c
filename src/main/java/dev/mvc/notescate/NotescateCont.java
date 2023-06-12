package dev.mvc.notescate;

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
import dev.mvc.tool.Tool;

@Controller
public class NotescateCont {
  @Autowired
  @Qualifier("dev.mvc.notescate.NotescateProc")  // @Component("dev.mvc.notescate.NotescateProc")
  private NotescateProcInter notescateProc; // NotescateProc 객체가 자동 생성되어 할당됨.
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") 
  private AdminProcInter adminProc;
  
 
  
  public NotescateCont() {
    System.out.println("-> NotescateCont created.");
  }
  
  // 등록폼
  // http://localhost:9091/notescate/create.do
  @RequestMapping(value="/notescate/create.do", method=RequestMethod.GET)
  public ModelAndView create(HttpSession session) {
    // System.out.println("-> NotescateCont create()");
    
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      // spring.mvc.view.prefix=/WEB-INF/views/
      // spring.mvc.view.suffix=.jsp
      mav.setViewName("/notescate/create"); // /WEB-INF/views/notescate/create.jsp      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }

    return mav;
  }

  // 등록 처리
  // http://localhost:9093/notescate/create.do
  @RequestMapping(value="/notescate/create.do", method=RequestMethod.POST)
  public ModelAndView create(HttpSession session, NotescateVO notescateVO) { // <form> 태그의 값이 자동으로 저장됨
    // request.getParameter("name"); 자동으로 실행
    // System.out.println("-> name: " + notescateVO.getName());
    
    ModelAndView mav = new ModelAndView();
    
    if (session.getAttribute("admin_id") != null) {
      int cnt = this.notescateProc.create(notescateVO);
      
      if (cnt == 1) {
        // request.setAttribute("code", "create_success"); // 고전적인 jsp 방법 
        // mav.addObject("code", "create_success");
        mav.setViewName("redirect:/notescate/list_all.do");     // 목록으로 자동 이동
        
      } else {
        // request.setAttribute("code", "create_fail");
        mav.addObject("code", "create_fail");
        mav.setViewName("/notescate/msg"); // /WEB-INF/views/notescate/msg.jsp // 등록 실패 메시지 출력

      }
      
      // request.setAttribute("cnt", cnt);
      mav.addObject("cnt", cnt);
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  // http://localhost:9091/notescate/list_all.do
  @RequestMapping(value="/notescate/list_all.do", method=RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/notescate/list_all"); // /WEB-INF/views/notescate/list_all.jsp
      
    ArrayList<NotescateVO> list = this.notescateProc.list_all();
    mav.addObject("list", list);      

    return mav;
  }
  
  // http://localhost:9091/notescate/read.do?notescateno=1
  @RequestMapping(value="/notescate/read.do", method=RequestMethod.GET)
  public ModelAndView read(HttpSession session, int notescateno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/notescate/read"); // /WEB-INF/views/notescate/read.jsp
      
      NotescateVO notescateVO = this.notescateProc.read(notescateno);
      mav.addObject("notescateVO", notescateVO);
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  // 수정폼
  // http://localhost:9091/notescate/read_update.do?notescateno=1
  // http://localhost:9091/notescate/read_update.do?notescateno=2
  // http://localhost:9091/notescate/read_update.do?notescateno=3
  @RequestMapping(value="/notescate/read_update.do", method=RequestMethod.GET)
  public ModelAndView read_update(HttpSession session, int notescateno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/notescate/read_update"); // /WEB-INF/views/notescate/read_update.jsp
      
      NotescateVO notescateVO = this.notescateProc.read(notescateno); // 수정용 데이터
      mav.addObject("notescateVO", notescateVO);
      
      ArrayList<NotescateVO> list = this.notescateProc.list_all(); // 목록 출력용 데이터
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      
    }
    
    return mav;
  }
  
  // 수정 처리
  @RequestMapping(value="/notescate/update.do", method=RequestMethod.POST)
  public ModelAndView update(HttpSession session, NotescateVO notescateVO) { // <form> 태그의 값이 자동으로 저장됨
//    System.out.println("-> notescateno: " + notescateVO.getNotescateno());
//    System.out.println("-> name: " + notescateVO.getName());
    
    ModelAndView mav = new ModelAndView();

    if (this.adminProc.isAdmin(session) == true) {
      int cnt = this.notescateProc.update(notescateVO);
      
      if (cnt == 1) {
        // request.setAttribute("code", "update_success"); // 고전적인 jsp 방법 
        // mav.addObject("code", "update_success");
        mav.setViewName("redirect:/notescate/list_all.do");       // 자동 주소 이동, Spring 재호출
        
      } else {
        // request.setAttribute("code", "update_fail");
        mav.addObject("code", "update_fail");
        mav.setViewName("/notescate/msg"); // /WEB-INF/views/notescate/msg.jsp
      }
      
      // request.setAttribute("cnt", cnt);
      mav.addObject("cnt", cnt);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
 
  // 삭제폼, 수정폼을 복사하여 개발 
  // http://localhost:9091/notescate/read_delete.do?notescateno=1
  /*
   * @RequestMapping(value="/notescate/read_delete.do", method=RequestMethod.GET)
   * public ModelAndView read_delete(HttpSession session, int notescateno) {
   * ModelAndView mav = new ModelAndView();
   * 
   * if (this.adminProc.isAdmin(session) == true) { NotescateVO notescateVO =
   * this.notescateProc.read(notescateno); // 수정용 데이터 mav.addObject("notescateVO",
   * notescateVO);
   * 
   * ArrayList<NotescateVO> list = this.notescateProc.list_all(); // 목록 출력용 데이터
   * mav.addObject("list", list);
   * 
   * // 특정 카테고리에 속한 레코드 갯수를 리턴 int count_by_notescateno =
   * this.contentsProc.count_by_notescateno(notescateno);
   * mav.addObject("count_by_notescateno", count_by_notescateno);
   * 
   * mav.setViewName("/notescate/read_delete"); //
   * /WEB-INF/views/notescate/read_delete.jsp
   * 
   * } else { mav.setViewName("/admin/login_need"); //
   * /WEB-INF/views/admin/login_need.jsp }
   * 
   * return mav; }
   */
  
  // 삭제 처리, 수정 처리를 복사하여 개발
  // 자식 테이블 레코드 삭제 -> 부모 테이블 레코드 삭제
  /**
   * 카테고리 삭제
   * @param session
   * @param notescateno 삭제할 카테고리 번호
   * @return
   */
  /*
   * @RequestMapping(value="/notescate/delete.do", method=RequestMethod.POST)
   * public ModelAndView delete(HttpSession session, int notescateno) { // <form>
   * 태그의 값이 자동으로 저장됨 // System.out.println("-> notescateno: " +
   * notescateVO.getNotescateno()); // System.out.println("-> name: " +
   * notescateVO.getName());
   * 
   * ModelAndView mav = new ModelAndView();
   * 
   * if (this.adminProc.isAdmin(session) == true) { ArrayList<ContentsVO> list =
   * this.contentsProc.list_by_notescateno(notescateno); // 자식 레코드 목록 읽기
   * 
   * for(ContentsVO contentsVO : list) { // 자식 레코드 관련 파일 삭제 //
   * ------------------------------------------------------------------- // 파일 삭제
   * 시작 // -------------------------------------------------------------------
   * String file1saved = contentsVO.getFile1saved(); String thumb1 =
   * contentsVO.getThumb1();
   * 
   * String uploadDir = Contents.getUploadDir(); Tool.deleteFile(uploadDir,
   * file1saved); // 실제 저장된 파일삭제 Tool.deleteFile(uploadDir, thumb1); // preview
   * 이미지 삭제 // -------------------------------------------------------------------
   * // 파일 삭제 종료 //
   * ------------------------------------------------------------------- }
   * 
   * this.contentsProc.delete_by_notescateno(notescateno); // 자식 레코드 삭제
   * 
   * int cnt = this.notescateProc.delete(notescateno); // 카테고리 삭제
   * 
   * if (cnt == 1) { mav.setViewName("redirect:/notescate/list_all.do"); // 자동 주소
   * 이동, Spring 재호출
   * 
   * } else { mav.addObject("code", "delete_fail");
   * mav.setViewName("/notescate/msg"); // /WEB-INF/views/notescate/msg.jsp }
   * 
   * mav.addObject("cnt", cnt);
   * 
   * } else { mav.setViewName("/admin/login_need"); //
   * /WEB-INF/views/admin/login_need.jsp }
   * 
   * return mav; }
   */
  
  /**
   * 출력 순서 올림(상향, 10 등 -> 1 등), seqno: 10 -> 1
   * http://localhost:9091/notescate/update_seqno_decrease.do?notescateno=1
   * http://localhost:9091/notescate/update_seqno_decrease.do?notescateno=2
   * @param notescateno
   * @return
   */
  @RequestMapping(value = "/notescate/update_seqno_decrease.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_decrease(int notescateno) {
    ModelAndView mav = new ModelAndView();
    
    // seqno 컬럼의 값이 1 초과(1<)일때만 감소를 할 수 있다.
    // 1) 특정 notescateno에 해당하는 seqno 컬럼의 값을 알고 싶다. -> 출력하세요.
    NotescateVO notescateVO = this.notescateProc.read(notescateno);
    int seqno = notescateVO.getSeqno();
    System.out.println("-> notescateno: " + notescateno + " seqno: " + seqno);

    // 2) if 문을 이용한 분기
    if (seqno > 1) {
      int cnt = this.notescateProc.update_seqno_decrease(notescateno); 
      mav.addObject("cnt", cnt);
      
      if (cnt == 1) {
        mav.setViewName("redirect:/notescate/list_all.do");

      } else {
        mav.addObject("code", "update_seqno_decrease_fail");
        mav.setViewName("/notescate/msg"); 
      }
      
    } else {
      mav.setViewName("redirect:/notescate/list_all.do");
    }
    
    return mav;
  }
  
  /**
   * 출력 순서 내림(상향, 1 등 -> 10 등), seqno: 1 -> 10
   * http://localhost:9091/notescate/update_seqno_increase.do?notescateno=1
   * http://localhost:9091/notescate/update_seqno_increase.do?notescateno=2
   * @param notescateno
   * @return
   */
  @RequestMapping(value = "/notescate/update_seqno_increase.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_increase(int notescateno) {
    ModelAndView mav = new ModelAndView();
    int cnt = this.notescateProc.update_seqno_increase(notescateno); 
    mav.addObject("cnt", cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/notescate/list_all.do");

    } else {
      mav.addObject("code", "update_seqno_increase_fail");
      mav.setViewName("/notescate/msg"); 
    }
    
    return mav;
  }

  /**
   * 공개
   * http://localhost:9091/notescate/update_visible_y.do?notescateno=1
   * @param notescateno
   * @return
   */
  @RequestMapping(value = "/notescate/update_visible_y.do", method = RequestMethod.GET)
  public ModelAndView update_visible_y(int notescateno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/notescate/list_all.do");
    
    this.notescateProc.update_visible_y(notescateno);
    
    return mav;
  }
  
  /**
   * 비공개
   * http://localhost:9091/notescate/update_visible_n.do?notescateno=1
   * @param notescateno
   * @return
   */
  @RequestMapping(value = "/notescate/update_visible_n.do", method = RequestMethod.GET)
  public ModelAndView update_visible_n(int notescateno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/notescate/list_all.do");
    
    this.notescateProc.update_visible_n(notescateno);
    
    return mav;
  }
  
  /**
   * 글수 증가
   * http://localhost:9091/notescate/update_cnt_add.do?notescateno=1
   * @param notescateno
   * @return 변경된 레코드 수
   */
  @RequestMapping(value = "/notescate/update_cnt_add.do", method = RequestMethod.GET)
  public ModelAndView update_cnt_add(int notescateno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/notescate/list_all.do");
    
    this.notescateProc.update_cnt_add(notescateno);
    
    return mav;
  }
  
  /**
   * 글수 감소
   * http://localhost:9091/notescate/update_cnt_sub.do?notescateno=1
   * @param notescateno
   * @return
   */  
  @RequestMapping(value = "/notescate/update_cnt_sub.do", method = RequestMethod.GET)
  public ModelAndView update_cnt_sub(int notescateno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/notescate/list_all.do");
    
    this.notescateProc.update_cnt_sub(notescateno);
    
    return mav;
  }
  
}






