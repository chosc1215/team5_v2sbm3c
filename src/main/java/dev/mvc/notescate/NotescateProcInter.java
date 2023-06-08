package dev.mvc.notescate;

import java.util.ArrayList;

public interface NotescateProcInter {
  /**
   * 등록
   * @param notesVO 등록할 데이터
   * @return 등록된 레코드 갯수
   */
  public int create(NotescateVO notesVO); // 추상 메소드
 
  /**
   *  전체 목록
   * @return
   */
  public ArrayList<NotescateVO> list_all();
  
  /**
   * 조회, 읽기
   * @param notesno
   * @return
   */
  public NotescateVO read(int notesno);
  
  /**
   * 수정
   * @param notesVO
   * @return 수정된 레코드 갯수를 리턴
   */
  public int update(NotescateVO notesVO);
  
  /**
   * 삭제
   * @param notesno
   * @return 삭제된 레코드 갯수를 리턴
   */
  public int delete(int notesno);
  
  /**
   * 출력 순서 하향(1등 -> 10등), seqno 컬럼의 값 증가
   * @param notesno
   * @return 변경된 레코드 수
   */
  public int update_seqno_decrease(int notesno);
  
  /**
   * 출력 순서 상향(10등 -> 1등), seqno 컬럼의 값 감소
   * @param notesno
   * @return 변경된 레코드 수
   */
  public int update_seqno_increase(int notesno);

  /**
   * 공개
   * @param notesno
   * @return
   */
  public int update_visible_y(int notesno);
  
  /**
   * 비공개
   * @param notesno
   * @return
   */
  public int update_visible_n(int notesno);
  
  /**
   * visible y만 출력
   * @return
   */
  public ArrayList<NotescateVO> list_all_y();

  /**
   * 글수 증가 
   * @param notesno
   * @return
   */
  public int update_cnt_add(int notesno);
  
  /**
   * 글수 감소
   * @param notesno
   * @return
   */
  public int update_cnt_sub(int notesno);
  
  
}


