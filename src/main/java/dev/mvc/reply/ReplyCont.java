package dev.mvc.reply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.restcate.RestcateProcInter;
import dev.mvc.restcontents.RestcontentsProcInter;
import dev.mvc.restcontents.RestcontentsVO;
import dev.mvc.tool.Tool;
import dev.mvc.admin.AdminProc;
import dev.mvc.admin.AdminProcInter;
import dev.mvc.member.MemberProc;
import dev.mvc.member.MemberVO;

@Controller
public class ReplyCont {
  @Autowired
  @Qualifier("dev.mvc.reply.ReplyProc") // 이름 지정
  private ReplyProcInter replyProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // 이름 지정
  private MemberProc memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") 
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.restcontents.RestcontentsProc") 
  private RestcontentsProcInter restcontentsProc;
  
  public ReplyCont(){
    System.out.println("-> ReplyCont created.");
  }
  
  @ResponseBody
  @RequestMapping(value = "/reply/create.do",
                            method = RequestMethod.POST,
                            produces = "text/plain;charset=UTF-8")
  public String create(ReplyVO replyVO) {
    int cnt = replyProc.create(replyVO);
    
    JSONObject obj = new JSONObject();
    obj.put("cnt",cnt);
 
    return obj.toString(); // {"cnt":1}

  }
  
  @RequestMapping(value="/reply/list.do", method=RequestMethod.GET)
  public ModelAndView list(HttpSession session) {
    ModelAndView mav = new ModelAndView();
       
    
    if (adminProc.isAdmin(session)) {
      List<ReplyVO> list = replyProc.list();
      
      mav.addObject("list", list);
      mav.setViewName("/reply/list"); // /webapp/reply/list.jsp

    } else {
      mav.addObject("return_url", "/reply/list.do"); // 로그인 후 이동할 주소 ★
      
      mav.setViewName("redirect:/member/login.do"); // /WEB-INF/views/member/login_ck_form.jsp
    }
    
    return mav;
  }

  /**
   <xmp>
   http://localhost:9090/ojt/reply/list_by_restcontentsno.do?restcontentsno=1
   글이 없는 경우: {"list":[]}
   글이 있는 경우
   {"list":[
            {"memberno":1,"rdate":"2019-12-18 16:46:43","passwd":"123","replyno":3,"content":"댓글 3","restcontentsno":1}
            ,
            {"memberno":1,"rdate":"2019-12-18 16:46:39","passwd":"123","replyno":2,"content":"댓글 2","restcontentsno":1}
            ,
            {"memberno":1,"rdate":"2019-12-18 16:46:35","passwd":"123","replyno":1,"content":"댓글 1","restcontentsno":1}
            ] 
   }
   </xmp>  
   * @param restcontentsno
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/reply/list_by_restcontentsno.do",
                            method = RequestMethod.GET,
                            produces = "text/plain;charset=UTF-8")
  public String list_by_restcontentsno(int restcontentsno) {
    List<ReplyVO> list = replyProc.list_by_restcontentsno(restcontentsno);
    
    JSONObject obj = new JSONObject();
    obj.put("list", list);
 
    return obj.toString(); 

  }
  
  /**
   * {"list":[
          {"memberno":1,
        "rdate":"2019-12-18 16:46:35",
      "passwd":"123",
      "replyno":1,
      "id":"user1",
      "content":"댓글 1",
      "restcontentsno":1}
    ,
        {"memberno":1,
       "rdate":"2019-12-18 16:46:35",
       "passwd":"123",
       "replyno":1,
       "id":"user1",
       "content":"댓글 1",
       "restcontentsno":1}
    ]
}
   * http://localhost:9090/ojt/reply/list_by_restcontentsno_join.do?restcontentsno=1
   * @param restcontentsno
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/reply/list_by_restcontentsno_join.do",
                              method = RequestMethod.GET,
                              produces = "text/plain;charset=UTF-8")
  public String list_by_restcontentsno_join(int restcontentsno) {
    // String msg="JSON 출력";
    // return msg;
    
    List<ReplyMemberVO> list = replyProc.list_by_restcontentsno_join(restcontentsno);
    
    JSONObject obj = new JSONObject();
    obj.put("list", list);
 
    return obj.toString();     
  }
  
  /**
   * 패스워드를 검사한 후 삭제 
   * http://localhost:9090/resort/reply/delete.do?replyno=1&passwd=1234
   * {"delete_cnt":0,"passwd_cnt":0}
   * {"delete_cnt":1,"passwd_cnt":1}
   * @param replyno
   * @param passwd
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/reply/delete.do", 
                              method = RequestMethod.POST,
                              produces = "text/plain;charset=UTF-8")
  public String delete(int replyno, String passwd) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("replyno", replyno);
    map.put("passwd", passwd);
    
    int passwd_cnt = replyProc.checkPasswd(map); // 패스워드 일치 여부, 1: 일치, 0: 불일치
    int delete_cnt = 0;                                    // 삭제된 댓글
    if (passwd_cnt == 1) { // 패스워드가 일치할 경우
      delete_cnt = replyProc.delete(replyno); // 댓글 삭제
    }
    
    JSONObject obj = new JSONObject();
    obj.put("passwd_cnt", passwd_cnt); // 패스워드 일치 여부, 1: 일치, 0: 불일치
    obj.put("delete_cnt", delete_cnt); // 삭제된 댓글
    
    return obj.toString();
  }
  
  /* 시도 */
  /**
   * 수정 폼
   * http://localhost:9093/reply/update_reply.do?replyno=1
   * 
   * @return
   */
  @RequestMapping(value = "/reply/update_reply.do", method = RequestMethod.GET)
  public ModelAndView update_reply(int replyno) {
    ModelAndView mav = new ModelAndView();
    
    ReplyVO replyVO = this.replyProc.read(replyno);
    mav.addObject("replyVO", replyVO);
    
    RestcontentsVO restcontentsVO = this.restcontentsProc.read(replyVO.getRestcontentsno());
    mav.addObject("restcontentsVO", restcontentsVO);
    
    mav.setViewName("/reply/update_reply"); // /WEB-INF/views/contents/update_reply.jsp
    // String content = "장소:\n인원:\n준비물:\n비용:\n기타:\n";
    // mav.addObject("content", content);

    return mav; // forward
  }
  
