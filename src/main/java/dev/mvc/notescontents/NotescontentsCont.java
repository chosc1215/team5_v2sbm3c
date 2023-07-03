package dev.mvc.notescontents;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.notescate.NotescateProcInter;
import dev.mvc.notescate.NotescateVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class NotescontentsCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") 
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.notescate.NotescateProc") 
  private NotescateProcInter notescateProc;
  
  @Autowired
  @Qualifier("dev.mvc.notescontents.NotescontentsProc") 
  private NotescontentsProcInter notescontentsProc;
  
  public NotescontentsCont () {
    System.out.println("-> NotescontentsCont created.");
  }
  
  // 등록 폼, notescontents 테이블은 FK로 notescateno를 사용함.
  // http://localhost:9091/notescontents/create.do  X
  // http://localhost:9091/notescontents/create.do?notescateno=1
  // http://localhost:9091/notescontents/create.do?notescateno=2
  // http://localhost:9091/notescontents/create.do?notescateno=3
  @RequestMapping(value="/notescontents/create.do", method = RequestMethod.GET)
  public ModelAndView create(int notescateno) {
//  public ModelAndView create(HttpServletRequest request,  int notescateno) {
    ModelAndView mav = new ModelAndView();

    NotescateVO notescateVO = this.notescateProc.read(notescateno); // create.jsp에 카테고리 정보를 출력하기위한 목적
    mav.addObject("notescateVO", notescateVO);
//    request.setAttribute("notescateVO", notescateVO);
    
    mav.setViewName("/notescontents/create"); // /webapp/WEB-INF/views/notescontents/create.jsp
    
    return mav;
  }
  
  /**
   * 등록 처리 http://localhost:9091/notescontents/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/notescontents/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, NotescontentsVO notescontentsVO) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image
      String file1saved = "";   // 저장된 파일명, image
      String thumb1 = "";     // preview image

      String upDir =  Notescontents.getUploadDir();
      System.out.println("-> upDir: " + upDir);
      
      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = notescontentsVO.getFile1MF();
      
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
      
      notescontentsVO.setFile1(file1);   // 순수 원본 파일명
      notescontentsVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
      notescontentsVO.setThumb1(thumb1);      // 원본이미지 축소판
      notescontentsVO.setSize1(size1);  // 파일 크기
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 종료
      // ------------------------------------------------------------------------------
      
      // Call By Reference: 메모리 공유, Hashcode 전달
      int adminno = (int)session.getAttribute("adminno"); // adminno FK
      notescontentsVO.setAdminno(adminno);
      int cnt = this.notescontentsProc.create(notescontentsVO); 
      
      // ------------------------------------------------------------------------------
      // PK의 return
      // ------------------------------------------------------------------------------
      // System.out.println("--> notescontentsno: " + notescontentsVO.getNotescontentsno());
      // mav.addObject("notescontentsno", notescontentsVO.getNotescontentsno()); // redirect parameter 적용
      // ------------------------------------------------------------------------------
      
      if (cnt == 1) {
        this.notescateProc.update_cnt_add(notescontentsVO.getNotescateno()); // notescate 테이블 글 수 증가
        mav.addObject("code", "create_success");
        // notescateProc.increaseCnt(notescontentsVO.getNotescateno()); // 글수 증가
      } else { 
        mav.addObject("code", "create_fail"); 
      }
      mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
      
      // System.out.println("--> notescateno: " + notescontentsVO.getNotescateno());
      // redirect시에 hidden tag로 보낸것들이 전달이 안됨으로 request에 다시 저장
      mav.addObject("notescateno", notescontentsVO.getNotescateno()); // redirect parameter 적용
      
      mav.addObject("url", "/notescontents/msg"); // msg.jsp, redirect parameter 적용
      mav.setViewName("redirect:/notescontents/msg.do"); 

    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/notescontents/msg.do"); 
    }
    
    return mav; // forward
  }
  
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * @return
   */
  @RequestMapping(value="/notescontents/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  /**
   * 모든 카테고리의 등록된 글목록, http://localhost:9091/notescontents/list_all.do
   * @return
   */
  @RequestMapping(value="/notescontents/list_all.do", method=RequestMethod.GET)
  public ModelAndView list_all() {
    ModelAndView mav = new ModelAndView();
    
    ArrayList<NotescontentsVO> list = this.notescontentsProc.list_all();
    mav.addObject("list", list);
    
    mav.setViewName("/notescontents/list_all"); // /webapp/WEB-INF/views/notescontents/list_all.jsp
    
    return mav;
  }
  
//  /**
//   * 특정 카테고리의 등록된 글목록
//   * http://localhost:9091/notescontents/list_by_notescateno.do?notescateno=1
//   * http://localhost:9091/notescontents/list_by_notescateno.do?notescateno=2
//   * http://localhost:9091/notescontents/list_by_notescateno.do?notescateno=3
//   * @return
//   */
//  @RequestMapping(value="/notescontents/list_by_notescateno.do", method=RequestMethod.GET)
//  public ModelAndView list_by_notescateno(int notescateno) {
//    ModelAndView mav = new ModelAndView();
//    
//    NotescateVO notescateVO = this.notescateProc.read(notescateno);
//    mav.addObject("notescateVO", notescateVO);
//        
//    ArrayList<NotescontentsVO> list = this.notescontentsProc.list_by_notescateno(notescateno);
//    mav.addObject("list", list);
//    
//    mav.setViewName("/notescontents/list_by_notescateno"); // /webapp/WEB-INF/views/notescontents/list_by_notescateno.jsp
//    
//    return mav;
//  }
  
  // http://localhost:9091/notescontents/read.do
  /**
   * 조회
   * @return
   */
  @RequestMapping(value="/notescontents/read.do", method=RequestMethod.GET )
  public ModelAndView read(int notescontentsno) {
    ModelAndView mav = new ModelAndView();

    NotescontentsVO notescontentsVO = this.notescontentsProc.read(notescontentsno);
    
    String title = notescontentsVO.getTitle();
    String content = notescontentsVO.getContent();
    
    title = Tool.convertChar(title);  // 특수 문자 처리
    content = Tool.convertChar(content); 
    
    notescontentsVO.setTitle(title);
    notescontentsVO.setContent(content);  
    
    long size1 = notescontentsVO.getSize1();
    notescontentsVO.setSize1_label(Tool.unit(size1));    
    
    mav.addObject("notescontentsVO", notescontentsVO); // request.setAttribute("notescontentsVO", notescontentsVO);

    NotescateVO notescateVO = this.notescateProc.read(notescontentsVO.getNotescateno()); // 그룹 정보 읽기
    mav.addObject("notescateVO", notescateVO);
    
    // 회원 번호: admino -> AdminVO -> mname
    String mname = this.adminProc.read(notescontentsVO.getAdminno()).getMname();
    mav.addObject("mname", mname);

    mav.setViewName("/notescontents/read"); // /WEB-INF/views/notescontents/read.jsp
        
    return mav;
  }
  
  /**
   * 맵 등록/수정/삭제 폼
   * http://localhost:9091/notescontents/map.do?notescontentsno=1
   * @return
   */
  @RequestMapping(value="/notescontents/map.do", method=RequestMethod.GET )
  public ModelAndView map(int notescontentsno) {
    ModelAndView mav = new ModelAndView();

    NotescontentsVO notescontentsVO = this.notescontentsProc.read(notescontentsno); // map 정보 읽어 오기
    mav.addObject("notescontentsVO", notescontentsVO); // request.setAttribute("notescontentsVO", notescontentsVO);

    NotescateVO notescateVO = this.notescateProc.read(notescontentsVO.getNotescateno()); // 그룹 정보 읽기
    mav.addObject("notescateVO", notescateVO); 

    mav.setViewName("/notescontents/map"); // /WEB-INF/views/notescontents/map.jsp
        
    return mav;
  }
  
  /**
   * MAP 등록/수정/삭제 처리
   * http://localhost:9091/notescontents/map.do
   * @param notescontentsVO
   * @return
   */
  @RequestMapping(value="/notescontents/map.do", method = RequestMethod.POST)
  public ModelAndView map_update(NotescontentsVO notescontentsVO) {
    ModelAndView mav = new ModelAndView();
    
    this.notescontentsProc.map(notescontentsVO);
    
    mav.setViewName("redirect:/notescontents/read.do?notescontentsno=" + notescontentsVO.getNotescontentsno()); 
    // /webapp/WEB-INF/views/notescontents/read.jsp
    
    return mav; 
  }
 
  
//  /**
//   * 특정 카테고리의 검색된 글목록
//   * http://localhost:9091/notescontents/list_by_notescateno.do?notescateno=8&word=부대찌게
//   * @return
//   */
//  @RequestMapping(value="/notescontents/list_by_notescateno.do", method=RequestMethod.GET)
//  public ModelAndView list_by_notescateno_search(NotescontentsVO notescontentsVO) {
//    ModelAndView mav = new ModelAndView();
//    
//    NotescateVO notescateVO = this.notescateProc.read(notescontentsVO.getNotescateno());
//    mav.addObject("notescateVO", notescateVO);
//        
//    ArrayList<NotescontentsVO> list = this.notescontentsProc.list_by_notescateno_search(notescontentsVO);
//    mav.addObject("list", list);
//    
//    mav.setViewName("/notescontents/list_by_notescateno_search"); // /webapp/WEB-INF/views/notescontents/list_by_notescateno_search.jsp
//    
//    return mav;
//  }
  
  /**
   * 목록 + 검색 + 페이징 지원
   * http://localhost:9091/notescontents/list_by_notescateno.do?notescateno=1&word=스위스&now_page=1
   * 
   * @param notescateno
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/notescontents/list_by_notescateno.do", method = RequestMethod.GET)
  public ModelAndView list_by_notescateno_search_paging(NotescontentsVO notescontentsVO) {
    ModelAndView mav = new ModelAndView();

    // 검색된 전체 글 수
    int search_count = this.notescontentsProc.search_count(notescontentsVO);
    mav.addObject("search_count", search_count);
    
    // 검색 목록: 검색된 레코드를 페이지 단위로 분할하여 가져옴
    ArrayList<NotescontentsVO> list = notescontentsProc.list_by_notescateno_search_paging(notescontentsVO);
    mav.addObject("list", list);
    
    NotescateVO notescateVO = notescateProc.read(notescontentsVO.getNotescateno());
    mav.addObject("notescateVO", notescateVO);

    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param notescateno 카테고리번호
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
     */
    String paging = notescontentsProc.pagingBox(notescontentsVO.getNotescateno(), notescontentsVO.getNow_page(), notescontentsVO.getWord(), "list_by_notescateno.do");
    mav.addObject("paging", paging);

    // mav.addObject("now_page", now_page);
    
    mav.setViewName("/notescontents/list_by_notescateno_search_paging");  // /notescontents/list_by_notescateno_search_paging.jsp

    return mav;
  }

  /**
   * 목록 + 검색 + 페이징 + Grid(갤러리) 지원
   * http://localhost:9091/notescontents/list_by_notescateno_grid.do?notescateno=1&word=스위스&now_page=1
   * 
   * @param notescateno
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/notescontents/list_by_notescateno_grid.do", method = RequestMethod.GET)
  public ModelAndView list_by_notescateno_search_paging_grid(NotescontentsVO notescontentsVO) {
    ModelAndView mav = new ModelAndView();

    // 검색된 전체 글 수
    int search_count = this.notescontentsProc.search_count(notescontentsVO);
    mav.addObject("search_count", search_count);
    
    // 검색 목록
    ArrayList<NotescontentsVO> list = notescontentsProc.list_by_notescateno_search_paging(notescontentsVO);
    mav.addObject("list", list);

    NotescateVO notescateVO = notescateProc.read(notescontentsVO.getNotescateno());
    mav.addObject("notescateVO", notescateVO);

    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param notescateno 카테고리번호
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
     */
    String paging = notescontentsProc.pagingBox(notescontentsVO.getNotescateno(), notescontentsVO.getNow_page(), notescontentsVO.getWord(), "list_by_notescateno_grid.do");
    mav.addObject("paging", paging);

    // mav.addObject("now_page", now_page);
    
    mav.setViewName("/notescontents/list_by_notescateno_search_paging_grid");  // /notescontents/list_by_notescateno_search_paging_grid.jsp

    return mav;
  }
  
  /**
   * 수정 폼
   * http://localhost:9091/notescontents/update_text.do?notescontentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/notescontents/update_text.do", method = RequestMethod.GET)
  public ModelAndView update_text(int notescontentsno) {
    ModelAndView mav = new ModelAndView();
    
    NotescontentsVO notescontentsVO = this.notescontentsProc.read(notescontentsno);
    mav.addObject("notescontentsVO", notescontentsVO);
    
    NotescateVO notescateVO = this.notescateProc.read(notescontentsVO.getNotescateno());
    mav.addObject("notescateVO", notescateVO);
    
    mav.setViewName("/notescontents/update_text"); // /WEB-INF/views/notescontents/update_text.jsp
    // String content = "장소:\n인원:\n준비물:\n비용:\n기타:\n";
    // mav.addObject("content", content);

    return mav; // forward
  }
  
  /**
   * 수정 처리
   * http://localhost:9091/notescontents/update_text.do?notescontentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/notescontents/update_text.do", method = RequestMethod.POST)
  public ModelAndView update_text(HttpSession session, NotescontentsVO notescontentsVO) {
    ModelAndView mav = new ModelAndView();
    
    // System.out.println("-> word: " + notescontentsVO.getWord());
    
    if (this.adminProc.isAdmin(session)) { // 관리자 로그인
      this.notescontentsProc.update_text(notescontentsVO);  
      
      mav.addObject("notescontentsno", notescontentsVO.getNotescontentsno());
      mav.addObject("notescateno", notescontentsVO.getNotescateno());
      mav.setViewName("redirect:/notescontents/read.do");
    } else { // 정상적인 로그인이 아닌 경우
      if (this.notescontentsProc.password_check(notescontentsVO) == 1) {
        this.notescontentsProc.update_text(notescontentsVO);  
         
        // mav 객체 이용
        mav.addObject("notescontentsno", notescontentsVO.getNotescontentsno());
        mav.addObject("notescateno", notescontentsVO.getNotescateno());
        mav.setViewName("redirect:/notescontents/read.do");
      } else {
        mav.addObject("url", "/notescontents/passwd_check"); // /WEB-INF/views/notescontents/passwd_check.jsp
        mav.setViewName("redirect:/notescontents/msg.do");  // POST -> GET -> JSP 출력
      }    
    }
    
    mav.addObject("now_page", notescontentsVO.getNow_page()); // POST -> GET: 데이터 분실이 발생함으로 다시하번 데이터 저장 ★
    
    // URL에 파라미터의 전송
    // mav.setViewName("redirect:/notescontents/read.do?notescontentsno=" + notescontentsVO.getNotescontentsno() + "&notescateno=" + notescontentsVO.getNotescateno());             
    
    return mav; // forward
  }
     
  /**
   * notescontentsno, passwd를 GET 방식으로 전달받아 패스워드 일치 검사를하고 결과 1또는 0을 Console에 출력하세요.
   * http://localhost:9091/notescontents/password_check.do?notescontentsno=2&passwd=123
   * @return
   */
  @RequestMapping(value="/notescontents/password_check.do", method=RequestMethod.GET )
  public ModelAndView password_check(NotescontentsVO notescontentsVO) {
    ModelAndView mav = new ModelAndView();

    int cnt = this.notescontentsProc.password_check(notescontentsVO);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 0) {
      mav.setViewName("/notescontents/passwd_check"); // /WEB-INF/views/notescontents/passwd_check.jsp
    }
        
    return mav;
  }
 
  /**
   * 파일 수정 폼
   * http://localhost:9091/notescontents/update_file.do?notescontentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/notescontents/update_file.do", method = RequestMethod.GET)
  public ModelAndView update_file(int notescontentsno) {
    ModelAndView mav = new ModelAndView();
    
    NotescontentsVO notescontentsVO = this.notescontentsProc.read(notescontentsno);
    mav.addObject("notescontentsVO", notescontentsVO);
    
    NotescateVO notescateVO = this.notescateProc.read(notescontentsVO.getNotescateno());
    mav.addObject("notescateVO", notescateVO);
    
    mav.setViewName("/notescontents/update_file"); // /WEB-INF/views/notescontents/update_file.jsp
 
    return mav; // forward
  }
  
  /**
   * 파일 수정 처리 http://localhost:9091/notescontents/update_file.do
   * 
   * @return
   */
  @RequestMapping(value = "/notescontents/update_file.do", method = RequestMethod.POST)
  public ModelAndView update_file(HttpSession session, NotescontentsVO notescontentsVO) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session)) {
      // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
      NotescontentsVO notescontentsVO_old = notescontentsProc.read(notescontentsVO.getNotescontentsno());
      
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = notescontentsVO_old.getFile1saved();  // 실제 저장된 파일명
      String thumb1 = notescontentsVO_old.getThumb1();       // 실제 저장된 preview 이미지 파일명
      long size1 = 0;
         
      String upDir =  Notescontents.getUploadDir(); // C:/kd/deploy/resort_v2sbm3c/notescontents/storage/
      
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
      MultipartFile mf = notescontentsVO.getFile1MF();
          
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
          
      notescontentsVO.setFile1(file1);
      notescontentsVO.setFile1saved(file1saved);
      notescontentsVO.setThumb1(thumb1);
      notescontentsVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
          
      this.notescontentsProc.update_file(notescontentsVO); // Oracle 처리

      mav.addObject("notescontentsno", notescontentsVO.getNotescontentsno());
      mav.addObject("notescateno", notescontentsVO.getNotescateno());
      mav.setViewName("redirect:/notescontents/read.do"); // request -> param으로 접근 전환
                
    } else {
      mav.addObject("url", "/admin/login_need"); // login_need.jsp, redirect parameter 적용
      mav.setViewName("redirect:/notescontents/msg.do"); // GET
    }

    // redirect하게되면 전부 데이터가 삭제됨으로 mav 객체에 다시 저장
    mav.addObject("now_page", notescontentsVO.getNow_page());
    
    return mav; // forward
  }   

  /**
   * 삭제 폼
   * @param notescontentsno
   * @return
   */
  @RequestMapping(value="/notescontents/delete.do", method=RequestMethod.GET )
  public ModelAndView delete(int notescontentsno) { 
    ModelAndView mav = new  ModelAndView();
    
    // 삭제할 정보를 조회하여 확인
    NotescontentsVO notescontentsVO = this.notescontentsProc.read(notescontentsno);
    mav.addObject("notescontentsVO", notescontentsVO);
    
    NotescateVO notescateVO = this.notescateProc.read(notescontentsVO.getNotescateno());
    mav.addObject("notescateVO", notescateVO);
    
    mav.setViewName("/notescontents/delete");  // /webapp/WEB-INF/views/notescontents/delete.jsp
    
    return mav; 
  }
  
  /**
   * 삭제 처리 http://localhost:9091/notescontents/delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/notescontents/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(NotescontentsVO notescontentsVO) {
    ModelAndView mav = new ModelAndView();
    
    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    NotescontentsVO notescontentsVO_read = notescontentsProc.read(notescontentsVO.getNotescontentsno());
        
    String file1saved = notescontentsVO.getFile1saved();
    String thumb1 = notescontentsVO.getThumb1();
    
    String uploadDir = Notescontents.getUploadDir();
    Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
    Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료
    // -------------------------------------------------------------------
        
    this.notescontentsProc.delete(notescontentsVO.getNotescontentsno()); // DBMS 삭제
    
    this.notescateProc.update_cnt_sub(notescontentsVO.getNotescateno()); // notescate 테이블 글 수 감소
        
    // -------------------------------------------------------------------------------------
    // 마지막 페이지의 마지막 레코드 삭제시의 페이지 번호 -1 처리
    // -------------------------------------------------------------------------------------    
    // 마지막 페이지의 마지막 10번째 레코드를 삭제후
    // 하나의 페이지가 3개의 레코드로 구성되는 경우 현재 9개의 레코드가 남아 있으면
    // 페이지수를 4 -> 3으로 감소 시켜야함, 마지막 페이지의 마지막 레코드 삭제시 나머지는 0 발생
    int now_page = notescontentsVO.getNow_page();
    if (notescontentsProc.search_count(notescontentsVO) % Notescontents.RECORD_PER_PAGE == 0) {
      now_page = now_page - 1; // 삭제시 DBMS는 바로 적용되나 크롬은 새로고침등의 필요로 단계가 작동 해야함.
      if (now_page < 1) {
        now_page = 1; // 시작 페이지
      }
    }
    // -------------------------------------------------------------------------------------

    mav.addObject("notescateno", notescontentsVO.getNotescateno());
    mav.addObject("now_page", now_page);
    mav.setViewName("redirect:/notescontents/list_by_notescateno.do"); 
    
    return mav;
  }   
  
  // http://localhost:9091/notescontents/count_by_notescateno.do?notescateno=1
  @RequestMapping(value = "/notescontents/count_by_notescateno.do", method = RequestMethod.GET)
  public String count_by_notescateno(int notescateno) {
    System.out.println("-> count: " + this.notescontentsProc.count_by_notescateno(notescateno));
    return "";
  
  }
  
  // http://localhost:9091/notescontents/delete_by_notescateno.do?notescateno=1
  // 파일 삭제 -> 레코드 삭제
  @RequestMapping(value = "/notescontents/delete_by_notescateno.do", method = RequestMethod.GET)
  public String delete_by_notescateno(int notescateno) {
    ArrayList<NotescontentsVO> list = this.notescontentsProc.list_by_notescateno(notescateno);
    
    for(NotescontentsVO notescontentsVO : list) {
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = notescontentsVO.getFile1saved();
      String thumb1 = notescontentsVO.getThumb1();
      
      String uploadDir = Notescontents.getUploadDir();
      Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
      Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
    }
    
    System.out.println("-> count: " + this.notescontentsProc.delete_by_notescateno(notescateno));
    
    return "";
  
  }
  
  
}





