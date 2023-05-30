/**********************************/
/* Table Name: 맛집 컨텐츠 */
/**********************************/
CREATE TABLE RESTCONTENTS(
		RESTCONTENTSNO                		NUMBER(20)		 NOT NULL		 PRIMARY KEY,
		TITLE                         		VARCHAR2(200)		 NOT NULL,
		CONTENT                       		CLOB(4000)		 NOT NULL,
		RECOM                         		NUMBER(7)		 NOT NULL,
		CNT                           		NUMBER(7)		 NOT NULL,
		REPLYCNT                      		NUMBER(7)		 NOT NULL,
		PASSWD                        		VARCHAR2(15)		 NOT NULL,
		WORD                          		VARCHAR2(100)		 NULL ,
		RDATE                         		DATE		 NOT NULL,
		FILE1                         		VARCHAR2(100)		 NULL ,
		FILE1SAVED                    		VARCHAR2(100)		 NULL ,
		THUMB1                        		VARCHAR2(100)		 NULL ,
		SIZE1                         		NUMBER(10)		 NULL ,
		MAP                           		VARCHAR2(1000)		 NULL ,
		YOUTUBE                       		VARCHAR2(1000)		 NULL ,
		ADMINNO                       		NUMBER(10)		 NULL ,
		RESTCATENO                    		NUMBER(10)		 NULL ,
		NOTESNO                       		NUMBER(10)		 NULL ,
  FOREIGN KEY (ADMINNO) REFERENCES ADMIN (ADMINNO),
  FOREIGN KEY (RESTCATENO) REFERENCES RESTCATE (RESTCATENO)
);

COMMENT ON TABLE RESTCONTENTS is '맛집 컨텐츠';
COMMENT ON COLUMN RESTCONTENTS.RESTCONTENTSNO is '맛집 컨텐츠 번호';
COMMENT ON COLUMN RESTCONTENTS.TITLE is '제목';
COMMENT ON COLUMN RESTCONTENTS.CONTENT is '내용';
COMMENT ON COLUMN RESTCONTENTS.RECOM is '추천수';
COMMENT ON COLUMN RESTCONTENTS.CNT is '조회수';
COMMENT ON COLUMN RESTCONTENTS.REPLYCNT is '댓글수';
COMMENT ON COLUMN RESTCONTENTS.PASSWD is '패스워드';
COMMENT ON COLUMN RESTCONTENTS.WORD is '검색어';
COMMENT ON COLUMN RESTCONTENTS.RDATE is '등록일';
COMMENT ON COLUMN RESTCONTENTS.FILE1 is '메인 이미지';
COMMENT ON COLUMN RESTCONTENTS.FILE1SAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN RESTCONTENTS.THUMB1 is '메인 이미지 Preview';
COMMENT ON COLUMN RESTCONTENTS.SIZE1 is '메인 이미지 크기';
COMMENT ON COLUMN RESTCONTENTS.MAP is '지도';
COMMENT ON COLUMN RESTCONTENTS.YOUTUBE is 'Youtube 영상';
COMMENT ON COLUMN RESTCONTENTS.ADMINNO is '관리자 번호';
COMMENT ON COLUMN RESTCONTENTS.RESTCATENO is '카테고리번호';
COMMENT ON COLUMN RESTCONTENTS.NOTESNO is '공지사항 번호';

DROP SEQUENCE contents_seq;

CREATE SEQUENCE contents_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;   
  
-- 등록
INSERT INTO restcontents(restcontentsno, restcateno, adminno, title, 
        content, recom, cnt, replycnt, passwd, word, rdate)
VALUES(contents_seq.nextval, 1, 1, '제목 타이틀', '맛집 내용', 0, 0, 0, '1234', '검색어', sysdate);     

-- 목록 (전체 글 목록)
SELECT restcontentsno, restcateno, adminno, title, content, recom, cnt, replycnt, word, rdate
FROM restcontents
ORDER BY restcontentsno ASC;  




