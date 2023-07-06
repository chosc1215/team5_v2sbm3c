package dev.mvc.restcontents;

import java.util.ArrayList;
import java.util.List;

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
import dev.mvc.recommend.RecommendProcInter;
import dev.mvc.recommend.RecommendVO;
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
  
  @Autowired
  @Qualifier("dev.mvc.recommend.RecommendProc")
  private RecommendProcInter recommendProc;
  
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
  public ModelAndView create(HttpSession session, int restcateno) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) {
      RestcateVO restcateVO = this.restcateProc.read(restcateno);
      mav.addObject("restcateVO", restcateVO);
      mav.setViewName("/restcontents/create");
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/restcontents/msg.do"); 
    }    
    
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
    
    String mname = this.adminProc.read(restcontentsVO.getAdminno()).getMname();
    mav.addObject("mname", mname);
    
    
    
    mav.setViewName("/restcontents/read"); // /WEB-INF/views/restcontents/read.jsp
    
    
    return mav;
    
  }
  /**
   * 맵 등록/수정/삭제 폼
   * @return
   */
  @RequestMapping(value = "/restcontents/map.do", method = RequestMethod.GET)
  public ModelAndView map(HttpSession session, int restcontentsno) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) {
      
      RestcontentsVO restcontentsVO = this.restcontentsProc.read(restcontentsno);
      mav.addObject("restcontentsVO", restcontentsVO);
      
      RestcateVO restcateVO = this.restcateProc.read(restcontentsVO.getRestcateno());
      mav.addObject("restcateVO", restcateVO);
      
      mav.setViewName("/restcontents/map");
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/restcontents/msg.do"); 
    }
    
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
  public ModelAndView youtube(HttpSession session, int restcontentsno) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) {
      RestcontentsVO restcontentsVO = this.restcontentsProc.read(restcontentsno);
      mav.addObject("restcontentsVO", restcontentsVO);
      
      RestcateVO restcateVO = this.restcateProc.read(restcontentsVO.getRestcateno());
      
      mav.addObject("restcateVO", restcateVO);
      mav.setViewName("/restcontents/youtube");
      
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/restcontents/msg.do"); 
    }
    
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
  
  /**
   * 목록 + 검색 + 페이징 지원
   * http://localhost:9093/restcontents/list_by_restcateno.do?restcateno=1&word=스위스&now_page=1
   * 
   * @param restcateno
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/restcontents/list_by_restcateno.do", method = RequestMethod.GET)
  public ModelAndView list_by_restcateno_search_paging(RestcontentsVO restcontentsVO) {
    ModelAndView mav = new ModelAndView();
    
    // 검색된 전체 글 수  
    int search_count = this.restcontentsProc.search_count(restcontentsVO);
    mav.addObject("search_count", search_count);
    
    // 검색 목록: 검색된 레코드를 페이지 단위로 분할하여 가져옴 
    ArrayList<RestcontentsVO> list = restcontentsProc.list_by_restcateno_search_paging(restcontentsVO);
    mav.addObject("list", list);
    
    RestcateVO restcateVO = restcateProc.read(restcontentsVO.getRestcateno());
    mav.addObject("restcateVO", restcateVO);
    
    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param cateno 카테고리번호
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
     */    
    String paging = restcontentsProc.pagingBox(restcontentsVO.getRestcateno(), restcontentsVO.getNow_page(), restcontentsVO.getWord(), "list_by_restcateno.do");
    mav.addObject("paging", paging);
    
    mav.setViewName("/restcontents/list_by_restcateno_search_paging");  // /restcontents/list_by_restcateno_search_paging.jsp
    
    return mav;
  }
  
  /**
   * 목록 + 검색 + 페이징 + Grid(갤러리) 지원
   * http://localhost:9093/restcontents/list_by_restcateno_grid.do?restcateno=1&word=스위스&now_page=1
   * 
   * @param restcateno
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/restcontents/list_by_restcateno_grid.do", method = RequestMethod.GET)
  public ModelAndView list_by_restcateno_search_paging_grid(RestcontentsVO restcontentsVO) {
    ModelAndView mav = new ModelAndView();

    // 검색된 전체 글 수
    int search_count = this.restcontentsProc.search_count(restcontentsVO);
    mav.addObject("search_count", search_count);
    
    // 검색 목록
    ArrayList<RestcontentsVO> list = restcontentsProc.list_by_restcateno_search_paging(restcontentsVO);
    mav.addObject("list", list);

    RestcateVO restcateVO = restcateProc.read(restcontentsVO.getRestcateno());
    mav.addObject("restcateVO", restcateVO);

    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param restcateno 카테고리번호
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
     */
    String paging = restcontentsProc.pagingBox(restcontentsVO.getRestcateno(), restcontentsVO.getNow_page(), restcontentsVO.getWord(), "list_by_restcateno_grid.do");
    mav.addObject("paging", paging);

    // mav.addObject("now_page", now_page);
    
    mav.setViewName("/restcontents/list_by_restcateno_search_paging_grid");  // /restcontents/list_by_restcateno_search_paging_grid.jsp

    return mav;
  }
  
  
  
  /**
   * 수정 폼
   * http://localhost:9093/restcontents/update_text.do?restcontentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/restcontents/update_text.do", method = RequestMethod.GET)
  public ModelAndView update_text(HttpSession session, int restcontentsno) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) {
      RestcontentsVO restcontentsVO = this.restcontentsProc.read(restcontentsno);
      mav.addObject("restcontentsVO", restcontentsVO);
      
      RestcateVO restcateVO = this.restcateProc.read(restcontentsVO.getRestcateno());
      mav.addObject("restcateVO", restcateVO);
      
      mav.setViewName("/restcontents/update_text"); // /WEB-INF/views/contents/update_text.jsp
      // mav.addObject("content", content);      
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/restcontents/msg.do"); 
    }

    return mav; // forward
  }
  
  /**
   * 수정 처리
   * http://localhost:9091/contents/update_text.do?contentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/restcontents/update_text.do", method = RequestMethod.POST)
  public ModelAndView update_text(HttpSession session, RestcontentsVO restcontentsVO) {
    ModelAndView mav = new ModelAndView();
    
    // System.out.println("-> word: " + contentsVO.getWord());
    
    if (this.adminProc.isAdmin(session)) { // 관리자 로그인
      this.restcontentsProc.update_text(restcontentsVO);  
      
      mav.addObject("restcontentsno", restcontentsVO.getRestcontentsno());
      mav.addObject("restcateno", restcontentsVO.getRestcateno());
      mav.setViewName("redirect:/restcontents/read.do");
    } else { // 정상적인 로그인이 아닌 경우
      if (this.restcontentsProc.password_check(restcontentsVO) == 1) {
        this.restcontentsProc.update_text(restcontentsVO);  
         
        // mav 객체 이용
        mav.addObject("restcontentsno", restcontentsVO.getRestcontentsno());
        mav.addObject("restcateno", restcontentsVO.getRestcateno());
        mav.setViewName("redirect:/restcontents/read.do");
      } else {
        mav.addObject("url", "/restcontents/passwd_check"); // /WEB-INF/views/contents/passwd_check.jsp
        mav.setViewName("redirect:/restcontents/msg.do");  // POST -> GET -> JSP 출력
      }    
    }
    
    mav.addObject("now_page", restcontentsVO.getNow_page()); // POST -> GET: 데이터 분실이 발생함으로 다시하번 데이터 저장 ★
    
    // URL에 파라미터의 전송
    // mav.setViewName("redirect:/contents/read.do?contentsno=" + contentsVO.getRestcontentsno() + "&cateno=" + contentsVO.getRestcateno());             
    
    return mav; // forward
  }
  
  /**
   * restcontentsno, passwd를 GET 방식으로 전달받아 패스워드 일치 검사를하고 결과 1또는 0을 Console에 출력하세요.
   * http://localhost:9093/restcontents/password_check.do?restcontentsno=2&passwd=123
   * @return
   */
  @RequestMapping(value="/restcontents/password_check.do", method=RequestMethod.GET )
  public ModelAndView password_check(RestcontentsVO restcontentsVO) {
    ModelAndView mav = new ModelAndView();

    int cnt = this.restcontentsProc.password_check(restcontentsVO);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 0) {
      mav.setViewName("/restcontents/passwd_check"); // /WEB-INF/views/restcontents/passwd_check.jsp
    }
        
    return mav;
  }    
  
  /**
   * 파일 수정 폼
   * http://localhost:9091/contents/update_file.do?contentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/restcontents/update_file.do", method = RequestMethod.GET)
  public ModelAndView update_file(HttpSession session, int restcontentsno) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) {
      RestcontentsVO restcontentsVO = this.restcontentsProc.read(restcontentsno);
      mav.addObject("restcontentsVO", restcontentsVO);
      
      RestcateVO restcateVO = this.restcateProc.read(restcontentsVO.getRestcateno());
      mav.addObject("restcateVO", restcateVO);
      
      mav.setViewName("/restcontents/update_file"); // /WEB-INF/views/contents/update_file.jsp
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/restcontents/msg.do"); 
    }
    
    return mav; // forward
  }
  
  /**
   * 파일 수정 처리 http://localhost:9091/contents/update_file.do
   * 
   * @return
   */
  @RequestMapping(value = "/restcontents/update_file.do", method = RequestMethod.POST)
  public ModelAndView update_file(HttpSession session, RestcontentsVO restcontentsVO) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session)) {
      // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
      RestcontentsVO restcontentsVO_old = restcontentsProc.read(restcontentsVO.getRestcontentsno());
      
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = restcontentsVO_old.getFile1saved();  // 실제 저장된 파일명
      String thumb1 = restcontentsVO_old.getThumb1();       // 실제 저장된 preview 이미지 파일명
      long size1 = 0;
         
      String upDir =  Restcontents.getUploadDir(); // C:/kd/deploy/resort_v2sbm3c/restcontents/storage/
      
      Tool.deleteFile(upDir, file1saved);  // 실제 저장된 파일삭제
      Tool.deleteFile(upDir, thumb1);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
          
      // -------------------------------------------------------------------
      // 파일 전송 시작
      // -------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image

      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = restcontentsVO.getFile1MF();
          
      file1 = mf.getOriginalFilename(); // 원본 파일명
      size1 = mf.getSize();  // 파일 크기
          
      if (size1 > 0) { // 폼에서 새롭게 올리는 파일이 있는지 파일 크기로 체크 ★
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
          thumb1 = Tool.preview(upDir, file1saved, 250, 200); 
        }
        
      } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
        file1="";
        file1saved="";
        thumb1="";
        size1=0;
      } 
          
      restcontentsVO.setFile1(file1);
      restcontentsVO.setFile1saved(file1saved);
      restcontentsVO.setThumb1(thumb1);
      restcontentsVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
          
      this.restcontentsProc.update_file(restcontentsVO); // Oracle 처리

      mav.addObject("restcontentsno", restcontentsVO.getRestcontentsno());
      mav.addObject("cateno", restcontentsVO.getRestcateno());
      mav.setViewName("redirect:/restcontents/read.do"); // request -> param으로 접근 전환
                
    } else {
      mav.addObject("url", "/admin/login_need"); // login_need.jsp, redirect parameter 적용
      mav.setViewName("redirect:/restcontents/msg.do"); // GET
    }

    // redirect하게되면 전부 데이터가 삭제됨으로 mav 객체에 다시 저장
    mav.addObject("now_page", restcontentsVO.getNow_page());
    
    return mav; // forward
  } 
  
  /**
   * 삭제 폼
   * @param restcontentsno
   * @return
   */
  @RequestMapping(value = "/restcontents/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int restcontentsno) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) {
      // 삭제할 정보를 조회하여 확인
      RestcontentsVO restcontentsVO = this.restcontentsProc.read(restcontentsno);
      mav.addObject("restcontentsVO", restcontentsVO);
      
      RestcateVO restcateVO = this.restcateProc.read(restcontentsVO.getRestcateno());
      mav.addObject("restcateVO", restcateVO);
      mav.setViewName("/restcontents/delete"); // /webapp/WEB-INF/views/restcontents/delete.jsp
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/restcontents/msg.do"); 
    }
        
    return mav;
  }
  
  /**
   * 삭제 처리
   * @param restcontentsno
   * @return
   */
  @RequestMapping(value = "/restcontents/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(RestcontentsVO restcontentsVO) {
    ModelAndView mav = new ModelAndView();
    
    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    RestcontentsVO restcontentsVO_read = restcontentsProc.read(restcontentsVO.getRestcontentsno());
        
    String file1saved = restcontentsVO.getFile1saved();
    String thumb1 = restcontentsVO.getThumb1();
    
    String uploadDir = Restcontents.getUploadDir();
    Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
    Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료
    // -------------------------------------------------------------------
        
    this.restcontentsProc.delete(restcontentsVO.getRestcontentsno()); // DBMS 삭제
    
    this.restcateProc.update_cnt_sub(restcontentsVO.getRestcateno()); // restcate 테이블 글 수 감소
        
    // -------------------------------------------------------------------------------------
    // 마지막 페이지의 마지막 레코드 삭제시의 페이지 번호 -1 처리
    // -------------------------------------------------------------------------------------    
    // 마지막 페이지의 마지막 10번째 레코드를 삭제후
    // 하나의 페이지가 3개의 레코드로 구성되는 경우 현재 9개의 레코드가 남아 있으면
    // 페이지수를 4 -> 3으로 감소 시켜야함, 마지막 페이지의 마지막 레코드 삭제시 나머지는 0 발생
    int now_page = restcontentsVO.getNow_page();
    if (restcontentsProc.search_count(restcontentsVO) % Restcontents.RECORD_PER_PAGE == 0) {
      now_page = now_page - 1; // 삭제시 DBMS는 바로 적용되나 크롬은 새로고침등의 필요로 단계가 작동 해야함.
      if (now_page < 1) {
        now_page = 1; // 시작 페이지
      }
    }
    // -------------------------------------------------------------------------------------

    mav.addObject("restcateno", restcontentsVO.getRestcateno());
    mav.addObject("now_page", now_page);
    mav.setViewName("redirect:/restcontents/list_by_restcateno.do"); 
    
    return mav;
  } 

  @RequestMapping(value = "/restcontents/count_by_restcateno.do", method = RequestMethod.GET)
  public String count_by_restcateno(int restcateno) {
    System.out.println("-> count: " + this.restcontentsProc.count_by_restcateno(restcateno));
    return "";
  }
  
  //http://localhost:9091/restcontents/delete_by_restcateno.do?restcateno=1
  // 파일 삭제 -> 레코드 삭제
  @RequestMapping(value = "/restcontents/delete_by_restcateno.do", method = RequestMethod.GET)
  public String delete_by_restcateno(int restcateno) {
    ArrayList<RestcontentsVO> list = this.restcontentsProc.list_by_restcateno(restcateno);
    
    for(RestcontentsVO restcontentsVO : list) {
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = restcontentsVO.getFile1saved();
      String thumb1 = restcontentsVO.getThumb1();
     
      String uploadDir = Restcontents.getUploadDir();
      Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
      Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
    }
   
    System.out.println("-> count: " + this.restcontentsProc.delete_by_restcateno(restcateno));
    
    return "";
   
  }
  
  /**
   * 목록 + 검색 + 페이징 + Grid(갤러리) 지원
   * http://localhost:9093/restcontents/recommend_rdate.do?restcateno=1&word=&now_page=1
   * 
   * @param restcateno
   * @param word
   * @param now_page
   * @return
   */
  /**
   * 관심 카테고리의 좋아요(recom) 기준, 3번 회원이 3번 카테고리를 추천 받는 경우, 추천 상품이 5건일 경우
   * http://localhost:9093/restcontents/recommend_rdate.do
   * @return
   */
  @RequestMapping(value = "/restcontents/recommend_rdate.do", method = RequestMethod.GET)
  public ModelAndView recommend_rdate(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    int memberno = (int)(session.getAttribute("memberno"));
    //System.out.println("-> memberno:" + memberno);
    
    RecommendVO recommendVO = this.recommendProc.recommend_read(memberno);
    //System.out.println("-> restcate:" + recommendVO.getRestcateno());
    
    //관심분야 목록 읽기
    ArrayList<RestcontentsVO> list_rdate = this.restcontentsProc.recommend_rdate(recommendVO.getRestcateno());
    
    mav.addObject("list_rdate", list_rdate);
    
    mav.setViewName("/restcontents/recommend_rdate");
    
    return mav;
  }
  
  
  
  
}
