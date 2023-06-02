

/**********************************/
/* Table Name: 공지사항 카테고리 */
/**********************************/
CREATE TABLE NOTESCATE(
		NOTESCATENO                   		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		NAME                          		VARCHAR2(50)		 NOT NULL,
		CNT                           		NUMERIC(7)		 NOT NULL,
		RDATE                         		DATE		 NULL ,
		UDATE                         		DATE		 NULL ,
		SEQNO                         		NUMERIC(5)		 NOT NULL,
		VISIBLE                       		CHAR(1)		 NOT NULL
);

COMMENT ON TABLE NOTESCATE is '공지사항 카테고리';
COMMENT ON COLUMN NOTESCATE.NOTESCATENO is '공지사항 카테고리 번호';
COMMENT ON COLUMN NOTESCATE.NAME is '카테고리 이름';
COMMENT ON COLUMN NOTESCATE.CNT is '관련자료수';
COMMENT ON COLUMN NOTESCATE.RDATE is '등록일';
COMMENT ON COLUMN NOTESCATE.UDATE is '수정일';
COMMENT ON COLUMN NOTESCATE.SEQNO is '출력 순서';
COMMENT ON COLUMN NOTESCATE.VISIBLE is '출력 모드';




