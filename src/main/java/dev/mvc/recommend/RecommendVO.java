package dev.mvc.recommend;

//RECOMMENDNO                       NUMBER(10)     NOT NULL    PRIMARY KEY,
//SEQ                               NUMBER(2)    DEFAULT 1     NOT NULL,
//RDATE                             DATE     NOT NULL,
//RESTCATENO                        NUMBER(10)     NULL ,
//MEMBERNO                          NUMBER(10)     NULL ,
public class RecommendVO {
  private int recommendno;
  
  private int seq;
  
  private String rdate;
  
  private int restcateno;
  
  private int memberno;

  public int getRecommendno() {
    return recommendno;
  }

  public void setRecommendno(int recommendno) {
    this.recommendno = recommendno;
  }

  public int getSeq() {
    return seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  public String getRdate() {
    return rdate;
  }

  public void setRdate(String rdate) {
    this.rdate = rdate;
  }

  public int getRestcateno() {
    return restcateno;
  }

  public void setRestcateno(int restcateno) {
    this.restcateno = restcateno;
  }

  public int getMemberno() {
    return memberno;
  }

  public void setMemberno(int memberno) {
    this.memberno = memberno;
  }
  
  

}
