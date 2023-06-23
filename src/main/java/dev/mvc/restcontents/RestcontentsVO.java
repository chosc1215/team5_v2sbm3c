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
  /** 평점 */
  private int rating;
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

  public int getRestcontentsno() {
    return restcontentsno;
  }

  public void setRestcontentsno(int restcontentsno) {
    this.restcontentsno = restcontentsno;
  }

  public int getAdminno() {
    return adminno;
  }

  public void setAdminno(int adminno) {
    this.adminno = adminno;
  }

  public int getRestcateno() {
    return restcateno;
  }

  public void setRestcateno(int restcateno) {
    this.restcateno = restcateno;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getRecom() {
    return recom;
  }

  public void setRecom(int recom) {
    this.recom = recom;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public int getCnt() {
    return cnt;
  }

  public void setCnt(int cnt) {
    this.cnt = cnt;
  }

  public int getReplycnt() {
    return replycnt;
  }

  public void setReplycnt(int replycnt) {
    this.replycnt = replycnt;
  }

  public String getPasswd() {
    return passwd;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public String getRdate() {
    return rdate;
  }

  public void setRdate(String rdate) {
    this.rdate = rdate;
  }

  public String getFile1() {
    return file1;
  }

  public void setFile1(String file1) {
    this.file1 = file1;
  }

  public String getFile1saved() {
    return file1saved;
  }

  public void setFile1saved(String file1saved) {
    this.file1saved = file1saved;
  }

  public String getThumb1() {
    return thumb1;
  }

  public void setThumb1(String thumb1) {
    this.thumb1 = thumb1;
  }

  public long getSize1() {
    return size1;
  }

  public void setSize1(long size1) {
    this.size1 = size1;
  }

  public String getMap() {
    return map;
  }

  public void setMap(String map) {
    this.map = map;
  }

  public String getYoutube() {
    return youtube;
  }

  public void setYoutube(String youtube) {
    this.youtube = youtube;
  }

  public MultipartFile getFile1MF() {
    return file1MF;
  }

  public void setFile1MF(MultipartFile file1mf) {
    file1MF = file1mf;
  }

  public String getSize1_label() {
    return size1_label;
  }

  public void setSize1_label(String size1_label) {
    this.size1_label = size1_label;
  }

  public int getStart_num() {
    return start_num;
  }

  public void setStart_num(int start_num) {
    this.start_num = start_num;
  }

  public int getEnd_num() {
    return end_num;
  }

  public void setEnd_num(int end_num) {
    this.end_num = end_num;
  }

  public int getNow_page() {
    return now_page;
  }

  public void setNow_page(int now_page) {
    this.now_page = now_page;
  }
  
  

}
