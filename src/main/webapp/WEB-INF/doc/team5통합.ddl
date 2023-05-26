/**********************************/
/* Table Name: ȸ�� */
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

COMMENT ON TABLE MEMBER is 'ȸ��';
COMMENT ON COLUMN MEMBER.MEMBERNO is 'ȸ�� ��ȣ';
COMMENT ON COLUMN MEMBER.ID is '���̵�';
COMMENT ON COLUMN MEMBER.PASSWD is '�н�����';
COMMENT ON COLUMN MEMBER.MNAME is '����';
COMMENT ON COLUMN MEMBER.TEL is '��ȭ��ȣ';
COMMENT ON COLUMN MEMBER.ZIPCODE is '�����ȣ';
COMMENT ON COLUMN MEMBER.ADDRESS1 is '�ּ�1';
COMMENT ON COLUMN MEMBER.ADDRESS2 is '�ּ�2';
COMMENT ON COLUMN MEMBER.MDATE is '������';
COMMENT ON COLUMN MEMBER.GRADE is '���';


/**********************************/
/* Table Name: ī�װ� */
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

COMMENT ON TABLE RESTCATE is 'ī�װ�';
COMMENT ON COLUMN RESTCATE.RESTCATENO is 'ī�װ���ȣ';
COMMENT ON COLUMN RESTCATE.NAME is 'ī�װ� �̸�';
COMMENT ON COLUMN RESTCATE.CNT is '���� �ڷ��';
COMMENT ON COLUMN RESTCATE.RDATE is '�����';
COMMENT ON COLUMN RESTCATE.UDATE is '������';
COMMENT ON COLUMN RESTCATE.SEQNO is '��� ����';
COMMENT ON COLUMN RESTCATE.visible is '��� ���';


/**********************************/
/* Table Name: ������ */
/**********************************/
CREATE TABLE ADMIN(
		ADMINNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(20)		 NOT NULL,
		PASSWD                        		VARCHAR2(15)		 NOT NULL,
		MNAME                         		VARCHAR2(20)		 NOT NULL,
		MDATE                         		DATE		 NOT NULL,
		GRADE                         		NUMBER(2)		 NOT NULL
);

COMMENT ON TABLE ADMIN is '������';
COMMENT ON COLUMN ADMIN.ADMINNO is '������ ��ȣ';
COMMENT ON COLUMN ADMIN.ID is '���̵�(UQ)';
COMMENT ON COLUMN ADMIN.PASSWD is '�н�����';
COMMENT ON COLUMN ADMIN.MNAME is '����';
COMMENT ON COLUMN ADMIN.MDATE is '������';
COMMENT ON COLUMN ADMIN.GRADE is '���';


/**********************************/
/* Table Name: ���� ������ */
/**********************************/
CREATE TABLE RESTCONTENTS(
		RESTCONTENTSNO                		NUMBER(50)		 NOT NULL		 PRIMARY KEY,
		RESTCATENO                    		NUMBER(50)		 NOT NULL,
		ADMINNO                       		NUMBER(10)		 NOT NULL,
		TITLE                         		VARCHAR2(50)		 NOT NULL,
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
  FOREIGN KEY (RESTCATENO) REFERENCES RESTCATE (RESTCATENO),
  FOREIGN KEY (ADMINNO) REFERENCES ADMIN (ADMINNO)
);

