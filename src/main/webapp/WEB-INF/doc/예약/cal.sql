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
/* Table Name: 맛집 컨텐츠 */
/**********************************/
CREATE TABLE RESTCONTENTS(
		RESTCONTENTSNO                		NUMBER(20)		 NOT NULL		 PRIMARY KEY,
		ADMINNO                       		NUMBER(10)		 NOT NULL,
		RESTCATENO                    		NUMBER(10)		 NOT NULL,
		TITLE                         		VARCHAR2(200)		 NOT NULL,
		CONTENT                       		CLOB(4000)		 NOT NULL,
		RECOM                         		NUMBER(7)		 DEFAULT 0		 NOT NULL,
		RATING                        		NUMBER(3,2)		 NULL ,
		CNT                           		NUMBER(7)		 DEFAULT 0		 NOT NULL,
		REPLYCNT                      		NUMBER(7)		 DEFAULT 0		 NOT NULL,
		PASSWD                        		VARCHAR2(15)		 NOT NULL,
		WORD                          		VARCHAR2(100)		 NULL ,
		RDATE                         		DATE		 NOT NULL,
		FILE1                         		VARCHAR2(100)		 NULL ,
		FILE1SAVED                    		VARCHAR2(100)		 NULL ,
		THUMB1                        		VARCHAR2(100)		 NULL ,
		SIZE1                         		NUMBER(10)		 DEFAULT 0		 NULL ,
		MAP                           		VARCHAR2(1000)		 NULL ,
		YOUTUBE                       		VARCHAR2(1000)		 NULL ,
  FOREIGN KEY (RESTCATENO) REFERENCES RESTCATE (RESTCATENO),
  FOREIGN KEY (ADMINNO) REFERENCES ADMIN (ADMINNO)
);

