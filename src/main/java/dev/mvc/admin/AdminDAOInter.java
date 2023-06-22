package dev.mvc.admin;

import java.util.ArrayList;

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
  
}