COMMENT ON TABLE RESTCONTENTS is '���� ������';
COMMENT ON COLUMN RESTCONTENTS.RESTCONTENTSNO is '���� ������ ��ȣ';
COMMENT ON COLUMN RESTCONTENTS.RESTCATENO is 'ī�װ���ȣ';
COMMENT ON COLUMN RESTCONTENTS.ADMINNO is '������ ��ȣ';
COMMENT ON COLUMN RESTCONTENTS.TITLE is '����';
COMMENT ON COLUMN RESTCONTENTS.CONTENT is '����';
COMMENT ON COLUMN RESTCONTENTS.RECOM is '��õ��';
COMMENT ON COLUMN RESTCONTENTS.CNT is '��ȸ��';
COMMENT ON COLUMN RESTCONTENTS.REPLYCNT is '��ۼ�';
COMMENT ON COLUMN RESTCONTENTS.PASSWD is '�н�����';
COMMENT ON COLUMN RESTCONTENTS.WORD is '�˻���';
COMMENT ON COLUMN RESTCONTENTS.RDATE is '�����';
COMMENT ON COLUMN RESTCONTENTS.FILE1 is '���� �̹���';
COMMENT ON COLUMN RESTCONTENTS.FILE1SAVED is '���� ����� ���� �̹���';
COMMENT ON COLUMN RESTCONTENTS.THUMB1 is '���� �̹��� Preview';
COMMENT ON COLUMN RESTCONTENTS.SIZE1 is '���� �̹��� ũ��';
COMMENT ON COLUMN RESTCONTENTS.MAP is '����';
COMMENT ON COLUMN RESTCONTENTS.YOUTUBE is 'Youtube ����';


/**********************************/
/* Table Name: ���� */
/**********************************/
CREATE TABLE RESERVE(
		RESERVENO                     		NUMBER(50)		 NOT NULL		 PRIMARY KEY,
		RESTCONTENTSNO                		NUMBER(50)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (RESTCONTENTSNO) REFERENCES RESTCONTENTS (RESTCONTENTSNO),
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE RESERVE is '����';
COMMENT ON COLUMN RESERVE.RESERVENO is '�����ȣ';
COMMENT ON COLUMN RESERVE.RESTCONTENTSNO is '���� ������ ��ȣ';
COMMENT ON COLUMN RESERVE.MEMBERNO is 'ȸ�� ��ȣ';


/**********************************/
/* Table Name: ��� */
/**********************************/
CREATE TABLE COMMENT(
		COMMENTNO                     		NUMBER(30)		 NOT NULL		 PRIMARY KEY,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		RESTCONTENTSNO                		NUMBER(50)		 NOT NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO),
  FOREIGN KEY (RESTCONTENTSNO) REFERENCES RESTCONTENTS (RESTCONTENTSNO)
);

COMMENT ON TABLE COMMENT is '���';
COMMENT ON COLUMN COMMENT.COMMENTNO is '��� ��ȣ';
COMMENT ON COLUMN COMMENT.MEMBERNO is 'ȸ�� ��ȣ';
COMMENT ON COLUMN COMMENT.RESTCONTENTSNO is '���� ������ ��ȣ';


/**********************************/
/* Table Name: ����� */
/**********************************/
CREATE TABLE �����(

);

COMMENT ON TABLE ����� is '�����';


/**********************************/
/* Table Name: �������� */
/**********************************/
CREATE TABLE NOTES(
		NOTESNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ADMINNO                       		NUMBER(10)		 NOT NULL,
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
  FOREIGN KEY (ADMINNO) REFERENCES ADMIN (ADMINNO)
);

COMMENT ON TABLE NOTES is '��������';
COMMENT ON COLUMN NOTES.NOTESNO is '�������� ��ȣ';
COMMENT ON COLUMN NOTES.ADMINNO is '������ ��ȣ';
COMMENT ON COLUMN NOTES.TITLE is '����';
COMMENT ON COLUMN NOTES.CONTENT is '����';
COMMENT ON COLUMN NOTES.CNT is '��ȸ��';
COMMENT ON COLUMN NOTES.WORD is '�˻���';
COMMENT ON COLUMN NOTES.PASSWD is '�н�����';
COMMENT ON COLUMN NOTES.RDATE is '�����';
COMMENT ON COLUMN NOTES.FILE1 is '���� �̹���';
COMMENT ON COLUMN NOTES.FILE1SAVED is '���� ����� ���� �̹���';
COMMENT ON COLUMN NOTES.THUMB1 is '���� �̹��� Preview';
COMMENT ON COLUMN NOTES.SIZE1 is '���� �̹��� ũ��';
COMMENT ON COLUMN NOTES.MAP is '����';
COMMENT ON COLUMN NOTES.YOUTUBE is 'Youtube ����';


/**********************************/
/* Table Name: ����ö */
/**********************************/
CREATE TABLE ����ö(

);

COMMENT ON TABLE ����ö is '����ö';


