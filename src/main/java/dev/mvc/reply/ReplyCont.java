package dev.mvc.reply;

import java.util.List;

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
  
}