package dev.mvc.notescate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.notescate.NotescateProc") // Controller가 사용하는 이름
public class NotescateProc implements NotescateProcInter {
  @Autowired
  private NotescateDAOInter notescateDAO; // Spring이 NotescateDAOInter.java를 구현하여 객체를 자동 생성후 할당
  
  @Override
  public int create(NotescateVO notescateVO) {
    int cnt = this.notescateDAO.create(notescateVO); // Spring이 자동으로 구현한 메소드를 호출
    return cnt;
  }

  @Override
  public ArrayList<NotescateVO> list_all() {
    ArrayList<NotescateVO> list = this.notescateDAO.list_all();
    
    return list;
  }

  @Override
  public NotescateVO read(int notescateno) {
    NotescateVO notescateVO = this.notescateDAO.read(notescateno);
    
    return notescateVO;
  }

  @Override
  public int update(NotescateVO notescateVO) {
    int cnt = this.notescateDAO.update(notescateVO);
    
    return cnt;
  }

  @Override
  public int delete(int notescateno) {
    int cnt = this.notescateDAO.delete(notescateno);
    return cnt;
  }

  @Override
  public int update_seqno_decrease(int notescateno) {
    int cnt = this.notescateDAO.update_seqno_decrease(notescateno);
    return cnt;
  }

  @Override
  public int update_seqno_increase(int notescateno) {
    int cnt = this.notescateDAO.update_seqno_increase(notescateno);
    return cnt;
  }
  
  @Override
  public int update_visible_y(int notescateno) {
    int cnt = this.notescateDAO.update_visible_y(notescateno);
    return cnt;
  }
  
  @Override
  public int update_visible_n(int notescateno) {
    int cnt = this.notescateDAO.update_visible_n(notescateno);
    return cnt;
  }

  @Override
  public ArrayList<NotescateVO> list_all_y() {
    ArrayList<NotescateVO> list = this.notescateDAO.list_all_y();
    return list;
  }
  
  @Override
  public int update_cnt_add(int notescateno) {
    int cnt = this.notescateDAO.update_cnt_add(notescateno);
    return cnt;
  }
  
  @Override
  public int update_cnt_sub(int notescateno) {
    int cnt = this.notescateDAO.update_cnt_sub(notescateno);
    return cnt;
  }
  
}
