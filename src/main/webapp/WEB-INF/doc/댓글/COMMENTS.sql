
/**********************************/
/* Table Name: 댓글 */
/**********************************/
select*from comments;
CREATE TABLE COMMENTS(
		COMMENTSNO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		RESTCONTENTSNO                		NUMBER(20)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		CONTENT                       		VARCHAR2(2000)		 NULL ,
		PASSWD                        		VARCHAR2(20)		 NOT NULL,
		RDATE                         		DATE		 NULL ,
  FOREIGN KEY (RESTCONTENTSNO) REFERENCES RESTCONTENTS (RESTCONTENTSNO),
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE COMMENTS is '댓글';
COMMENT ON COLUMN COMMENTS.COMMENTSNO is '댓글 번호';
COMMENT ON COLUMN COMMENTS.RESTCONTENTSNO is '맛집 컨텐츠 번호';
COMMENT ON COLUMN COMMENTS.MEMBERNO is '회원 번호';
COMMENT ON COLUMN COMMENTS.CONTENT is '내용';
COMMENT ON COLUMN COMMENTS.PASSWD is '비밀번호';
COMMENT ON COLUMN COMMENTS.RDATE is '등록일';

DROP SEQUENCE comments_seq;
CREATE SEQUENCE comments_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999999
  CACHE 2                     -- 2번은 메모리에서만 계산
  NOCYCLE;                   -- 다시 1부터 생성되는 것을 방지


1) 등록
INSERT INTO comments(commentsno, restcontentsno, memberno, content, passwd, rdate)
VALUES(comments_seq.nextval, 5, 3, '댓글1', '1234', sysdate);
INSERT INTO comments(commentsno, restcontentsno, memberno, content, passwd, rdate)
VALUES(comments_seq.nextval, 5, 3, '댓글2', '1234', sysdate);
INSERT INTO comments(commentsno, restcontentsno, memberno, content, passwd, rdate)
VALUES(comments_seq.nextval, 5, 3, '댓글3', '1234', sysdate);             

2) 전체 목록
SELECT commentsno, restcontentsno, memberno, content, passwd, rdate
FROM comments
ORDER BY commentsno DESC;

 commentsNO restcontentsno MEMBERNO CONTENT PASSWD RDATE
 ------- ---------- -------- ------- ------ ---------------------
       3          1        1 댓글3     1234   2019-12-17 16:59:38.0
       2          1        1 댓글2     1234   2019-12-17 16:59:37.0
       1          1        1 댓글1     1234   2019-12-17 16:59:36.0
       
3) comments + member join 목록
SELECT m.id,
          r.commentsno, r.restcontentsno, r.memberno, r.content, r.passwd, r.rdate
FROM member m,  comments r
WHERE m.memberno = r.memberno
ORDER BY r.commentsno DESC;

4) comments + member join + 특정 restcontentsno 별 목록
SELECT m.id,
           r.commentsno, r.restcontentsno, r.memberno, r.content, r.passwd, r.rdate
FROM member m,  comments r
WHERE (m.memberno = r.memberno) AND r.restcontentsno=5
ORDER BY r.commentsno DESC;

 ID    commentsNO restcontentsno MEMBERNO CONTENT                                                                                                                                                                         PASSWD RDATE
 ----- ------- ---------- -------- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ------ ---------------------
 user1       3          1        1 댓글 3                                                                                                                                                                            123    2019-12-18 16:46:43.0
 user1       2          1        1 댓글 2                                                                                                                                                                            123    2019-12-18 16:46:39.0
 user1       1          1        1 댓글 1      
 

5) 삭제
-- 패스워드 검사
SELECT count(passwd) as cnt
FROM comments
WHERE commentsno=1 AND passwd='1234';

 CNT
 ---
   1
   
-- 삭제
DELETE FROM comments
WHERE commentsno=1;

6) restcontentsno에 해당하는 댓글 수 확인 및 삭제
SELECT COUNT(*) as cnt
FROM comments
WHERE restcontentsno=1;

 CNT
 ---
   1

DELETE FROM comments
WHERE restcontentsno=1;

7) memberno에 해당하는 댓글 수 확인 및 삭제
SELECT COUNT(*) as cnt
FROM comments
WHERE memberno=1;

 CNT
 ---
   1

DELETE FROM comments
WHERE memberno=1;
 
8) 삭제용 패스워드 검사
SELECT COUNT(*) as cnt
FROM comments
WHERE commentsno=1 AND passwd='1234';

 CNT
 ---
   0

9) 삭제
DELETE FROM comments
WHERE commentsno=1;