/**********************************/
/* Table Name: 리뷰 */
/**********************************/
CREATE TABLE REVIEW(
    REVIEWNO                          NUMBER(10)     NOT NULL    PRIMARY KEY,
    RESTCONTENTSNO                    NUMBER(20)     NOT NULL,
    MEMBERNO                          NUMBER(10)     NOT NULL,
    RECONTENT                         VARCHAR2(2000)     NOT NULL,
    RATING                            NUMBER(2)    NULL ,
    RDATE                             DATE     NOT NULL,
  FOREIGN KEY (RESTCONTENTSNO) REFERENCES RESTCONTENTS (RESTCONTENTSNO),
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE REVIEW is '리뷰';
COMMENT ON COLUMN REVIEW.REVIEWNO is '리뷰번호';
COMMENT ON COLUMN REVIEW.RESTCONTENTSNO is '맛집 컨텐츠 번호';
COMMENT ON COLUMN REVIEW.MEMBERNO is '회원 번호';
COMMENT ON COLUMN REVIEW.RECONTENT is '리뷰내용';
COMMENT ON COLUMN REVIEW.RATING is '평점';
COMMENT ON COLUMN REVIEW.RDATE is '등록일';

DROP SEQUENCE REVIEW_seq;

CREATE SEQUENCE REVIEW_seq
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE; 

--등록
INSERT INTO review(REVIEWNO, RESTCONTENTSNO, MEMBERNO, RECONTENT, RATING, RDATE) 
VALUES(REVIEW_seq.nextval, 2, 3, '좋아요', 5, sysdate);

--조회
SELECT reviewno, restcontentsno, memberno, recontent, rating, rdate
FROM review
ORDER BY reviewno ASC;

--수정
UPDATE review
SET recontent = '좋군요'
WHERE reviewno = 1;

--삭제
DELETE FROM review
WHERE reviewno = 1;
  
  
  
  