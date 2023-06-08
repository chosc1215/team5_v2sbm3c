package dev.mvc.notescate;

import java.util.ArrayList;

public interface NotescateProcInter {
  /**
   * 등록
   * @param notescateVO 등록할 데이터
   * @return 등록된 레코드 갯수
   */
  public int create(NotescateVO notescateVO); // 추상 메소드
 
  /**
   *  전체 목록
   * @return
   */
  public ArrayList<NotescateVO> list_all();
  
  /**
   * 조회, 읽기
   * @param notescateno
   * @return
   */
  public NotescateVO read(int notescateno);
  
  /**
   * 수정
   * @param notescateVO
   * @return 수정된 레코드 갯수를 리턴
   */
  public int update(NotescateVO notescateVO);
  
  /**
   * 삭제
   * @param notescateno
   * @return 삭제된 레코드 갯수를 리턴
   */
  public int delete(int notescateno);
  
  /**
   * 출력 순서 하향(1등 -> 10등), seqno 컬럼의 값 증가
   * @param notescateno
   * @return 변경된 레코드 수
   */
  public int update_seqno_decrease(int notescateno);
  
  /**
   * 출력 순서 상향(10등 -> 1등), seqno 컬럼의 값 감소
   * @param notescateno
   * @return 변경된 레코드 수
   */
  public int update_seqno_increase(int notescateno);

  /**
   * 공개
   * @param notescateno
   * @return
   */
  public int update_visible_y(int notescateno);
  
  /**
   * 비공개
   * @param notescateno
   * @return
   */
  public int update_visible_n(int notescateno);
  
  /**
   * visible y만 출력
   * @return
   */
  public ArrayList<NotescateVO> list_all_y();

  /**
   * 글수 증가 
   * @param notescateno
   * @return
   */
  public int update_cnt_add(int notescateno);
  
  /**
   * 글수 감소
   * @param notescateno
   * @return
   */
  public int update_cnt_sub(int notescateno);
  
  
}


