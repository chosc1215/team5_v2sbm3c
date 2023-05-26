/**********************************/
/* Table Name: 카테고리 */
/**********************************/
CREATE TABLE RESTCATE(
		RESTCATENO                    		NUMBER(50)		 NOT NULL		 PRIMARY KEY,
		NAME                          		VARCHAR2(30)		 NOT NULL,
		CNT                           		NUMBER(10)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
		UDATE                         		DATE		 NULL ,
		SEQNO                         		NUMBER(10)		 NOT NULL,
		visible                       		CHAR(1)		 DEFAULT 'N'		 NOT NULL
);

COMMENT ON TABLE RESTCATE is '카테고리';
COMMENT ON COLUMN RESTCATE.RESTCATENO is '카테고리번호';
COMMENT ON COLUMN RESTCATE.NAME is '카테고리 이름';
COMMENT ON COLUMN RESTCATE.CNT is '관련 자료수';
COMMENT ON COLUMN RESTCATE.RDATE is '등록일';
COMMENT ON COLUMN RESTCATE.UDATE is '수정일';
COMMENT ON COLUMN RESTCATE.SEQNO is '출력 순서';
COMMENT ON COLUMN RESTCATE.visible is '출력 모드';


/**********************************/
/* Table Name: 공지사항 */
/**********************************/
CREATE TABLE CONTENTS(
		NOTESNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		TITLE                         		VARCHAR2(200)		 NOT NULL,
		CONTENT                       		CLOB(4000)		 NOT NULL,
		CNT                           		NUMBER(7)		 NOT NULL,
		PASSWD                        		VARCHAR2(15)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
		FILE1                         		VARCHAR2(100)		 NULL ,
		FILE1SAVED                    		VARCHAR2(100)		 NULL ,
		THUMB1                        		VARCHAR2(100)		 NULL ,
		SIZE1                         		NUMBER(10)		 NULL ,
		MAP                           		VARCHAR2(1000)		 NULL ,
		YOUTUBE                       		VARCHAR2(1000)		 NULL 
);

COMMENT ON TABLE CONTENTS is '공지사항';
COMMENT ON COLUMN CONTENTS.NOTESNO is '공지사항 번호';
COMMENT ON COLUMN CONTENTS.TITLE is '제목';
COMMENT ON COLUMN CONTENTS.CONTENT is '내용';
COMMENT ON COLUMN CONTENTS.CNT is '조회수';
COMMENT ON COLUMN CONTENTS.PASSWD is '패스워드';
COMMENT ON COLUMN CONTENTS.RDATE is '등록일';
COMMENT ON COLUMN CONTENTS.FILE1 is '메인 이미지';
COMMENT ON COLUMN CONTENTS.FILE1SAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN CONTENTS.THUMB1 is '메인 이미지 Preview';
COMMENT ON COLUMN CONTENTS.SIZE1 is '메인 이미지 크기';
COMMENT ON COLUMN CONTENTS.MAP is '지도';
COMMENT ON COLUMN CONTENTS.YOUTUBE is 'Youtube 영상';


/**********************************/
/* Table Name: 관리자 */
/**********************************/
CREATE TABLE ADMIN(
		ADMINNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(20)		 NOT NULL,
		PASSWD                        		VARCHAR2(15)		 NOT NULL,
		MNAME                         		VARCHAR2(20)		 NOT NULL,
		MDATE                         		DATE		 NOT NULL,
		GRADE                         		NUMBER(2)		 NOT NULL
);

COMMENT ON TABLE ADMIN is '관리자';
COMMENT ON COLUMN ADMIN.ADMINNO is '관리자 번호';
COMMENT ON COLUMN ADMIN.ID is '아이디(UQ)';
COMMENT ON COLUMN ADMIN.PASSWD is '패스워드';
COMMENT ON COLUMN ADMIN.MNAME is '성명';
COMMENT ON COLUMN ADMIN.MDATE is '가입일';
COMMENT ON COLUMN ADMIN.GRADE is '등급';


