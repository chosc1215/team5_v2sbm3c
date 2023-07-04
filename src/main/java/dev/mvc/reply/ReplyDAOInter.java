package dev.mvc.reply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ReplyDAOInter {
  public int create(ReplyVO replyVO);
  
  public List<ReplyVO> list();
  
  public List<ReplyVO> list_by_restcontentsno(int restcontentsno);
  
  public List<ReplyMemberVO> list_by_restcontentsno_join(int restcontentsno);
  
  public int checkPasswd(Map<String, Object> map);
  
  public List<ReplyMemberVO> list_member_join();

  public int delete(int replyno);
  
  /**
   * 특정글 관련 전체 댓글 목록
   * @param contentsno
   * @return
   */
//  public List<ReplyMemberVO>lis_by_restcontentsno_join_add(int restcontentsno);
  
  public ReplyVO read(int replyno);
  
  public int password_check(ReplyVO replyVO);
  
  public int update_reply(ReplyVO replyVO);
  
  

  

}