/**********************************/
/* Table Name: 설문조사항목 */
/**********************************/
DROP TABLE calendar;
CREATE TABLE calendar(
  calendarno  NUMBER(8)     NOT NULL   PRIMARY KEY,
  labeldate   VARCHAR(10)   NOT NULL,  -- 출력할 날짜 2013-10-20 
  title       VARCHAR(100)  NOT NULL,  -- 제목(*)
  content     CLOB          NOT NULL,  -- 글 내용
  rdate       DATE          NOT NULL,   -- 등록 날짜
  passwd     VARCHAR(60)   NOT NULL, -- 패스워드, 영숫자 조합
  memberno    NUMBER(10) NOT NULL,     -- 회원 번호, 레코드를 구분하는 컬럼
  
  FOREIGN KEY (memberno) REFERENCES member (memberno)
);

COMMENT ON TABLE calendar is '일정';
COMMENT ON COLUMN calendar.calendarno is '일정 번호';
COMMENT ON COLUMN calendar.labeldate is '달력에 출력되는 문장의 기준 날짜';
COMMENT ON COLUMN calendar.title is '제목';
COMMENT ON COLUMN calendar.content is '글 내용';
COMMENT ON COLUMN calendar.rdate is '등록 날짜';
COMMENT ON COLUMN calendar.passwd is '패스워드';
COMMENT ON COLUMN calendar.memberno is '회원 번호';

DROP SEQUENCE calendar_seq;

CREATE SEQUENCE calendar_seq
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 99999999    -- 최대값: 99999999 --> NUMBER(8) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지


INSERT INTO calendar(calendarno, labeldate, title, content, rdate,passwd, memberno)
VALUES(calendar_seq.nextval, '2023-06-26', '주차장 건설로 임시 폐쇄합니다.', '주차장 건설로 임시 폐쇄합니다.', sysdate,'1234', 3);

INSERT INTO calendar(calendarno, labeldate,  title, content, rdate,passwd, memberno)
VALUES(calendar_seq.nextval, '2023-06-29', '주차장 건설로 임시 폐쇄합니다.2','333', sysdate,'1234', 3);
       
-- 전체 목록
SELECT calendarno, labeldate, title, content,  rdate, memberno
FROM calendar
ORDER BY calendarno DESC;

-- 6월달만 출력, SUBSTR(labeldate, 1, 7): 첫번재부터 7개의 문자 추출
SELECT calendarno, labeldate, title, content, cnt, rdate, memberno
FROM calendar
WHERE SUBSTR(labeldate, 1, 7) = '2023-06'
ORDER BY calendarno ASC;

-- 특정 날짜만 출력
SELECT calendarno, labeldate, title, content, cnt, rdate, memberno
FROM calendar
WHERE labeldate = '2023-06-26'
ORDER BY calendarno ASC;

-- 조회
SELECT calendarno, labeldate, title, content, cnt, rdate, memberno
FROM calendar
WHERE calendarno=1;
          
-- 수정
UPDATE calendar
SET labeldate='2023-06-27', title='주차장 완공', content='주차장 완공했습니다.'
WHERE calendarno = 1;

-- 조회수 증가
UPDATE calendar
SET cnt = cnt + 1
WHERE calendarno = 1;

-- 삭제
DELETE FROM calendar
WHERE calendarno = 3;

--패스워드 확인 
select*count 