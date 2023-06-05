package dev.mvc.restcontents;

import org.springframework.web.multipart.MultipartFile;

//RESTCONTENTSNO                    NUMBER(20)     NOT NULL    PRIMARY KEY,
//ADMINNO                           NUMBER(10)     NOT NULL,
//RESTCATENO                        NUMBER(10)     NOT NULL,
//TITLE                             VARCHAR2(200)    NOT NULL,
//CONTENT                           CLOB       NOT NULL,
//RECOM                             NUMBER(7)    NOT NULL,
//CNT                               NUMBER(7)    NOT NULL,
//REPLYCNT                          NUMBER(7)    NOT NULL,
//PASSWD                            VARCHAR2(15)     NOT NULL,
//WORD                              VARCHAR2(100)    NULL ,
//RDATE                             DATE     NOT NULL,
//FILE1                             VARCHAR2(100)    NULL ,
//FILE1SAVED                        VARCHAR2(100)    NULL ,
//THUMB1                            VARCHAR2(100)    NULL ,
//SIZE1                             NUMBER(10)     NULL ,
//MAP                               VARCHAR2(1000)     NULL ,
//YOUTUBE                           VARCHAR2(1000)     NULL ,

public class RestcontentsVO {
  /** 컨텐츠 번호 */
  private int restcontentsno;
  /** 관리자 번호 */
  private int adminno;
  /** 카테고리 번호 */
  private int restcateno;
  /** 제목 */
  private String title = "";
  /** 내용 */
  private String content = "";
  /** 추천수 */
  private int recom;
  /** 조회수 */
  private int cnt = 0;
  /** 댓글수 */
  private int replycnt = 0;
  /** 패스워드 */
  private String passwd = "";
  /** 검색어 */
  private String word = "";
  /** 등록 날짜 */
  private String rdate = "";

  /** 메인 이미지 */
  private String file1 = "";
  /** 실제 저장된 메인 이미지 */
  private String file1saved = "";
  /** 메인 이미지 preview */
  private String thumb1 = "";
  /** 메인 이미지 크기 */
  private long size1;

  
  /** 지도 */
  private String map;
  
  /** Youtube */
  private String youtube;
  
  /**
   이미지 파일
   <input type='file' class="form-control" name='file1MF' id='file1MF' 
              value='' placeholder="파일 선택">
   */
  private MultipartFile file1MF;
  
  /** 메인 이미지 크기 단위, 파일 크기 */
  private String size1_label = "";
  
  /** 시작 rownum */
  private int start_num;
  
  /** 종료 rownum */
  private int end_num;    
  
  /** 현재 페이지 */
  private int now_page = 1;
  

}
