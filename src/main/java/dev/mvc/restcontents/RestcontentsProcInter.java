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
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param cateno          카테고리번호 
   * @param now_page      현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명 
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int restcateno, int now_page, String word, String list_file);

  /**
   * 패스워드 검사  
   * @param contentsVO
   * @return 1: 패스워드 일치, 0: 패스워드 불일치
   */
  public int password_check(RestcontentsVO restcontentsVO);  
  
  /**
   * 글 정보 수정
   * @param contentsVO
   * @return 변경된 레코드 갯수
   */
  public int update_text(RestcontentsVO restcontentsVO);
  
  /**
   * 파일 정보 수정
   * @param contentsVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(RestcontentsVO restcontentsVO);
  
  /**
   * 삭제
   * @param contentsno
   * @return 삭제된 레코드 갯수
   */
  public int delete(int restcontentsno);
  
  /**
   * 카테고리별 레코드 갯수
   * @param restcateno
   * @return
   */
  public int count_by_restcateno(int restcateno);
  
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param restcateno
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_restcateno(int restcateno);    
  
  /**
   * 글 수 증가
   * @param 
   * @return
   */ 
  public int increaseCommentscnt(int restcontentsno);
 
  /**
   * 글 수 감소
   * @param 
   * @return
   */   
  public int decreaseCommentscnt(int restcontentsno);
  
  /**
   * 특정 카테고리의 등록된 추천 목록 5건
   * @param restcateno
   * @return
   */
  public ArrayList<RestcontentsVO> recommend_rdate(int restcateno);
  
}
