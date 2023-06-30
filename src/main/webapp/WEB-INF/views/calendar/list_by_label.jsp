<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<<title>동서울 터미널과 함께하는 맛집리스트</title>
<link rel="shortcut icon" href="/images/star.jpg" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css">
  
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</head> 
 
<body>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'>
  ${calendarVO.title }  
</DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
  
    
    
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>    
    <A href="./list_by_label.do?labeldate=${param.labeldate }&now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">기본 목록형</A>    
    <span class='menu_divide' >│</span>

  </ASIDE>
  
  <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_label.do'>
      <input type='hidden' name='labeldate' value='${calendarVO.labeldate }'>  <%-- 게시판의 구분 --%>  
    </form>
  </DIV>

  <DIV class='menu_line'></DIV>
  
  <table class="table table-striped" style='width: 100%;'>
    <colgroup>
      <c:choose>
        <c:when test="${sessionScope.admin_id != null }">
          <col style="width: 10%;"></col>
          <col style="width: 80%;"></col>
          <col style="width: 10%;"></col>        
        </c:when>
        <c:otherwise>
          <col style="width: 10%;"></col>
          <col style="width: 90%;"></col>
        </c:otherwise>
      </c:choose>
    </colgroup>

<!--     <thead>
      <tr>
        <th style='text-align: center;'>파일</th>
        <th style='text-align: center;'>제목</th>
        <th style='text-align: center;'>기타</th>
      </tr>
    
    </thead> -->
    
    <tbody>
      <c:forEach var="calendarVO" items="${list}">
        <c:set var="calendarno" value="${calendarVO.calendarno }" />
        <c:set var="labeldate" value="${calendarVO.labeldate }" />
        <c:set var="title" value="${calendarVO.title }" />
        <c:set var="content" value="${calendarVO.content }" />
        <c:set var="rdate" value="${calendarVO.rdate.substring(0, 16) }" />
        
         <tr style="height: 112px;" onclick="location.href='./list_by_label.do?labeldate=${labeldate }'" class='hover'>
          
          
            
            <td style='vertical-align: middle; text-align: center;'>
            ${calendarno}   ${labeldate}
          </td>
          <td style='vertical-align: middle;'>
            <div style='font-weight: bold;'>${title }</div>
            <c:choose> 
              <c:when test="${content.length() > 160 }"> <%-- 160자 이상이면 160자만 출력 --%>
                  ${content.substring(0, 160)}.....
              </c:when>
              <c:when test="${content.length() <= 160 }">
                  ${content}
              </c:when>
            </c:choose>
          </td> 
          <td style='vertical-align: middle; text-align: center;'>
            <A href="./read_update.do?calendarno=${calendarno}" title="수정"><IMG src="/calendar/images/update.png" class="icon"></A>
            <A href="./read_delete.do?calendarno=${calendarno}" title="삭제"><IMG src="/calendar/images/delete.png" class="icon"></A>          
          </td>            
          
        </tr>
        
      </c:forEach>

    </tbody>
  </table>
  
  <!-- 페이지 목록 출력 부분 시작 -->
  <DIV class='bottom_menu'>${paging }</DIV> <%-- 페이지 리스트 --%>
  <!-- 페이지 목록 출력 부분 종료 -->
  
</DIV>

 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>

