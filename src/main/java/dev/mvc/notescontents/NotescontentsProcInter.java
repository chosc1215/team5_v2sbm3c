package dev.mvc.notescontents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import dev.mvc.notescate.NotescateVO;

public interface NotescontentsProcInter {
  /**
   * 등록
   * spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @param notescontentsVO
   * @return
   */
  public int create(NotescontentsVO notescontentsVO);

  /**
   *  모든 카테고리의 등록된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<NotescontentsVO> list_all();

  /**
   *  특정 카테고리의 등록된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<NotescontentsVO> list_by_notescateno(int notescateno);
  
  /**
   * 조회
   * @param notescontentsno
   * @return
   */
  public NotescontentsVO read(int notescontentsno);
  
  /**
   * Map
   * @param notescontentsVO
   * @return
   */
  public int map(NotescontentsVO notescontentsVO);

  /**
   *  특정 카테고리의 검색된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<NotescontentsVO> list_by_notescateno_search(NotescontentsVO notescontentsVO);
  
  /**
   * 검색된 레코드 갯수 리턴
   * @param notescontentsVO
   * @return
   */
  public int search_count(NotescontentsVO notescontentsVO);

  /**
   *  특정 카테고리의 검색 + 페이징된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<NotescontentsVO> list_by_notescateno_search_paging(NotescontentsVO notescontentsVO);
  
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
  public String pagingBox(int notescateno, int now_page, String word, String list_file);
  
  /**
   * 글 정보 수정
   * @param notescontentsVO
   * @return 변경된 레코드 갯수
   */
  public int update_text(NotescontentsVO notescontentsVO);
 
  /**
   * 패스워드 검사  
   * @param notescontentsVO
   * @return 1: 패스워드 일치, 0: 패스워드 불일치
   */
  public int password_check(NotescontentsVO notescontentsVO);
  
  /**
   * 파일 정보 수정
   * @param notescontentsVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(NotescontentsVO notescontentsVO);
  
  /**
   * 삭제
   * @param notescontentsno
   * @return 삭제된 레코드 갯수
   */
  public int delete(int notescontentsno);
  
  /**
   * 카테고리별 레코드 갯수
   * @param notescateno
   * @return
   */
  public int count_by_notescateno(int notescateno);
  
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param notescateno
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_notescateno(int notescateno);
}
 


