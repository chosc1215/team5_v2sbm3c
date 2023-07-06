package dev.mvc.recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.recommend.RecommendProc")
public class RecommendProc implements RecommendProcInter {
  @Autowired
  private RecommendDAOInter recommendDAO;

  /**
   * 조회
   */
  @Override
  public RecommendVO recommend_read(int memberno) {
    RecommendVO recommendVO = this.recommendDAO.recommend_read(memberno);
    
    return recommendVO;
  }

}
