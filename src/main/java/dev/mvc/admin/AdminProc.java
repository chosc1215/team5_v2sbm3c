package dev.mvc.admin;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.admin.AdminProc")
public class AdminProc implements AdminProcInter {
  @Autowired
  private AdminDAOInter adminDAO;
  
  @Override
  public int checkid(String id) {
    int cnt = this.adminDAO.checkid(id);
    return cnt;
  }
  
  @Override
  public int create(AdminVO adminVO) {
    int cnt = this.adminDAO.create(adminVO);
    return cnt;
  }
  
  @Override
  public int login(AdminVO adminVO) {
    int cnt = this.adminDAO.login(adminVO);
    return cnt;
  }

  @Override
  public AdminVO read_by_id(String id) {
    AdminVO adminVO = this.adminDAO.read_by_id(id);
    return adminVO;
  }

  @Override
  public boolean isAdmin(HttpSession session){
    boolean sw = false;
    
    String id_admin = (String)session.getAttribute("id_admin");
    
    if (id_admin != null){
      sw = true;
    }
    return sw;
  }
  
  
  
  

  @Override
  public AdminVO read(int adminno) {
    AdminVO adminVO = this.adminDAO.read(adminno);
    return adminVO;
  }

  @Override
  public ArrayList<AdminVO> list() {
    ArrayList<AdminVO> list = this.adminDAO.list();
    return list;
  }

  
  
  
}



