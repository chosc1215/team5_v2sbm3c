/**********************************/
/* Table Name: 회원 */
/**********************************/
CREATE TABLE MEMBER(
		MEMBERNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(20)		 NOT NULL,
		PASSWD                        		VARCHAR2(60)		 NOT NULL,
		MNAME                         		VARCHAR2(30)		 NOT NULL,
		TEL                           		VARCHAR2(14)		 NOT NULL,
		ZIPCODE                       		VARCHAR2(5)		 NULL ,
		ADDRESS1                      		VARCHAR2(80)		 NULL ,
		ADDRESS2                      		VARCHAR2(50)		 NULL ,
		MDATE                         		DATE		 NOT NULL,
		GRADE                         		NUMBER(2)		 NOT NULL
);

COMMENT ON TABLE MEMBER is '회원';
COMMENT ON COLUMN MEMBER.MEMBERNO is '회원 번호';
COMMENT ON COLUMN MEMBER.ID is '아이디';
COMMENT ON COLUMN MEMBER.PASSWD is '패스워드';
COMMENT ON COLUMN MEMBER.MNAME is '성명';
COMMENT ON COLUMN MEMBER.TEL is '전화번호';
COMMENT ON COLUMN MEMBER.ZIPCODE is '우편번호';
COMMENT ON COLUMN MEMBER.ADDRESS1 is '주소1';
COMMENT ON COLUMN MEMBER.ADDRESS2 is '주소2';
COMMENT ON COLUMN MEMBER.MDATE is '가입일';
COMMENT ON COLUMN MEMBER.GRADE is '등급';


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


/**********************************/
/* Table Name: 카테고리 */
/**********************************/
CREATE TABLE RESTCATE(
		RESTCATENO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
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
/* Table Name: 맛집 컨텐츠 */
/**********************************/
CREATE TABLE RESTCONTENTS(
		RESTCONTENTSNO                		NUMBER(20)		 NOT NULL		 PRIMARY KEY,
		ADMINNO                       		NUMBER(10)		 NOT NULL,
		RESTCATENO                    		NUMBER(10)		 NOT NULL,
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
  FOREIGN KEY (ADMINNO) REFERENCES ADMIN (ADMINNO),
  FOREIGN KEY (RESTCATENO) REFERENCES RESTCATE (RESTCATENO)
);

COMMENT ON TABLE RESTCONTENTS is '맛집 컨텐츠';
COMMENT ON COLUMN RESTCONTENTS.RESTCONTENTSNO is '맛집 컨텐츠 번호';
COMMENT ON COLUMN RESTCONTENTS.ADMINNO is '관리자 번호';
COMMENT ON COLUMN RESTCONTENTS.RESTCATENO is '카테고리번호';
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


/**********************************/
/* Table Name: 예약 */
/**********************************/
CREATE TABLE RESERVE(
		RESERVENO                     		NUMBER(20)		 NOT NULL		 PRIMARY KEY,
		COMMENTNO                     		NUMBER(10)		 NULL ,
		MEMBERNO                      		NUMBER(10)		 NULL ,
		RESTCONTENTSNO                		NUMBER(20)		 NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO),
  FOREIGN KEY (RESTCONTENTSNO) REFERENCES RESTCONTENTS (RESTCONTENTSNO)
);

COMMENT ON TABLE RESERVE is '예약';
COMMENT ON COLUMN RESERVE.RESERVENO is '예약번호';
COMMENT ON COLUMN RESERVE.COMMENTNO is '댓글 번호';
COMMENT ON COLUMN RESERVE.MEMBERNO is '회원 번호';
COMMENT ON COLUMN RESERVE.RESTCONTENTSNO is '맛집 컨텐츠 번호';


/**********************************/
/* Table Name: 댓글 */
/**********************************/
CREATE TABLE COMMENT(
		COMMENTNO                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		RESTCONTENTSNO                		NUMBER(20)		 NULL ,
		MEMBERNO                      		NUMBER(10)		 NULL ,
		CONTENT                       		VARCHAR2(2000)		 NULL ,
		PASSWD                        		VARCHAR2(20)		 NOT NULL,
		RDATE                         		DATE		 NULL ,
  FOREIGN KEY (RESTCONTENTSNO) REFERENCES RESTCONTENTS (RESTCONTENTSNO),
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE COMMENT is '댓글';
COMMENT ON COLUMN COMMENT.COMMENTNO is '댓글 번호';
COMMENT ON COLUMN COMMENT.RESTCONTENTSNO is '맛집 컨텐츠 번호';
COMMENT ON COLUMN COMMENT.MEMBERNO is '회원 번호';
COMMENT ON COLUMN COMMENT.CONTENT is '내용';
COMMENT ON COLUMN COMMENT.PASSWD is '비밀번호';
COMMENT ON COLUMN COMMENT.RDATE is '등록일';


/**********************************/
/* Table Name: 김상진 */
/**********************************/
CREATE TABLE 김상진(

);

COMMENT ON TABLE 김상진 is '김상진';


/**********************************/
/* Table Name: 공지사항 */
/**********************************/
CREATE TABLE NOTES(
		NOTESNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		TITLE                         		VARCHAR2(200)		 NOT NULL,
		CONTENT                       		CLOB(4000)		 NOT NULL,
		CNT                           		NUMBER(7)		 NOT NULL,
		WORD                          		VARCHAR2(300)		 NULL ,
		PASSWD                        		VARCHAR2(15)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
		FILE1                         		VARCHAR2(100)		 NULL ,
		FILE1SAVED                    		VARCHAR2(100)		 NULL ,
		THUMB1                        		VARCHAR2(100)		 NULL ,
		SIZE1                         		NUMBER(10)		 NULL ,
		MAP                           		VARCHAR2(1000)		 NULL ,
		YOUTUBE                       		VARCHAR2(1000)		 NULL ,
		ADMINNO                       		NUMBER(10)		 NULL ,
  FOREIGN KEY (ADMINNO) REFERENCES ADMIN (ADMINNO)
);

COMMENT ON TABLE NOTES is '공지사항';
COMMENT ON COLUMN NOTES.NOTESNO is '공지사항 번호';
COMMENT ON COLUMN NOTES.TITLE is '제목';
COMMENT ON COLUMN NOTES.CONTENT is '내용';
COMMENT ON COLUMN NOTES.CNT is '조회수';
COMMENT ON COLUMN NOTES.WORD is '검색어';
COMMENT ON COLUMN NOTES.PASSWD is '패스워드';
COMMENT ON COLUMN NOTES.RDATE is '등록일';
COMMENT ON COLUMN NOTES.FILE1 is '메인 이미지';
COMMENT ON COLUMN NOTES.FILE1SAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN NOTES.THUMB1 is '메인 이미지 Preview';
COMMENT ON COLUMN NOTES.SIZE1 is '메인 이미지 크기';
COMMENT ON COLUMN NOTES.MAP is '지도';
COMMENT ON COLUMN NOTES.YOUTUBE is 'Youtube 영상';
COMMENT ON COLUMN NOTES.ADMINNO is '관리자 번호';


/**********************************/
/* Table Name: 조성철 */
/**********************************/
CREATE TABLE 조성철(

);

COMMENT ON TABLE 조성철 is '조성철';


