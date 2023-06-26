/**********************************/
/* Table Name: 설문조사항목 */
/**********************************/
CREATE TABLE calendar(
  calendarno  INT          NOT NULL   AUTO_INCREMENT PRIMARY KEY,
  labeldate   VARCHAR(10)  NOT NULL, -- 출력할 날짜 2013-10-20 
  label       VARCHAR(20)  NOT NULL, -- 달력에 출력될 레이블
  title       VARCHAR(100) NOT NULL, -- 제목(*)
  content     TEXT          NOT NULL, -- 글 내용
  cnt         INT           DEFAULT 0, -- 조회수
  regdate     DATETIME      NOT NULL  -- 등록 날짜
);

INSERT INTO calendar(labeldate, label, title, content, cnt, regdate)
VALUES('2015-02-16', '개강', '개강 안내입니다.', '"개강 내용입니다.', 0, now());

INSERT INTO calendar(labeldate, label, title, content, cnt, regdate)
VALUES('2015-02-17', '종강', '종강 안내입니다.', '"종강 내용입니다.', 0, now());

INSERT INTO calendar(labeldate, label, title, content, cnt, regdate)
VALUES('2015-02-19', '설날', '설날 안내입니다.', '"설날 내용입니다.', 0, now());

-- 전체 목록
SELECT calendarno, labeldate, label, title, content, cnt, regdate
FROM calendar
ORDER BY calendarno DESC;

 calendarno labeldate  label title     content    cnt regdate
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



