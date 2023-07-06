<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="dev.mvc.calendar.CalendarVO" %>

<%
// 목록시 출력시 for문에서 calendarVO를 사용하고 있음으로 여기서는 calendarVO_read 라고 다르게 해야함.
// 삭제할 카테고리 정보를 읽어옴
CalendarVO calendarVO_read = (CalendarVO)request.getAttribute("calendarVO");
%>
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>동서울 터미널과 함께하는 맛집리스트</title>
<!-- /static 기준 -->
<link rel="shortcut icon" href="/images/star.jpg" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css">
 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>.min.css">
    
</head> 
 
<body>
<%-- <jsp:include page="../menu/top.jsp" flush='false' /> --%>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'>전체 카테고리 > 삭제</DIV>

<DIV class='content_body'>
  <DIV id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <FORM name='frm_delete' id='frm_delete' method='POST' action='./delete.do'>
      <input type="hidden" name="calendarno" value="<%=calendarVO_read.getCalendarno() %>"> <%-- 삭제할 카테고리 번호 --%>

       <c:choose>
        <c:when test="${count_by_calendarno >= 1 }"> 자식 레코드가 있는 상황
          <div class="msg_warning">
            관련 자료 ${count_by_calendarno } 건이 발견되었습니다.<br>
            관련 자료와 카테고리를 모두 삭제하시겠습니까?
          </div> 
            
           <label>관련 카테고리 이름</label>: <%=calendarVO_read.getTitle() %> 
          <a href="../notescontents/list_by_calendarno.do?calendarno=${calendarVO.calendarno }" title="관련 카테고리로 이동"><img src='/calendar/images/link.png'></a>
          &nbsp;      
          <button type="submit" id='submit' class='btn btn-danger btn-sm' style='height: 28px; margin-bottom: 5px;'>관련 자료와 함께 카테고리 삭제</button>
          
        </c:when>
        <c:otherwise>
          <div class="msg_warning">일정을 삭제하면 복구 할 수 없습니다.</div>
          <label>카테고리 이름</label>: <%=calendarVO_read.getTitle() %>
      
          <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>          
        </c:otherwise>
      </c:choose>      

      <button type="button" onclick="location.href='/calendar/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
    </FORM>
  </DIV> 

  <TABLE class='table table-hover'>
    <colgroup>
      <col style='width: 10%;'/>
      <col style='width: 50%;'/>
      <col style='width: 10%;'/>    
      <col style='width: 20%;'/>
      <col style='width: 10%;'/>
    </colgroup>
   
    <thead>  
    <TR>
      <TH class="th_bs">예약 날짜</TH>
      <TH class="th_bs">제목</TH>
      <TH class="th_bs">내용</TH>
      <TH class="th_bs">등록 날짜</TH>
      <TH class="th_bs">기타</TH>
    </TR>
    </thead>
    
    <tbody>
    <%
    ArrayList<CalendarVO> list = (ArrayList<CalendarVO>)request.getAttribute("list");
    
    for (int i=0; i < list.size(); i++) {
      CalendarVO calendarVO = list.get(i);
    %>
      <TR>
        <TD class='td_bs'><%= calendarVO.getLabeldate() %></TD>
        <TD><%= calendarVO.getTitle() %></TD>
        <TD class='td_bs'><%=calendarVO.getContent() %></TD> 
        
        <TD class='td_bs'><%=calendarVO.getRdate().substring(0, 10) %></TD>
        <TD>
          <A href="./read_update.do?calendarno=<%=calendarVO.getCalendarno() %>" title="수정"><IMG src="/calendar/images/update.png" class="icon"></A>
          <A href="./read_delete.do?calendarno=<%=calendarVO.getCalendarno() %>" title="삭제"><IMG src="/calendar/images/delete.png" class="icon"></A>
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

