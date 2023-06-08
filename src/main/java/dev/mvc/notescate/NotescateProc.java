package dev.mvc.notescate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.notes.NotesProc") // Controller가 사용하는 이름
public class NotescateProc implements NotescateProcInter {
  @Autowired
  private NotescateDAOInter notesDAO; // Spring이 NotesDAOInter.java를 구현하여 객체를 자동 생성후 할당
  
  @Override
  public int create(NotescateVO notesVO) {
    int cnt = this.notesDAO.create(notesVO); // Spring이 자동으로 구현한 메소드를 호출
    return cnt;
  }

  @Override
  public ArrayList<NotescateVO> list_all() {
    ArrayList<NotescateVO> list = this.notesDAO.list_all();
    
    return list;
  }

  @Override
  public NotescateVO read(int notesno) {
    NotescateVO notesVO = this.notesDAO.read(notesno);
    
    return notesVO;
  }

  @Override
  public int update(NotescateVO notesVO) {
    int cnt = this.notesDAO.update(notesVO);
    
    return cnt;
  }

  @Override
  public int delete(int notesno) {
    int cnt = this.notesDAO.delete(notesno);
    return cnt;
  }

  @Override
  public int update_seqno_decrease(int notesno) {
    int cnt = this.notesDAO.update_seqno_decrease(notesno);
    return cnt;
  }

  @Override
  public int update_seqno_increase(int notesno) {
    int cnt = this.notesDAO.update_seqno_increase(notesno);
    return cnt;
  }
  
  @Override
  public int update_visible_y(int notesno) {
    int cnt = this.notesDAO.update_visible_y(notesno);
    return cnt;
  }
  
  @Override
  public int update_visible_n(int notesno) {
    int cnt = this.notesDAO.update_visible_n(notesno);
    return cnt;
  }

  @Override
  public ArrayList<NotescateVO> list_all_y() {
    ArrayList<NotescateVO> list = this.notesDAO.list_all_y();
    return list;
  }
  
  @Override
  public int update_cnt_add(int notesno) {
    int cnt = this.notesDAO.update_cnt_add(notesno);
    return cnt;
  }
  
  @Override
  public int update_cnt_sub(int notesno) {
    int cnt = this.notesDAO.update_cnt_sub(notesno);
    return cnt;
  }
  
}
