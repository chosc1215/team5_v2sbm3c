package dev.mvc.rating;

public interface RatingProcInter {
  
  /**
   * 평점 등록
   * @param ratingVO
   * @return
   */
  public int create(RatingVO ratingVO);

}
