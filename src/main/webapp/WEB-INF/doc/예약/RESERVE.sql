/**********************************/
/* Table Name: 예약 */
/**********************************/
drop table reserve;
CREATE TABLE RESERVE(
    RESERVENO                         NUMBER(20)     NOT NULL    PRIMARY KEY,
    MEMBERNO                          NUMBER(10)     NOT NULL,
    RESTCONTENTSNO                    NUMBER(20)     NOT NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO),
  FOREIGN KEY (RESTCONTENTSNO) REFERENCES RESTCONTENTS (RESTCONTENTSNO)
);

COMMENT ON TABLE RESERVE is '예약';
COMMENT ON COLUMN RESERVE.RESERVENO is '예약번호';
COMMENT ON COLUMN RESERVE.MEMBERNO is '회원 번호';
COMMENT ON COLUMN RESERVE.RESTCONTENTSNO is '맛집 컨텐츠 번호';


DROP SEQUENCE RESERVE_seq;
CREATE SEQUENCE RESERVE_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
 