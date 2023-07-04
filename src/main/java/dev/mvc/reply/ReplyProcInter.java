package dev.mvc.reply;

import java.util.List;
import java.util.Map;

public interface ReplyProcInter {
  public int create(ReplyVO replyVO);
  
  public List<ReplyVO> list();
  
  public List<ReplyVO> list_by_restcontentsno(int restcontentsno);
  
  public List<ReplyMemberVO> list_by_restcontentsno_join(int restcontentsno);
  
  public int checkPasswd(Map<String, Object> map);

  public int delete(int replyno);
  
  public List<ReplyMemberVO> list_member_join();
  
  public ReplyVO read(int replyno);
  
  public int password_check(ReplyVO replyVO);
  
  public int update_reply(ReplyVO replyVO);


  
}