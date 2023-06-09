/**********************************/
/* Table Name: 회원 */
/**********************************/
DROP TABLE member CASCADE CONSTRAINTS;
DROP TABLE member;
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
COMMENT ON COLUMN MEMBER.GRADE is '등급'; -- 등급(1~10: 관리자, 11~20: 회원, 40~49: 정지 회원, 99: 탈퇴 회원)

DROP SEQUENCE member_seq;
CREATE SEQUENCE member_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
 
 
1. 등록
 
1) id 중복 확인(null 값을 가지고 있으면 count에서 제외됨)
SELECT COUNT(id)
FROM member
WHERE id='user1';

SELECT COUNT(id) as cnt
FROM member
WHERE id='user1';
 
 cnt
 ---
   0   ← 중복 되지 않음.
   
2) 등록
-- 회원 관리용 계정, Q/A 용 계정
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode,
                                 address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'qnaadmin', '1234', '질문답변관리자', '000-0000-0000', '12345',
             '서울시 종로구', '관철동', sysdate, 1);
 
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode,
                                address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'crm', '1234', '고객관리자', '000-0000-0000', '12345',
             '서울시 종로구', '관철동', sysdate, 1);
 
-- 개인 회원 테스트 계정
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'user1@gmail.com', '1234', '왕눈이', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'user2@gmail.com', '1234', '아로미', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'user3@gmail.com', '1234', '투투투', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
-- 부서별(그룹별) 공유 회원 기준
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'team1', '1234', '개발팀', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'team2', '1234', '웹퍼블리셔팀', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'team3', '1234', '디자인팀', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);

COMMIT;

 
2. 목록
- 검색을 하지 않는 경우, 전체 목록 출력
 
SELECT memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade
FROM member
WHERE memberno=12
ORDER BY grade ASC, id ASC;
     
     
3. 조회
 
1) user1@gmail.com 사원 정보 보기
SELECT memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade
FROM member
WHERE memberno = 1;

SELECT memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade
FROM member
WHERE id = 'user3@gmail.com';
 
    
4. 수정
UPDATE member 
SET id='user5', mname='아로미', tel='111-1111-1111', zipcode='00000',
      address1='경기도', address2='파주시', grade=14
WHERE memberno=1;

COMMIT;
 
5. 삭제
1) 모두 삭제
DELETE FROM member;
 
2) 특정 회원 삭제
DELETE FROM member
WHERE memberno=21;

COMMIT;

 
6. 패스워드 변경
1) 패스워드 검사
SELECT COUNT(memberno) as cnt
FROM member
WHERE memberno=1 AND passwd='1234';
 
2) 패스워드 수정
UPDATE member
SET passwd='0000'
WHERE memberno=1;

COMMIT;
 
 
7. 로그인
SELECT COUNT(memberno) as cnt
FROM member
WHERE id='user1@gmail.com' AND passwd='1234';
 cnt
 ---
   0
 
SELECT memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade
FROM member
WHERE memberno= 12;

8. 회원 탈퇴
UPDATE member 
SET grade=99
WHERE memberno=5;

-- 로그인시 정지회원
SELECT COUNT(*) as cnt
FROM member
WHERE id='user1@gmail.com' AND grade >= 40; AND grade <= 49;
-- 0: 아님
-- 1: 맞음

-- 탈퇴 회원인지 확인
SELECT COUNT(*) as cnt
FROM member
WHERE id='user5@gmail.com' AND grade = 99;

-- 컨트롤러에서 정지회원 갯수도 0 탈퇴회원 갯수도 0일 경우 로그인 처리




