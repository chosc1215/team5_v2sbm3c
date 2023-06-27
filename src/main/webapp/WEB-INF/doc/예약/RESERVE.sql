/**********************************/
/* Table Name: 설문조사항목 */
/**********************************/
DROP TABLE calendar;
CREATE TABLE calendar(
  calendarno  NUMBER(8)   NOT NULL   PRIMARY KEY,
  labeldate   VARCHAR(10)  NOT NULL, -- 달력에 출력되는 문장의 기준날짜
  label       VARCHAR(100)  NOT NULL, -- 달력에 출력될 레이블
  title       VARCHAR(100) NOT NULL, -- 제목(*)
  content     CLOB          NOT NULL, -- 글 내용
  cnt         NUMBER(7)    DEFAULT 0, -- 조회수
  rdate       DATE         NOT NULL,  -- 등록날짜
  passwd     VARCHAR(60)   NOT NULL, -- 패스워드, 영숫자 조합
  MEMBERNO   NUMBER(10)		 NOT NULL,  -- 레코드를 구분하는 컬럼
  
  FOREIGN KEY (memberno) REFERENCES member (memberno)
);

COMMENT ON TABLE calendar is '예약';
COMMENT ON COLUMN calendar.contentsno is '예약번호 번호';
COMMENT ON COLUMN calendar.labeldate is '달력에 출력되는 문장의 기준날짜';
COMMENT ON COLUMN calendar.label is '달력의 출력될 레이블';
COMMENT ON COLUMN calendar.title is '제목';
COMMENT ON COLUMN calendar.content is '글 내용';
COMMENT ON COLUMN calendar.cnt is '조회수';
COMMENT ON COLUMN calendar.rdate is '등록날짜';
COMMENT ON COLUMN calendar.passwd is '패스워드';
COMMENT ON COLUMN calendar.memberno is '회원번호 , 레코드를 구분하는 컬럼';

DROP SEQUENCE calendar_seq;

CREATE SEQUENCE calendar_seq
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 99999999  -- 최대값: 9999999999 --> NUMBER(8) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지
  
-- CREATE -> SELECT LIST -> SELECT READ -> UPDATE -> DELETE -> COUNT(*)
-- CREATE
INSERT INTO calendar(labeldate, label, title, content, cnt, rdate)
VALUES('2015-02-16', '개강', '개강 안내입니다.', '"개강 내용입니다.', 0, now());

INSERT INTO calendar(labeldate, label, title, content, cnt, rdate)
VALUES('2015-02-17', '종강', '종강 안내입니다.', '"종강 내용입니다.', 0, now());

INSERT INTO calendar(labeldate, label, title, content, cnt, rdate)
VALUES('2015-02-19', '설날', '설날 안내입니다.', '"설날 내용입니다.', 0, now());

-- 전체 목록
SELECT calendarno, labeldate, label, title, content, cnt, rdate
FROM calendar
ORDER BY calendarno DESC;

 calendarno labeldate  label title     content    cnt rdate
 ---------- ---------- ----- --------- ---------- --- ---------------------
          3 2015-02-19 설날    설날 안내입니다. "설날 내용입니다.   0 2015-02-16 19:49:23.0
          2 2015-02-17 종강    종강 안내입니다. "종강 내용입니다.   0 2015-02-16 19:49:22.0
          1 2015-02-16 개강    개강 안내입니다. "개강 내용입니다.   0 2015-02-16 19:49:20.0


-- 특정 날짜의 목록
SELECT calendarno, labeldate, label
FROM calendar
WHERE labeldate = '2015-02-16';

 calendarno labeldate  label
 ---------- ---------- -----
          1 2015-02-16 개강

          
SELECT calendarno, labeldate, label
FROM calendar
WHERE substring(labeldate, 1, 7) = '2015-02'; -- 2월달

 calendarno labeldate  label
 ---------- ---------- -----
          1 2015-02-16 개강
          2 2015-02-17 종강
          3 2015-02-19 설날

          
-- 조회
UPDATE calendar
SET cnt = cnt + 1
WHERE calendarno = 1;


SELECT calendarno, labeldate, label, title, content, cnt, regdate
FROM calendar
WHERE calendarno = 1;


-- 변경
UPDATE calendar
SET labeldate='', label='', title='', content=''
WHERE calendarno = 1;


-- 삭제
DELETE FROM calendar
WHERE calendarno = 1;



