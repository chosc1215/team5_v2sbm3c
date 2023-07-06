/**********************************/
/* Table Name: 추천 */
/**********************************/
DROP TABLE RECOMMEND;
CREATE TABLE RECOMMEND(
		RECOMMENDNO                   		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		SEQ                           		NUMBER(2)		 DEFAULT 1		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
		RESTCATENO                    		NUMBER(10)		 NULL ,
		MEMBERNO                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (RESTCATENO) REFERENCES RESTCATE (RESTCATENO),
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE RECOMMEND is '추천';
COMMENT ON COLUMN RECOMMEND.RECOMMENDNO is '추천번호';
COMMENT ON COLUMN RECOMMEND.SEQ is '추천우선순위';
COMMENT ON COLUMN RECOMMEND.RDATE is '추천날짜';
COMMENT ON COLUMN RECOMMEND.RESTCATENO is '카테고리번호';
COMMENT ON COLUMN RECOMMEND.MEMBERNO is '회원 번호';

DROP SEQUENCE RECOMMEND_SEQ;

CREATE SEQUENCE RECOMMEND_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지

--존재하는 memberno, restcateno 등록
INSERT INTO recommend(recommendno, memberno, restcateno, seq, rdate)
VALUES (RECOMMEND_SEQ.nextval, 1, 1, 1,sysdate);

SELECT recommendno, memberno, restcateno, seq, rdate
FROM recommend
ORDER BY recommendno ASC;

-- 조회
SELECT recommendno, memberno, restcateno, seq, rdate 
FROM recommend 
WHERE memberno = 3;


DELETE FROM recommend;
DELETE FROM recommend WHERE memberno=3;
commit;

SELECT restcontentsno, adminno, restcateno, title, content, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, r
FROM (
           SELECT restcontentsno, adminno, restcateno, title, content, recom, cnt, replycnt, rdate,
                      file1, file1saved, thumb1, size1, map, youtube, rownum as r
           FROM (
                     SELECT restcontentsno, adminno, restcateno, title, content, recom, cnt, replycnt, rdate,
                                file1, file1saved, thumb1, size1, map, youtube
                     FROM restcontents
                     WHERE restcateno=1  
                     ORDER BY rdate DESC
           )          
)
WHERE r >= 1 AND r <= 5;
commit;





