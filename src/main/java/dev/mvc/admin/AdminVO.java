package dev.mvc.admin;

public class AdminVO {
  /** 관리자번호 */
  private int adminno;
  /** 아이디 */
  private String id;
  /**패스워드*/
  private String passwd;
  /** 관리자 성명 */
  private String mname;
  /** 가입일 */
  private String mdate;
  /** 등급 */
  private int grade;
  
  
  /** 등록된 패스워드 */
  private String old_passwd = "";  
  /** id 저장 여부 */
  private String id_save;  
  /** 패스워드 저장 여부 */
  private String passwd_save;
  
  
  public int getAdminno() {
    return adminno;
  }
  public void setAdminno(int adminno) {
    this.adminno = adminno;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getPasswd() {
    return passwd;
  }
  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }
  public String getMname() {
    return mname;
  }
  public void setMname(String mname) {
    this.mname = mname;
  }
  public String getMdate() {
    return mdate;
  }
  public void setMdate(String mdate) {
    this.mdate = mdate;
  }
  public int getGrade() {
    return grade;
  }
  public void setGrade(int grade) {
    this.grade = grade;
  }
  public String getOld_passwd() {
    return old_passwd;
  }
  public void setOld_passwd(String old_passwd) {
    this.old_passwd = old_passwd;
  }
  public String getId_save() {
    return id_save;
  }
  public void setId_save(String id_save) {
    this.id_save = id_save;
  }
  public String getPasswd_save() {
    return passwd_save;
  }
  public void setPasswd_save(String passwd_save) {
    this.passwd_save = passwd_save;
  }
  
  
}
