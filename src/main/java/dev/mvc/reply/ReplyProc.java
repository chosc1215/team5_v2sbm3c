package dev.mvc.reply;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.restcontents.RestcontentsVO;
import dev.mvc.tool.Tool;

@Component("dev.mvc.reply.ReplyProc")
public class ReplyProc implements ReplyProcInter {
  @Autowired
  private ReplyDAOInter replyDAO; 
  
  @Override
  public int create(ReplyVO replyVO) {
    int count = replyDAO.create(replyVO);
    return count;
  }

  @Override
  public List<ReplyVO> list() {
    List<ReplyVO> list = replyDAO.list();
    return list;
  }

  @Override
  public List<ReplyVO> list_by_restcontentsno(int restcontentsno) {
    List<ReplyVO> list = replyDAO.list_by_restcontentsno(restcontentsno);
    String content = "";
    
    // 특수 문자 변경
    for (ReplyVO replyVO:list) {
      content = replyVO.getContent();
      content = Tool.convertChar(content);
      replyVO.setContent(content);
    }
    return list;
  }

  @Override
  public List<ReplyMemberVO> list_by_restcontentsno_join(int restcontentsno) {
    List<ReplyMemberVO> list = replyDAO.list_by_restcontentsno_join(restcontentsno);
    String content = "";
    
    // 특수 문자 변경
    for (ReplyMemberVO replyMemberVO:list) {
      content = replyMemberVO.getContent();
      content = Tool.convertChar(content);
      replyMemberVO.setContent(content);
    }
    return list;
  }

  @Override
  public int checkPasswd(Map<String, Object> map) {
    int count = replyDAO.checkPasswd(map);
    return count;
  }

  @Override
  public int delete(int replyno) {
    int count = replyDAO.delete(replyno);
    return count;
  }
  
  @Override
  public List<ReplyMemberVO> list_member_join() {
    List<ReplyMemberVO> list = replyDAO.list_member_join();
    
    // 특수 문자 변경
    for (ReplyMemberVO replyMemberVO:list) {
      String content = replyMemberVO.getContent();
      content = Tool.convertChar(content);
      replyMemberVO.setContent(content);
    }
    
    return list;
  }
  
  @Override
  public ReplyVO read(int replyno) {
    ReplyVO replyVO = this.replyDAO.read(replyno);
    return replyVO;
  }


  @Override
  public int update_reply(ReplyVO replyVO) {
    int cnt = this.replyDAO.update_reply(replyVO);
    return cnt;
  }

  @Override
  public int password_check(ReplyVO replyVO) {
    int cnt = this.replyDAO.password_check(replyVO);
    return cnt;
  }


  



  /*
   * @Override public List<ReplyMemberVO> lis_by_restcontentsno_join_add(int
   * restcontentsno) { List<ReplyMemberVO> list =
   * this.replyDAO.lis_by_restcontentsno_join_add(restcontentsno);
   * 
   * String content = "";
   * 
   * // 특수 문자 변경 for (ReplyMemberVO replyMemberVO:list) { content =
   * replyMemberVO.getContent(); content = Tool.convertChar(content);
   * replyMemberVO.setContent(content); }
   * 
   * return list; }
   */


   
}