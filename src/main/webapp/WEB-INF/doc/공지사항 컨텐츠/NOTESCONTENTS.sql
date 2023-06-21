/**********************************/
/* Table Name: 공지사항 컨텐츠 */
/**********************************/
drop table notescontents;
CREATE TABLE NOTESCONTENTS(
		NOTESCONTENTSNO               		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		TITLE                         		VARCHAR2(200)		 NOT NULL,
		CONTENT                       		CLOB		 NOT NULL,
		WORD                          		VARCHAR2(300)		 NULL ,
		FILE1                         		VARCHAR2(100)		 NULL ,
		FILE1SAVED                    		VARCHAR2(100)		 NULL ,
		THUMB1                        		VARCHAR2(100)		 NULL ,
		SIZE1                         		NUMBER(10)		 NULL ,
		MAP                           		VARCHAR2(1000)		 NULL ,
		NOTESCATENO                   		NUMBER(10)		 NOT NULL ,
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
COMMENT ON COLUMN NOTESCONTENTS.NOTESCATENO is '공지사항 카테고리 번호';
COMMENT ON COLUMN NOTESCONTENTS.ADMINNO is '관리자 번호';

select*from notescontents;

DROP SEQUENCE notescontents_seq;

CREATE SEQUENCE notescontents_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

-- 등록 화면 유형 1: 커뮤니티(공지사항, 게시판, 자료실, 갤러리,  Q/A...)글 등록
INSERT INTO notescontents(notescontentsno, title, content, word, file1, file1saved, thumb1, size1,notescateno, adminno )
VALUES(notescontents_seq.nextval, '공지사항 컨텐츠', '공지사항 내용', '검색어', 'space.jpg', 'space_1.jpg', 'space_t.jpg', 1000,1,3 );

-- SELECT READ
SELECT notescontentsno, title, content, word, file1, file1saved, thumb1, size1,notescateno, adminno
    FROM notescontents
    WHERE notescontentsno = 3;
    
    --테스트
    
   SELECT notescontentsno, title, content, word, file1, file1saved, thumb1, size1,notescateno, adminno
    FROM  (
              SELECT notescontentsno, title, content, word, file1, file1saved, thumb1, size1, adminno, notescateno, rownum as r
              FROM (
                        SELECT notescontentsno, title, content, word, file1, file1saved, thumb1, size1,notescateno, adminno
                        FROM notescontents
                            WHERE  (UPPER(title) LIKE '%' || '' || '%' 
                                                              OR UPPER(content) LIKE '%' ||'' || '%' 
                                                              OR UPPER(word) LIKE '%' || '' || '%')
                
                        ORDER BY notescontentsno DESC
               )
    )
    WHERE r>=1 AND r <= 30;