COMMENT ON TABLE RESTCONTENTS is '맛집 컨텐츠';
COMMENT ON COLUMN RESTCONTENTS.RESTCONTENTSNO is '맛집 컨텐츠 번호';
COMMENT ON COLUMN RESTCONTENTS.ADMINNO is '관리자 번호';
COMMENT ON COLUMN RESTCONTENTS.RESTCATENO is '카테고리번호';
COMMENT ON COLUMN RESTCONTENTS.TITLE is '제목';
COMMENT ON COLUMN RESTCONTENTS.CONTENT is '내용';
COMMENT ON COLUMN RESTCONTENTS.RECOM is '추천수';
COMMENT ON COLUMN RESTCONTENTS.RATING is '평점';
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
/* Table Name: 댓글 */
/**********************************/
CREATE TABLE REPLY(
		REPLYNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		RESTCONTENTSNO                		NUMBER(20)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		CONTENT                       		VARCHAR2(2000)		 NULL ,
		PASSWD                        		VARCHAR2(20)		 NOT NULL,
		RDATE                         		DATE		 NULL ,
  FOREIGN KEY (RESTCONTENTSNO) REFERENCES RESTCONTENTS (RESTCONTENTSNO),
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE REPLY is '댓글';
COMMENT ON COLUMN REPLY.REPLYNO is '댓글 번호';
COMMENT ON COLUMN REPLY.RESTCONTENTSNO is '맛집 컨텐츠 번호';
COMMENT ON COLUMN REPLY.MEMBERNO is '회원 번호';
COMMENT ON COLUMN REPLY.CONTENT is '내용';
COMMENT ON COLUMN REPLY.PASSWD is '비밀번호';
COMMENT ON COLUMN REPLY.RDATE is '등록일';


/**********************************/
/* Table Name: 김상진 */
/**********************************/
CREATE TABLE 김상진(

);

COMMENT ON TABLE 김상진 is '김상진';


/**********************************/
/* Table Name: 조성철 */
/**********************************/
CREATE TABLE 조성철(

);

COMMENT ON TABLE 조성철 is '조성철';


/**********************************/
/* Table Name: 공지사항 카테고리 */
/**********************************/
CREATE TABLE NOTESCATE(
		NOTESCATENO                   		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		NAME                          		VARCHAR2(50)		 NOT NULL,
		CNT                           		NUMERIC(7)		 NOT NULL,
		RDATE                         		DATE		 NULL ,
		SEQNO                         		NUMERIC(5)		 NOT NULL,
		VISIBLE                       		CHAR(1)		 NOT NULL
);

COMMENT ON TABLE NOTESCATE is '공지사항 카테고리';
COMMENT ON COLUMN NOTESCATE.NOTESCATENO is '공지사항 카테고리 번호';
COMMENT ON COLUMN NOTESCATE.NAME is '카테고리 이름';
COMMENT ON COLUMN NOTESCATE.CNT is '관련자료수';
COMMENT ON COLUMN NOTESCATE.RDATE is '등록일';
COMMENT ON COLUMN NOTESCATE.SEQNO is '출력 순서';
COMMENT ON COLUMN NOTESCATE.VISIBLE is '출력 모드';


/**********************************/
/* Table Name: 평점리뷰 */
/**********************************/
CREATE TABLE RATING(
		RATINGNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		RESTCONTENTSNO                		NUMBER(20)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		SCORE                         		NUMBER(2)		 DEFAULT 1		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
  FOREIGN KEY (RESTCONTENTSNO) REFERENCES RESTCONTENTS (RESTCONTENTSNO),
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE RATING is '평점리뷰';
COMMENT ON COLUMN RATING.RATINGNO is '평점번호';
COMMENT ON COLUMN RATING.RESTCONTENTSNO is '맛집 컨텐츠 번호';
COMMENT ON COLUMN RATING.MEMBERNO is '회원 번호';
COMMENT ON COLUMN RATING.SCORE is '평점점수';
COMMENT ON COLUMN RATING.RDATE is '등록일';


/**********************************/
/* Table Name: 추천 */
/**********************************/
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


/**********************************/
/* Table Name: 공지사항 컨텐츠 */
/**********************************/
CREATE TABLE NOTESCONTENTS(
		NOTESCONTENTSNO               		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		TITLE                         		VARCHAR2(200)		 NOT NULL,
		CONTENT                       		CLOB(4000)		 NOT NULL,
		WORD                          		VARCHAR2(300)		 NULL ,
		FILE1                         		VARCHAR2(100)		 NULL ,
		FILE1SAVED                    		VARCHAR2(100)		 NULL ,
		THUMB1                        		VARCHAR2(100)		 NULL ,
		SIZE1                         		NUMBER(10)		 NULL ,
		MAP                           		VARCHAR2(1000)		 NULL ,
		YOUTUBE                       		VARCHAR2(1000)		 NULL ,
		NOTESCATENO                   		NUMBER(10)		 NOT NULL,
		ADMINNO                       		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (NOTESCATENO) REFERENCES NOTESCATE (NOTESCATENO),
  FOREIGN KEY (ADMINNO) REFERENCES ADMIN (ADMINNO)
);

COMMENT ON TABLE NOTESCONTENTS is '공지사항 컨텐츠';
COMMENT ON COLUMN NOTESCONTENTS.NOTESCONTENTSNO is '공지사항컨텐츠번호';
COMMENT ON COLUMN NOTESCONTENTS.TITLE is '제목';
COMMENT ON COLUMN NOTESCONTENTS.CONTENT is '내용';
COMMENT ON COLUMN NOTESCONTENTS.WORD is '검색어';
COMMENT ON COLUMN NOTESCONTENTS.FILE1 is '메인 이미지';
COMMENT ON COLUMN NOTESCONTENTS.FILE1SAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN NOTESCONTENTS.THUMB1 is '메인 이미지 Preview';
COMMENT ON COLUMN NOTESCONTENTS.SIZE1 is '메인 이미지 크기';
COMMENT ON COLUMN NOTESCONTENTS.MAP is '지도';
COMMENT ON COLUMN NOTESCONTENTS.YOUTUBE is 'Youtube 영상';
COMMENT ON COLUMN NOTESCONTENTS.NOTESCATENO is '공지사항 카테고리 번호';
COMMENT ON COLUMN NOTESCONTENTS.ADMINNO is '관리자 번호';


/**********************************/
/* Table Name: 예약일정 */
/**********************************/
CREATE TABLE CALENDAR(
		CALENDARNO                    		NUMBER(20)		 NOT NULL		 PRIMARY KEY,
		labeldate                     		VARCHAR2(10)		 NOT NULL,
		TITLE                         		VARCHAR2(100)		 NOT NULL,
		content                       		CLOB(4000)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
		passwd                        		VARCHAR2(60)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		RESTCONTENTSNO                		NUMBER(20)		 NOT NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO),
  FOREIGN KEY (RESTCONTENTSNO) REFERENCES RESTCONTENTS (RESTCONTENTSNO)
);

COMMENT ON TABLE CALENDAR is '예약일정';
COMMENT ON COLUMN CALENDAR.CALENDARNO is '예약일정번호';
COMMENT ON COLUMN CALENDAR.labeldate is '달력에 출력되는 문장의 기준 날짜';
COMMENT ON COLUMN CALENDAR.TITLE is '제목';
COMMENT ON COLUMN CALENDAR.content is '글 내용';
COMMENT ON COLUMN CALENDAR.RDATE is '등록 날짜';
COMMENT ON COLUMN CALENDAR.passwd is '패스워드';
COMMENT ON COLUMN CALENDAR.MEMBERNO is '회원 번호';
COMMENT ON COLUMN CALENDAR.RESTCONTENTSNO is '맛집 컨텐츠 번호';


