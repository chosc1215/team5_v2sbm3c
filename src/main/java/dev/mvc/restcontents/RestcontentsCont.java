package dev.mvc.restcontents;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.restcontents.Restcontents;
import dev.mvc.restcontents.RestcontentsVO;
import dev.mvc.restcate.RestcateProc;
import dev.mvc.restcate.RestcateProcInter;
import dev.mvc.restcate.RestcateVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class RestcontentsCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.restcate.RestcateProc")
  private RestcateProcInter restcateProc;

  @Autowired
  @Qualifier("dev.mvc.restcontents.RestcontentsProc")
  private RestcontentsProcInter restcontentsProc;
  
  public RestcontentsCont () {
    System.out.println("-> RestcontentsCont created.");
  }

  /**
   * 등록 폼, restcontents 테이블은 FK로 restcateno를 사용함.
   * http://localhost:9093/restcontents/create.do?restcateno=1
   * @param restcateno
   * @return
   */
  @RequestMapping(value="/restcontents/create.do", method = RequestMethod.GET)
  public ModelAndView create(int restcateno) {
    ModelAndView mav = new ModelAndView();
    
    RestcateVO restcateVO = this.restcateProc.read(restcateno);
    mav.addObject("restcateVO", restcateVO);
    
    mav.setViewName("/restcontents/create");
    
    return mav;
  }
  
  /**
   * 등록 처리 http://localhost:9091/restcontents/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/restcontents/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, RestcontentsVO restcontentsVO) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image
      String file1saved = "";   // 저장된 파일명, image
      String thumb1 = "";     // preview image

      String upDir =  Restcontents.getUploadDir();
      System.out.println("-> upDir: " + upDir);
      
      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = restcontentsVO.getFile1MF();
      
      file1 = Tool.getFname(mf.getOriginalFilename()); // 원본 순수 파일명 산출
      System.out.println("-> file1: " + file1);
      
      long size1 = mf.getSize();  // 파일 크기
      
      if (size1 > 0) { // 파일 크기 체크
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
          thumb1 = Tool.preview(upDir, file1saved, 200, 150); 
        }
        
      }    
      
      restcontentsVO.setFile1(file1);   // 순수 원본 파일명
      restcontentsVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
      restcontentsVO.setThumb1(thumb1);      // 원본이미지 축소판
      restcontentsVO.setSize1(size1);  // 파일 크기
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 종료
      // ------------------------------------------------------------------------------
      
      // Call By Reference: 메모리 공유, Hashcode 전달
      int adminno = (int)session.getAttribute("adminno"); // adminno FK
      restcontentsVO.setAdminno(adminno);
      int cnt = this.restcontentsProc.create(restcontentsVO); 
      
      // ------------------------------------------------------------------------------
      // PK의 return
      // ------------------------------------------------------------------------------
      // System.out.println("--> restcontentsno: " + restcontentsVO.getRestcontentsno());
      // mav.addObject("restcontentsno", restcontentsVO.getRestcontentsno()); // redirect parameter 적용
      // ------------------------------------------------------------------------------
      
      if (cnt == 1) {
        this.restcateProc.update_cnt_add(restcontentsVO.getRestcateno()); // restcate 테이블 글 수 증가
        mav.addObject("code", "create_success");
        // restcateProc.increaseCnt(restcontentsVO.getRestcateno()); // 글수 증가
      } else {
        mav.addObject("code", "create_fail");
      }
      mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
      
      // System.out.println("--> restcateno: " + restcontentsVO.getRestcateno());
      // redirect시에 hidden tag로 보낸것들이 전달이 안됨으로 request에 다시 저장
      mav.addObject("restcateno", restcontentsVO.getRestcateno()); // redirect parameter 적용
      
      mav.addObject("url", "/restcontents/msg"); // msg.jsp, redirect parameter 적용
      mav.setViewName("redirect:/restcontents/msg.do"); 

    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/restcontents/msg.do"); 
    }
    
    return mav; // forward
  }

  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * @return
   */
  @RequestMapping(value="/restcontents/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  /**
   * 모든 카테고리의 등록된 글목록, http://localhost:9091/restcontents/list_all.do
   * @return
   */
  @RequestMapping(value = "/restcontents/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all() {
    ModelAndView mav = new ModelAndView();
    
    ArrayList<RestcontentsVO> list = this.restcontentsProc.list_all();
    mav.addObject("list", list);
    
    mav.setViewName("/restcontents/list_all");
    
    return mav;
  }

  /**
   * 특정 카테고리의 등록된 글목록
   * http://localhost:9091/restcontents/list_by_restcateno.do?restcateno=1
   * http://localhost:9091/restcontents/list_by_restcateno.do?restcateno=2
   * http://localhost:9091/restcontents/list_by_restcateno.do?restcateno=3
   * @return
   */
  @RequestMapping(value="/restcontents/list_by_restcateno.do", method=RequestMethod.GET)
  public ModelAndView list_by_restcateno(int restcateno) {
    ModelAndView mav = new ModelAndView();
    
    RestcateVO restcateVO = this.restcateProc.read(restcateno);
    mav.addObject("restcateVO", restcateVO);
        
    ArrayList<RestcontentsVO> list = this.restcontentsProc.list_by_restcateno(restcateno);
    mav.addObject("list", list);
    
    mav.setViewName("/restcontents/list_by_restcateno"); // /webapp/WEB-INF/views/contents/list_by_cateno.jsp
    
    return mav;
  }  
  /**
   * 조회 */
  @RequestMapping(value = "/restcontents/read.do", method = RequestMethod.GET)
  public ModelAndView read(int restcontentsno) {
    ModelAndView mav = new ModelAndView();
    RestcontentsVO restcontentsVO = this.restcontentsProc.read(restcontentsno);
    
    String title = restcontentsVO.getTitle();
    String content = restcontentsVO.getContent();
    
    title = Tool.convertChar(title); // 특수 문자 처리
    content = Tool.convertChar(content);
    
    restcontentsVO.setTitle(title);
    restcontentsVO.setContent(content);
    
    long size1 = restcontentsVO.getSize1();
    restcontentsVO.setSize1_label(Tool.unit(size1));
    
    mav.addObject("restcontentsVO", restcontentsVO);
    
    RestcateVO restcateVO = this.restcateProc.read(restcontentsVO.getRestcateno());
    mav.addObject("restcateVO", restcateVO);
    
    mav.setViewName("/restcontents/read"); // /WEB-INF/views/restcontents/read.jsp
    
    return mav;
    
  }
  /**
   * 맵 등록/수정/삭제 폼
   * @return
   */
  @RequestMapping(value = "/restcontents/map.do", method = RequestMethod.GET)
  public ModelAndView map(int restcontentsno) {
    ModelAndView mav = new ModelAndView();
    
    RestcontentsVO restcontentsVO = this.restcontentsProc.read(restcontentsno);
    mav.addObject("restcontentsVO", restcontentsVO);
    
    RestcateVO restcateVO = this.restcateProc.read(restcontentsVO.getRestcateno());
    mav.addObject("restcateVO", restcateVO);
    
    mav.setViewName("/restcontents/map");
    return mav;
        
  }
  
  /**
   * 맵 등록/수정/삭제 처리
   * @return
   */
  @RequestMapping(value="/restcontents/map.do", method = RequestMethod.POST)
  public ModelAndView map_update(RestcontentsVO restcontentsVO) {
    ModelAndView mav = new ModelAndView();
    
    this.restcontentsProc.map(restcontentsVO);
    
    mav.setViewName("redirect:/restcontents/read.do?restcontentsno=" + restcontentsVO.getRestcontentsno());
    
    return mav;
  }
  
  /**
   * 유튜브 등록/수정/삭제 폼
   * @return
   */
  @RequestMapping(value = "/restcontents/youtube.do", method = RequestMethod.GET)
  public ModelAndView youtube(int restcontentsno) {
    ModelAndView mav = new ModelAndView();
    
    RestcontentsVO restcontentsVO = this.restcontentsProc.read(restcontentsno);
    mav.addObject("restcontentsVO", restcontentsVO);
    
    RestcateVO restcateVO = this.restcateProc.read(restcontentsVO.getRestcateno());
    mav.addObject("restcateVO", restcateVO);
    
    mav.setViewName("/restcontents/youtube");
    return mav;
        
  }
  
  /**
   * 유튜브 등록/수정/삭제 처리
   */
  @RequestMapping(value="/restcontents/youtube.do", method = RequestMethod.POST)
  public ModelAndView youtube_update(RestcontentsVO restcontentsVO) {
    ModelAndView mav = new ModelAndView();
    
    if (restcontentsVO.getYoutube().trim().length() > 0) { // 삭제 중인지 확인, 삭제가 아니면 youtube 크기 변경
      // youtube 영상의 크기를 width 기준 640 px로 변경 
      String youtube = Tool.youtubeResize(restcontentsVO.getYoutube());
      restcontentsVO.setYoutube(youtube);
    }
    
    this.restcontentsProc.youtube(restcontentsVO);

    // youtube 크기 조절
    // <iframe width="1019" height="573" src="https://www.youtube.com/embed/Aubh5KOpz-4" title="교보문고에서 가장 잘나가는 일본 추리소설 베스트셀러 10위부터 1위까지 소개해드려요📚" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
    
    
    mav.setViewName("redirect:/restcontents/read.do?restcontentsno=" + restcontentsVO.getRestcontentsno()); 
    // /webapp/WEB-INF/views/contents/read.jsp
    
    return mav;
  }

  
  
  
}
