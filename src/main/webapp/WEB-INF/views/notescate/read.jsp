<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="dev.mvc.notescate.NotescateVO" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0,
                                 maximum-scale=5.0, width=device-width" /> 
<title>http://localhost:9091/notescate/read.jsp?notescateno=1</title>
<style type="text/css">
  *{ font-family: Malgun Gothic; font-size: 26px;}
</style>
</head>
<body>
<DIV style="font-size: 20px;">
<% NotescateVO notescateVO = (NotescateVO) request.getAttribute("notescateVO"); %>

 notescateno:<%= notescateVO.getNotescateno() %><br>
 name:<%= notescateVO.getName() %><br>
 cnt :<%= notescateVO.getCnt() %><br>
 rdate:<%= notescateVO.getRdate() %><br>
</DIV>
</body>
</html>

