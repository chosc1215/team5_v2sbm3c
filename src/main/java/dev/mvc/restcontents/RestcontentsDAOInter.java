package dev.mvc.restcontents;

import java.util.ArrayList;


public interface RestcontentsDAOInter {
  /**
   * 등록
   * @param restcontentsVO
   * @return
   */
  public int create(RestcontentsVO restcontentsVO);
  
  /**
   *  모든 카테고리의 등록된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<RestcontentsVO> list_all();

  /**
   *  특정 카테고리의 등록된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<RestcontentsVO> list_by_restcateno(int restcateno);
  
  /**
   * 조회
   * @param restcontentsno
   * @return
   */
  public RestcontentsVO read(int restcontentsno);
  
  /**
   * Map
   * @param restcontentsVO
   * @return
   */
  public int map(RestcontentsVO restcontentsVO);
  
  /**
   * 유튜브
   * @param restcontentsVO
   * @return
   */
  public int youtube(RestcontentsVO restcontentsVO);
  
  /**
   * 검색된 레코드 갯수 리턴
   * @param contentsVO
   * @return
   */
  public int search_count(RestcontentsVO restcontentsVO);
  
  /**
   *  특정 카테고리의 검색 + 페이징된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<RestcontentsVO> list_by_restcateno_search_paging(RestcontentsVO restcontentsVO);

  /**
   * 글 정보 수정
   * @param contentsVO
   * @return 변경된 레코드 갯수
   */
  public int update_text(RestcontentsVO restcontentsVO);
  
}
