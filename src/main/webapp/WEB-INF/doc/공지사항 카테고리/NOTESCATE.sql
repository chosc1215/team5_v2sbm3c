

/**********************************/
/* Table Name: 공지사항 카테고리 */
/**********************************/
CREATE TABLE NOTESCATE(
		NOTESCATENO                   		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		NAME                          		VARCHAR2(50)		 NOT NULL,
		CNT                           		NUMERIC(7)		 NOT NULL,
		RDATE                         		DATE		 NULL ,
		SEQNO                         		NUMERIC(5)		 NOT NULL,
		VISIBLE                       		CHAR(1)		 NOT NULL,
		TITLE                         		VARCHAR2(200)		 NOT NULL,
		CONTENT                       		CLOB(4000)		 NOT NULL,
		WORD                          		VARCHAR2(300)		 NULL ,
		FILE1                         		VARCHAR2(100)		 NULL ,
		FILE1SAVED                    		VARCHAR2(100)		 NULL ,
		THUMB1                        		VARCHAR2(100)		 NULL ,
		SIZE1                         		NUMBER(10)		 NULL ,
		MAP                           		VARCHAR2(1000)		 NULL ,
		YOUTUBE                       		VARCHAR2(1000)		 NULL ,
		ADMINNO                       		NUMBER(10)		 NULL ,
  FOREIGN KEY (ADMINNO) REFERENCES ADMIN (ADMINNO)
);

COMMENT ON TABLE NOTESCATE is '공지사항 카테고리';
COMMENT ON COLUMN NOTESCATE.NOTESCATENO is '공지사항 카테고리 번호';
COMMENT ON COLUMN NOTESCATE.NAME is '카테고리 이름';
COMMENT ON COLUMN NOTESCATE.CNT is '관련자료수';
COMMENT ON COLUMN NOTESCATE.RDATE is '등록일';
COMMENT ON COLUMN NOTESCATE.SEQNO is '출력 순서';
COMMENT ON COLUMN NOTESCATE.VISIBLE is '출력 모드';
COMMENT ON COLUMN NOTESCATE.TITLE is '제목';
COMMENT ON COLUMN NOTESCATE.CONTENT is '내용';
COMMENT ON COLUMN NOTESCATE.WORD is '검색어';
COMMENT ON COLUMN NOTESCATE.FILE1 is '메인 이미지';
COMMENT ON COLUMN NOTESCATE.FILE1SAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN NOTESCATE.THUMB1 is '메인 이미지 Preview';
COMMENT ON COLUMN NOTESCATE.SIZE1 is '메인 이미지 크기';
COMMENT ON COLUMN NOTESCATE.MAP is '지도';
COMMENT ON COLUMN NOTESCATE.YOUTUBE is 'Youtube 영상';
COMMENT ON COLUMN NOTESCATE.ADMINNO is '관리자 번호';


DROP SEQUENCE notescate_seq;

CREATE SEQUENCE notescate_seq
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지
  
<<<<<<< HEAD
DROP SEQUENCE notescate_seq;
INSERT INTO notescate(notescateno, name, cnt, rdate, seqno) VALUES(notescate_seq.nextval, '공지사항', 0, sysdate, 0);
 
select * from notescate;

 
delete from notescate
where notescateno = 3;
=======
-- CREATE -> SELECT LIST -> SELECT READ -> UPDATE -> DELETE -> COUNT(*)
-- CREATE
INSERT INTO notescate(notescateno, name, cnt, rdate, seqno) VALUES(notescate_seq.nextval, '강릉', 0, sysdate, 0);
INSERT INTO notescate(notescateno, name, cnt, rdate, seqno) VALUES(notescate_seq.nextval, '양양', 0, sysdate, 0);
INSERT INTO notescate(notescateno, name, cnt, rdate, seqno) VALUES(notescate_seq.nextval, '속초', 0, sysdate, 0);
INSERT INTO notescate(notescateno, name, cnt, rdate, seqno) VALUES(notescate_seq.nextval, '춘천', 0, sysdate, 0);
INSERT INTO notescate(notescateno, name, cnt, rdate, seqno) VALUES(notescate_seq.nextval, '동해', 0, sysdate, 0);
commit;
>>>>>>> 4950bfba4f3f8b0aef989ec7450c1674683a8ca9