  /**
   * 수정 처리
   * http://localhost:9091/contents/update_reply.do?contentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/reply/update_reply.do", method = RequestMethod.POST)
  public ModelAndView update_reply(HttpSession session, ReplyVO replyVO) {
    ModelAndView mav = new ModelAndView();
    
    // System.out.println("-> word: " + contentsVO.getWord());
    
    if (this.adminProc.isAdmin(session)) { // 관리자 로그인
      this.replyProc.update_reply(replyVO);  
      
      mav.addObject("replyno", replyVO.getReplyno());
      mav.addObject("restcontentsno", replyVO.getRestcontentsno());
      mav.setViewName("redirect:/reply/read.do");
    } else { // 정상적인 로그인이 아닌 경우
      if (this.replyProc.password_check(replyVO) == 1) {
        this.replyProc.update_reply(replyVO);  
         
        // mav 객체 이용
        mav.addObject("replyno", replyVO.getReplyno());
        mav.addObject("restcontentsno", replyVO.getRestcontentsno());
        mav.setViewName("redirect:/reply/read.do");
      } else {
        mav.addObject("url", "/reply/passwd_check"); // /WEB-INF/views/contents/passwd_check.jsp
        mav.setViewName("redirect:/reply/msg.do");  // POST -> GET -> JSP 출력
      }    
    }
    
    
    
    // URL에 파라미터의 전송
    // mav.setViewName("redirect:/contents/read.do?contentsno=" + contentsVO.getReplyno() + "&cateno=" + contentsVO.getrestcontentsno());             
    
    return mav; // forward
  }
  
  /**
   * replyno, passwd를 GET 방식으로 전달받아 패스워드 일치 검사를하고 결과 1또는 0을 Console에 출력하세요.
   * http://localhost:9093/reply/password_check.do?replyno=2&passwd=123
   * @return
   */
  @RequestMapping(value="/reply/password_check.do", method=RequestMethod.GET )
  public ModelAndView password_check(ReplyVO replyVO) {
    ModelAndView mav = new ModelAndView();

    int cnt = this.replyProc.password_check(replyVO);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 0) {
      mav.setViewName("/reply/passwd_check"); // /WEB-INF/views/reply/passwd_check.jsp
    }
        
    return mav;
  }    
  
 
}
