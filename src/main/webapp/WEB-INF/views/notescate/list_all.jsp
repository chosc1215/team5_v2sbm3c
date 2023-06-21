<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="dev.mvc.notescate.NotescateVO" %>

<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css">
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head> 
 
<body>
<%-- <jsp:include page="../menu/top.jsp" flush='false' /> --%>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'>공지사항</DIV>

<DIV class='content_body'>
  <DIV id='panel_create' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <FORM name='frm_create' id='frm_create' method='POST' action='./create.do'>
      <label>카테고리 이름</label>
      <input type='text' name='name' value='' required="required" style='width: 25%; margin-right: 10px;' autofocus="autofocus">
      <label> 출력 순서</label>
      <input type='number' name='seqno' min="1" value='1' required="required" style='width: 5%;'>
  
      <button type="submit" id='submit' class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>등록</button>
      <button type="button" onclick="location.href='/notescate/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
    </FORM>
  </DIV>
  
  
 <!-- 테스트 -->
  
  <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_notescateno.do'>
      <input type='hidden' name='notescateno' value='${notescateVO.notescateno }'>  <%-- 게시판의 구분 --%>
      
      <c:choose>
        <c:when test="${param.word != '' }"> <%-- 검색하는 경우 --%>
          <input type='text' name='word' id='word' value='${param.word }' class='input_word'>
        </c:when>
        <c:otherwise> <%-- 검색하지 않는 경우 --%>
          <input type='text' name='word' id='word' value='' class='input_word'>
        </c:otherwise>
      </c:choose>
      <button type='submit' class='btn btn-info btn-sm'>검색</button>
      <c:if test="${param.word.length() > 0 }">
        <button type='button' class='btn btn-info btn-sm' 
                    onclick="location.href='./list_by_notescateno.do?notescateno=${notescateVO.notescateno}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </DIV>

  <TABLE class='table table-hover'>
    <colgroup>
      <col style='width: 10%;'/>
      <col style='width: 45%;'/>
      <col style='width: 10%;'/>    
      <col style='width: 20%;'/>
      <col style='width: 15%;'/>
    </colgroup>
   
    <thead>  
    <TR>
      <TH class="th_bs">순서</TH>
      <TH class="th_bs">카테고리 이름</TH>
      <TH class="th_bs">자료수</TH>
      <TH class="th_bs">등록일</TH>
      <TH class="th_bs">기타</TH>
    </TR>
    </thead>
    
    <tbody>
    <%
    ArrayList<NotescateVO> list = (ArrayList<NotescateVO>)request.getAttribute("list");
    
    for (int i=0; i < list.size(); i++) {
      NotescateVO notescateVO = list.get(i);
    %>
      <TR>
        <TD class='td_bs'><%= notescateVO.getSeqno() %></TD>
        <TD><a href="/notescateno/list_by_notescateno.do?notescateno=<%=notescateVO.getNotescateno() %>&now_page=1"><%=notescateVO.getName() %></a></TD>
        <TD class='td_bs'><%=notescateVO.getCnt() %></TD>
        <TD class='td_bs'><%=notescateVO.getRdate().substring(0, 10) %></TD>
        <TD>
          <A href="./read_update.do?notescateno=<%=notescateVO.getNotescateno() %>" title="수정"><IMG src="/notescate/images/update.png" class="icon"></A>
          <A href="./read_delete.do?notescateno=<%=notescateVO.getNotescateno() %>" title="삭제"><IMG src="/notescate/images/delete.png" class="icon"></A>
          <A href="./update_seqno_decrease.do?notescateno=<%=notescateVO.getNotescateno() %>" title="우선순위 높이기"><IMG src="/notescate/images/decrease.png" class="icon"></A>
          <A href="./update_seqno_increase.do?notescateno=<%=notescateVO.getNotescateno() %>" title="우선순위 낮추기"><IMG src="/notescate/images/increase.png" class="icon"></A>
 
          <%
          if (notescateVO.getVisible().equals("Y")) {
          %>
            <A href="./update_visible_n.do?notescateno=<%=notescateVO.getNotescateno() %>"><IMG src="/notescate/images/show.png" class="icon"></A>
          <%  
          } else { // N
          %>
            <A href="./update_visible_y.do?notescateno=<%=notescateVO.getNotescateno() %>"><IMG src="/notescate/images/hide.png" class="icon"></A>
          <%  
          }
          %>
                    
        </TD>
      </TR>
    <%  
    }
    %>
    </tbody>
   
  </TABLE>
</DIV>

 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>

