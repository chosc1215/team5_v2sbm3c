package dev.mvc.restcontents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.restcontents.RestcontentsProc")
public class RestcontentsProc implements RestcontentsProcInter {
  @Autowired
  private RestcontentsDAOInter restcontentsDAO;

  @Override
  public int create(RestcontentsVO restcontentsVO) {
    int cnt = this.restcontentsDAO.create(restcontentsVO);
    return cnt;
  }

}
