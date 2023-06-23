package dev.mvc.review;

//REVIEWNO                          NUMBER(10)     NOT NULL    PRIMARY KEY,
//RESTCONTENTSNO                    NUMBER(20)     NOT NULL,
//MEMBERNO                          NUMBER(10)     NOT NULL,
//RECONTENT                         VARCHAR2(2000)     NOT NULL,
//RATING                            NUMBER(2)    NULL ,
//RDATE                             DATE     NOT NULL,
public class ReviewVO {
  /** 리뷰번호 */
  private int reviewno;
  /** 컨텐츠 번호 */
  private int restcontentsno;
  /** 회원 번호 */
  private int memberno;
  /** 리뷰 내용 */
  private String recontent = "";
  /** 평점 */
  private int rating;
  /** 등록일 */
  private String rdate = "";
  
  
  public int getReviewno() {
    return reviewno;
  }
  public void setReviewno(int reviewno) {
    this.reviewno = reviewno;
  }
  public int getRestcontentsno() {
    return restcontentsno;
  }
  public void setRestcontentsno(int restcontentsno) {
    this.restcontentsno = restcontentsno;
  }
  public int getMemberno() {
    return memberno;
  }
  public void setMemberno(int memberno) {
    this.memberno = memberno;
  }
  public String getRecontent() {
    return recontent;
  }
  public void setRecontent(String recontent) {
    this.recontent = recontent;
  }
  public int getRating() {
    return rating;
  }
  public void setRating(int rating) {
    this.rating = rating;
  }
  public String getRdate() {
    return rdate;
  }
  public void setRdate(String rdate) {
    this.rdate = rdate;
  }
  
  
}
