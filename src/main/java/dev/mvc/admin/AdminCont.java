package dev.mvc.admin;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import dev.mvc.tool.Tool; 

@Controller
public class AdminCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc = null;
  
  public AdminCont() {
    System.out.println("-> AdminCont created.");
  }
  
  // http://localhost:9093/admin/checkid.do?id=admin1
  @ResponseBody
  @RequestMapping(value="/admin/checkid.do", method=RequestMethod.GET ,
                produces = "text/plain;charset=UTF-8" )
  public String checkid(String id) {
    int cnt = this.adminProc.checkid(id);
    
    JSONObject json = new JSONObject();
    json.put("cnt", cnt);
    
    return json.toString();
  }
  
  /**
   * 관리자회원 등록 폼
   * @return
   */
  @RequestMapping(value = "/admin/create.do", method = RequestMethod.GET)
  public ModelAndView create() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/admin/create");
    
    return mav;
  }
  
  /**
   * 관리자회원 등록 처리
   * @param adminVO
   * @return
   */
  @RequestMapping(value="/admin/create.do", method=RequestMethod.POST)
  public ModelAndView create(AdminVO adminVO) {
    ModelAndView mav = new ModelAndView();
    
    adminVO.setGrade(1);
    
    int cnt = adminProc.create(adminVO);
    
    if (cnt == 1) { // insert 레코드 개수
      mav.addObject("code", "create_success");
      mav.addObject("mname", adminVO.getMname());  // 관리자님(admin) 회원 가입을 축하합니다.
      mav.addObject("id", adminVO.getId());
    } else {
      mav.addObject("code", "create_fail");
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
    
    mav.addObject("url", "/admin/msg");  // /member/msg -> /member/msg.jsp
    
    mav.setViewName("redirect:/admin/msg.do"); // POST -> GET -> /member/msg.jsp

//    mav.addObject("code", "create_fail"); // 가입 실패 test용
//    mav.addObject("cnt", 0);                 // 가입 실패 test용
    
    return mav;
  }
  
  /**
   * 로그아웃 처리
   * @param session
   * @return
   */
  @RequestMapping(value="/admin/logout.do", method=RequestMethod.GET)
  public ModelAndView logout(HttpSession session){
    ModelAndView mav = new ModelAndView();
    session.invalidate(); // 모든 session 변수 삭제
    
    mav.setViewName("redirect:/index.do"); 
    
    return mav;
  }
  
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * @return
   */
  @RequestMapping(value="/admin/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
     
  /**
   * Cookie 로그인 폼
   * @return
   */
  // http://localhost:9091/admin/login.do 
  @RequestMapping(value = "/admin/login.do", 
                             method = RequestMethod.GET)
  public ModelAndView login_cookie(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView();
  
    mav.setViewName("/admin/login_form_ck"); // /admin/login_form_ck.jsp
    
    return mav;
  }
  
  /**
  * Cookie 기반 로그인 처리
  * @param request Cookie를 읽기위해 필요
  * @param response Cookie를 쓰기위해 필요
  * @param session 로그인 정보를 메모리에 기록
  * @param id  회원 아이디
  * @param passwd 회원 패스워드
  * @param id_save 회원 아이디 Cookie에 저장 여부
  * @param passwd_save 패스워드 Cookie에 저장 여부
  * @return
  */
  // http://localhost:9091/admin/login.do 
  @RequestMapping(value = "/admin/login.do", 
                            method = RequestMethod.POST)
  public ModelAndView login_cookie_proc(
                            HttpServletRequest request,
                            HttpServletResponse response,
                            HttpSession session,
                            AdminVO adminVO) {
    ModelAndView mav = new ModelAndView();
   
    int cnt = adminProc.login(adminVO);
    if (cnt == 1) { // 로그인 성공
      AdminVO adminVO_read = adminProc.read_by_id(adminVO.getId()); // DBMS에서 id를 이용한 회원 조회
      session.setAttribute("adminno", adminVO_read.getAdminno()); // 서버의 메모리에 기록
      session.setAttribute("admin_id", adminVO_read.getId());
      session.setAttribute("admin_mname", adminVO_read.getMname());
      session.setAttribute("admin_grade", adminVO_read.getGrade());
   
      String id_save = Tool.checkNull(adminVO.getId_save()); // 폼에 입력된 id 저장 여부
      String id = adminVO.getId();              // 폼에 입력된 id
      String passwd_save = Tool.checkNull(adminVO.getPasswd_save()); // 폼에 입력된 passwd 저장 여부
      String passwd = adminVO.getPasswd();              // 폼에 입력된 passwd 
      
      // -------------------------------------------------------------------
      // id 관련 쿠기 저장
      // -------------------------------------------------------------------
      if (id_save.equals("Y")) { // id를 저장할 경우, Checkbox를 체크한 경우
        Cookie ck_admin_id = new Cookie("ck_admin_id", id);
        ck_admin_id.setPath("/");  // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
        ck_admin_id.setMaxAge(60 * 60 * 24 * 30); // 30 day, 초단위
        response.addCookie(ck_admin_id); // id 저장
      } else { // N, id를 저장하지 않는 경우, Checkbox를 체크 해제한 경우
        Cookie ck_admin_id = new Cookie("ck_admin_id", "");
        ck_admin_id.setPath("/");
        ck_admin_id.setMaxAge(0);
        response.addCookie(ck_admin_id); // id 저장
      }
      
      // id를 저장할지 선택하는 CheckBox 체크 여부
      Cookie ck_admin_id_save = new Cookie("ck_admin_id_save", id_save);
      ck_admin_id_save.setPath("/");
      ck_admin_id_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
      response.addCookie(ck_admin_id_save);
      // -------------------------------------------------------------------
  
      // -------------------------------------------------------------------
      // Password 관련 쿠기 저장
      // -------------------------------------------------------------------
      if (passwd_save.equals("Y")) { // 패스워드 저장할 경우
        Cookie ck_admin_passwd = new Cookie("ck_admin_passwd", passwd);
        ck_admin_passwd.setPath("/");
        ck_admin_passwd.setMaxAge(60 * 60 * 24 * 30); // 30 day
        response.addCookie(ck_admin_passwd);
      } else { // N, 패스워드를 저장하지 않을 경우
        Cookie ck_admin_passwd = new Cookie("ck_admin_passwd", "");
        ck_admin_passwd.setPath("/");
        ck_admin_passwd.setMaxAge(0);
        response.addCookie(ck_admin_passwd);
      }
      // passwd를 저장할지 선택하는  CheckBox 체크 여부
      Cookie ck_admin_passwd_save = new Cookie("ck_admin_passwd_save", passwd_save);
      ck_admin_passwd_save.setPath("/");
      ck_admin_passwd_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
      response.addCookie(ck_admin_passwd_save);
      // -------------------------------------------------------------------
   
      mav.setViewName("redirect:/index.do");  
    } else {
      mav.addObject("url", "/admin/login_fail_msg");
      mav.setViewName("redirect:/admin/msg.do"); 
    }
       
    return mav;
  }
  
  // http://localhost:9091/admin/read.do?adminno=1
  @RequestMapping(value = "/admin/read.do", method = RequestMethod.GET)
  public String read(int adminno) {
    System.out.println("-> mname: " + this.adminProc.read(adminno).getMname());
    return "";
  
  }
  
}


