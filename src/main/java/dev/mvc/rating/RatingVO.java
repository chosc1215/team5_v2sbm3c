package dev.mvc.rating;


//RATINGNO                          NUMBER(10)     NOT NULL    PRIMARY KEY,
//RESTCONTENTSNO                    NUMBER(20)     NOT NULL,
//MEMBERNO                          NUMBER(10)     NOT NULL,
//SCORE                             NUMBER(2)    DEFAULT 1     NOT NULL,
//RDATE                             DATE     NOT NULL,
public class RatingVO {
  /** 평점 번호 */
  private int ratingno;
  /** 맛집 컨텐츠 번호 */
  private int restcontentsno;
  /** 회원 번호 */
  private int memberno;
  /** 점수 */
  private int score;
  /** 등록일 */
  private String rdate;
  
  
  public int getRatingno() {
    return ratingno;
  }
  public void setRatingno(int ratingno) {
    this.ratingno = ratingno;
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
  public int getScore() {
    return score;
  }
  public void setScore(int score) {
    this.score = score;
  }
  public String getRdate() {
    return rdate;
  }
  public void setRdate(String rdate) {
    this.rdate = rdate;
  }
  
  

}
