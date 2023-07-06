<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="dev.mvc.calendar.CalendarVO" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0,
                                 maximum-scale=5.0, width=device-width" /> 
<title>http://localhost:9093/calendar/read.jsp?calendarno=1</title>
<style type="text/css">
  *{ font-family: Malgun Gothic; font-size: 26px;}
</style>
</head>
<body>
<DIV style="font-size: 20px;">
<% CalendarVO calendarVO = (CalendarVO) request.getAttribute("calendarVO"); %>

 calendarno:<%= calendarVO.getCalendarno() %><br>
 labeldate:<%= calendarVO.getLabeldate() %><br>
 title :<%= calendarVO.getTitle() %><br>
 content:<%= calendarVO.getContent() %><br>
 rdate:<%= calendarVO.getRdate() %><br>
 memberno:<%= calendarVO.getMemberno() %><br>
</DIV>
</body>
</html>

