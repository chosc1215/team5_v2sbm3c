/**********************************/
/* Table Name: 공지사항 카테고리 */
/**********************************/
drop table notescate;
CREATE TABLE NOTESCATE(
		NOTESCATENO                   		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		NAME                          		VARCHAR2(50)		 NOT NULL,
		CNT                           		NUMERIC(7)		 NOT NULL,
		RDATE                         		DATE		 NULL ,
		SEQNO                         		NUMERIC(5)		 NOT NULL,
		VISIBLE                       		CHAR(1)		 DEFAULT'N' NOT NULL
		
);

COMMENT ON TABLE NOTESCATE is '공지사항 카테고리';
COMMENT ON COLUMN NOTESCATE.NOTESCATENO is '공지사항 카테고리 번호';
COMMENT ON COLUMN NOTESCATE.NAME is '카테고리 이름';
COMMENT ON COLUMN NOTESCATE.CNT is '관련자료수';
COMMENT ON COLUMN NOTESCATE.RDATE is '등록일';
COMMENT ON COLUMN NOTESCATE.SEQNO is '출력 순서';
COMMENT ON COLUMN NOTESCATE.VISIBLE is '출력 모드';


DROP SEQUENCE notescate_seq;

CREATE SEQUENCE notescate_seq
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지
  
-- CREATE -> SELECT LIST -> SELECT READ -> UPDATE -> DELETE -> COUNT(*)
-- CREATE
INSERT INTO notescate(notescateno, name, cnt, rdate, seqno) VALUES(notescate_seq.nextval, '공지', 0, sysdate, 0);
commit;
 
