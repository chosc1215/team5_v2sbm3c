package dev.mvc.restcontents;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.restcontents.RestcontentsVO;
import dev.mvc.tool.Tool;

@Component("dev.mvc.restcontents.RestcontentsProc")
public class RestcontentsProc implements RestcontentsProcInter {
  @Autowired
  private RestcontentsDAOInter restcontentsDAO;

  @Override
  public int create(RestcontentsVO restcontentsVO) {
    int cnt = this.restcontentsDAO.create(restcontentsVO);
    return cnt;
  }

  @Override
  public ArrayList<RestcontentsVO> list_all() {
   ArrayList<RestcontentsVO> list = this.restcontentsDAO.list_all();
   
   // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
   for (RestcontentsVO restcontentsVO : list) {
     String title = restcontentsVO.getTitle();
     String content = restcontentsVO.getContent();
     
     title = Tool.convertChar(title);  // 특수 문자 처리
     content = Tool.convertChar(content); 
     
     restcontentsVO.setTitle(title);
     restcontentsVO.setContent(content);  

   }   
    return list;
  }

  @Override
  public ArrayList<RestcontentsVO> list_by_restcateno(int restcateno) {
    ArrayList<RestcontentsVO> list = this.restcontentsDAO.list_by_restcateno(restcateno);
    
    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (RestcontentsVO restcontentsVO : list) {
      String title = restcontentsVO.getTitle();
      String content = restcontentsVO.getContent();
      
      title = Tool.convertChar(title);  // 특수 문자 처리
      content = Tool.convertChar(content); 
      
      restcontentsVO.setTitle(title);
      restcontentsVO.setContent(content);  

    }     
    return list;
  }

}
