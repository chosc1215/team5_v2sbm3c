<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
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
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
</head> 
 
<body>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'>
  ${notescateVO.name }(${search_count })  
</DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
  
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.admin_id != null }">
      <%--
      http://localhost:9091/notescontents/create.do?notescateno=1
      http://localhost:9091/notescontents/create.do?notescateno=2
      http://localhost:9091/notescontents/create.do?notescateno=3
      --%>
      <A href="./create.do?notescateno=${notescateVO.notescateno }">등록</A>
      <span class='menu_divide' >│</span>
    </c:if>
    
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>    
    <A href="./list_by_notescateno.do?notescateno=${param.notescateno }&now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">기본 목록형</A>    
    
  </ASIDE>
  
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
      <c:forEach var="notescontentsVO" items="${list}">
        <c:set var="title" value="${notescontentsVO.title }" />
        <c:set var="content" value="${notescontentsVO.content }" />
        <c:set var="notescateno" value="${notescontentsVO.notescateno }" />
        <c:set var="notescontentsno" value="${notescontentsVO.notescontentsno }" />
        <c:set var="thumb1" value="${notescontentsVO.thumb1 }" />
        
        
         <tr style="height: 112px;" onclick="location.href='./read.do?notescontentsno=${notescontentsno }&word=${param.word }&now_page=${param.now_page == null ? 1 : param.now_page }'" class='hover'>
          <td style='vertical-align: middle; text-align: center; '>
            <c:choose>
              <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
                <%-- registry.addResourceHandler("/notescontents/storage/**").addResourceLocations("file:///" +  Notescontents.getUploadDir()); --%>
                <img src="/notescontents/storage/${thumb1 }" style="width: 120px; height: 90px;">
              </c:when>
              <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력: /static/notescontents/images/none1.png -->
                <IMG src="/notescontents/images/none1.png" style="width: 120px; height: 90px;">
              </c:otherwise>
            </c:choose>
          </td>  
          <td style='vertical-align: middle;'>
            <div style='font-weight: bold;'><a href="./read.do?notescontentsno=${notescontentsno }&word=${param.word }&now_page=${param.now_page == null ? 1 : param.now_page }">${title }</a></div>
            <c:choose> 
              <c:when test="${content.length() > 160 }"> <%-- 160자 이상이면 160자만 출력 --%>
                  <a href="./read.do?notescontentsno=${notescontentsno }&word=${param.word }&now_page=${param.now_page == null ? 1 : param.now_page }">${content.substring(0, 160)}.....</a>
              </c:when>
              <c:when test="${content.length() <= 160 }">
                  <a href="./read.do?notescontentsno=${notescontentsno }&word=${param.word }&now_page=${param.now_page == null ? 1 : param.now_page }">${content}</a>
              </c:when>
            </c:choose>
            <div style='font-size: 0.95em;'>${rdate }</div>
          </td> 
          
          <c:choose>
            <c:when test="${sessionScope.admin_id != null }"> 
              <td style='vertical-align: middle; text-align: center;'>
                <A href="/notescontents/map.do?notescateno=${notescateno }&notescontentsno=${notescontentsno}&now_page=${param.now_page == null ? 1 : param.now_page}" title="지도"><IMG src="/notescontents/images/map.png" class="icon"></A>
                <A href="/notescontents/youtube.do?notescateno=${notescateno }&notescontentsno=${notescontentsno}&now_page=${param.now_page == null ? 1 : param.now_page}" title="Youtube"><IMG src="/notescontents/images/youtube.png" class="icon"></A>
                <A href="/notescontents/delete.do?notescateno=${notescateno }&notescontentsno=${notescontentsno}&now_page=${param.now_page == null ? 1 : param.now_page}" title="삭제"><IMG src="/notescontents/images/delete.png" class="icon"></A>
              </td>
            </c:when>
            <c:otherwise>
            
            </c:otherwise>
          </c:choose>
          
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

