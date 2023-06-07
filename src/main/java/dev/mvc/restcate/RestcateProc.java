package dev.mvc.restcate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.restcate.RestcateProc") // Controller가 사용하는 이름
public class RestcateProc implements RestcateProcInter {
  @Autowired
  private RestcateDAOInter restcateDAO; // Spring이 RestcateDAOInter.java를 구현하여 객체를 자동 생성후 할당
  
  @Override
  public int create(RestcateVO restcateVO) {
    int cnt = this.restcateDAO.create(restcateVO); // Spring이 자동으로 구현한 메소드를 호출
    return cnt;
  }

  @Override
  public ArrayList<RestcateVO> list_all() {
    ArrayList<RestcateVO> list = this.restcateDAO.list_all();
    
    return list;
  }

  @Override
  public RestcateVO read(int restcateno) {
    RestcateVO restcateVO = this.restcateDAO.read(restcateno);
    
    return restcateVO;
  }

  @Override
  public int update(RestcateVO restcateVO) {
    int cnt = this.restcateDAO.update(restcateVO);
    
    return cnt;
  }

  @Override
  public int delete(int restcateno) {
    int cnt = this.restcateDAO.delete(restcateno);
    return cnt;
  }

  @Override
  public int update_seqno_decrease(int restcateno) {
    int cnt = this.restcateDAO.update_seqno_decrease(restcateno);
    return cnt;
  }

  @Override
  public int update_seqno_increase(int restcateno) {
    int cnt = this.restcateDAO.update_seqno_increase(restcateno);
    return cnt;
  }
  
  @Override
  public int update_visible_y(int restcateno) {
    int cnt = this.restcateDAO.update_visible_y(restcateno);
    return cnt;
  }
  
  @Override
  public int update_visible_n(int restcateno) {
    int cnt = this.restcateDAO.update_visible_n(restcateno);
    return cnt;
  }

  @Override
  public ArrayList<RestcateVO> list_all_y() {
    ArrayList<RestcateVO> list = this.restcateDAO.list_all_y();
    return list;
  }
  
  @Override
  public int update_cnt_add(int restcateno) {
    int cnt = this.restcateDAO.update_cnt_add(restcateno);
    return cnt;
  }
  
  @Override
  public int update_cnt_sub(int restcateno) {
    int cnt = this.restcateDAO.update_cnt_sub(restcateno);
    return cnt;
  }
  
}





