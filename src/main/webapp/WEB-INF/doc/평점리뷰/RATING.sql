/**********************************/
/* Table Name: 평점리뷰 */
/**********************************/
drop table rating;
CREATE TABLE RATING(
    RATINGNO                          NUMBER(10)     NOT NULL    PRIMARY KEY,
    RESTCONTENTSNO                    NUMBER(20)     NOT NULL,
    MEMBERNO                          NUMBER(10)     NOT NULL,
    SCORE                             NUMBER(2)      DEFAULT 1     NOT NULL,
    CONTENT                       	  CLOB  		 NOT NULL,
    RDATE                             DATE           NOT NULL,
  FOREIGN KEY (RESTCONTENTSNO) REFERENCES RESTCONTENTS (RESTCONTENTSNO),
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE RATING is '평점리뷰';
COMMENT ON COLUMN RATING.RATINGNO is '평점번호';
COMMENT ON COLUMN RATING.RESTCONTENTSNO is '맛집 컨텐츠 번호';
COMMENT ON COLUMN RATING.MEMBERNO is '회원 번호';
COMMENT ON COLUMN RATING.SCORE is '평점점수';
COMMENT ON COLUMN RATING.CONTENT is '리뷰내용';
COMMENT ON COLUMN RATING.RDATE is '등록일';

DROP SEQUENCE RATING_seq;

CREATE SEQUENCE RATING_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE; 
  
-- 평점리뷰 등록  
INSERT INTO rating (ratingno, restcontentsno, memberno, score, content, rdate)
VALUES(rating_seq.nextval, 11, 3, 5,'맛있어요' ,sysdate);


--특정 컨텐츠별 평점 목록
SELECT ratingno, restcontentsno, memberno, score, content, rdate
FROM rating
WHERE restcontentsno = 11;

delete FROM rating
WHERE ratingno =1; 

commit;




