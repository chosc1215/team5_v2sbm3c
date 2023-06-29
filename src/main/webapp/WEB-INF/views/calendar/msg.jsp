<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>

<%-- /static/css/style.css --%> 
<link href="/css/style.css" rel="Stylesheet" type="text/css">

<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

</head> 
<body>
<%-- <jsp:include page="../menu/top.jsp" flush='false' /> --%>
<c:import url="/menu/top.do" />

<%
// Object: 최상위 부모 타입 -> 원래의 데이터 타입으로 변경하여 사용 
String code = (String)request.getAttribute("code");
int cnt = (int)request.getAttribute("cnt");
%>

<DIV class='title_line'>일정 > 알림</DIV>
<DIV class='message'>
  <fieldset class='fieldset_basic'>
    <UL> 
      <%
      if (code.equals("create_success")) {
      %>
        <LI class='li_none'><span class="span_success">일정 등록에 성공했습니다.</span></LI>
      <%  
      } else if (code.equals("create_fail")) {
      %>
        <LI class='li_none'><span class="span_fail">일정 등록에 실패했습니다.</span></LI>
      <%  
      } else if (code.equals("update_fail")) {
      %>
        <LI class='li_none'><span class="span_fail">일정 수정에 실패했습니다.</span></LI>
      <%  
      } else if (code.equals("delete_fail")) {
      %>
        <LI class='li_none'><span class="span_fail">일정 삭제에 실패했습니다.</span></LI>
      <%  
      }
      %>
      
      <LI class='li_none'>
        <br>
        <%
        if (cnt == 0) {
        %>
          <button onclick="history.back()" class="btn btn-danger">다시시도</button>
          
        <%
        } else {
        %>
          <button onclick="location.href='./create.do'" class="btn btn-info">새로운 일정 등록</button>
        <%
        }
        %>
        <button onclick="location.href='./list_all.do'" class="btn btn-info">전체 목록</button>
      </LI>
        
    </UL>
  </fieldset>

</DIV>

<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>

</html>