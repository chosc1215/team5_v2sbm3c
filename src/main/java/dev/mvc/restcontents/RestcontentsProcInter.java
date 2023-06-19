package dev.mvc.restcontents;

import java.util.ArrayList;

public interface RestcontentsProcInter {
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
  
}
