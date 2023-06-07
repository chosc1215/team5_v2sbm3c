package dev.mvc.restcate;

import java.util.ArrayList;

public interface RestcateProcInter {
  /**
   * 등록
   * @param restcateVO 등록할 데이터
   * @return 등록된 레코드 갯수
   */
  public int create(RestcateVO restcateVO); // 추상 메소드
 
  /**
   *  전체 목록
   * @return
   */
  public ArrayList<RestcateVO> list_all();
  
  /**
   * 조회, 읽기
   * @param restcateno
   * @return
   */
  public RestcateVO read(int restcateno);
  
  /**
   * 수정
   * @param restcateVO
   * @return 수정된 레코드 갯수를 리턴
   */
  public int update(RestcateVO restcateVO);
  
  /**
   * 삭제
   * @param restcateno
   * @return 삭제된 레코드 갯수를 리턴
   */
  public int delete(int restcateno);
  
  /**
   * 출력 순서 하향(1등 -> 10등), seqno 컬럼의 값 증가
   * @param restcateno
   * @return 변경된 레코드 수
   */
  public int update_seqno_decrease(int restcateno);
  
  /**
   * 출력 순서 상향(10등 -> 1등), seqno 컬럼의 값 감소
   * @param restcateno
   * @return 변경된 레코드 수
   */
  public int update_seqno_increase(int restcateno);

  /**
   * 공개
   * @param restcateno
   * @return
   */
  public int update_visible_y(int restcateno);
  
  /**
   * 비공개
   * @param restcateno
   * @return
   */
  public int update_visible_n(int restcateno);
  
  /**
   * visible y만 출력
   * @return
   */
  public ArrayList<RestcateVO> list_all_y();

  /**
   * 글수 증가 
   * @param restcateno
   * @return
   */
  public int update_cnt_add(int restcateno);
  
  /**
   * 글수 감소
   * @param restcateno
   * @return
   */
  public int update_cnt_sub(int restcateno);
  
  
}


