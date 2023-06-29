package dev.mvc.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.rating.RatingProc")
public class RatingProc implements RatingProcInter {
  @Autowired
  private RatingDAOInter ratingDAO;

  @Override
  public int create(RatingVO ratingVO) {
    int cnt = this.ratingDAO.create(ratingVO);
    return cnt;
  }

}
