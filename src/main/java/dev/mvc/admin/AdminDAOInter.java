package dev.mvc.admin;

import java.util.ArrayList;
import java.util.HashMap;

import dev.mvc.member.MemberVO;

public interface AdminDAOInter {
  
  /**
   * 중복 아이디 검사
   * @param id
   * @return 중복 아이디 갯수
   */
  public int checkid(String id);
  
  /**
   * 관리자 회원 가입
   * @param adminVO
   * @return
   */
  public int create(AdminVO adminVO);
  
  /**
   * 관리자 전체 목록
   * @return
   */
  public ArrayList<AdminVO> list();
  
  /**
   * 로그인
   * @param AdminVO
   * @return
   */
  public int login(AdminVO adminVO);
  
  /**
   * id를 통한 회원 정보
   * @param String
   * @return
   */
  public AdminVO read_by_id(String id);
  
  
  /**
   * adminno를 통한 회원 정보
   * @param adminno 회원 번호
   * @return
   */
  public AdminVO read(int adminno);
  
  /**
   * 관리자 조회
   * @param adminno
   * @return
   */
  public AdminVO admin_read(int adminno);
  
  /**
   * 관리자 수정
   * @param adminVO
   * @return
   */
  public int update (AdminVO adminVO);
  
  /**
   * 현재 패스워드 체크
   * @param map
   * @return
   */
  public int passwd_check(HashMap<Object, Object> map);
  
  /**
   * 패스워드 변경
   * @param map
   * @return
   */
  public int passwd_update(HashMap<Object, Object> map);

  /**
   * 관리자 탈퇴
   * @param adminVO
   * @return
   */
  public int admin_out(AdminVO adminVO);
  
}




