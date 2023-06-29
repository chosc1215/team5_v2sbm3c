package dev.mvc.rating;

public interface RatingDAOInter {
  
  /**
   * 평점 등록
   * @param ratingVO
   * @return
   */
  public int create(RatingVO ratingVO);

}
