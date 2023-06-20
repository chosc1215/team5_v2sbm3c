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
  ${restcateVO.name }(${search_count }) 
      
</DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
  
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.admin_id != null }">
      <%--
      http://localhost:9091/restcontents/create.do?restcateno=1
      http://localhost:9091/restcontents/create.do?restcateno=2
      http://localhost:9091/restcontents/create.do?restcateno=3
      --%>
      <A href="./create.do?restcateno=${restcateVO.restcateno }">등록</A>
      <span class='menu_divide' >│</span>
    </c:if>
    
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>    
    <A href="./list_by_restcateno.do?restcateno=${param.restcateno }&now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">기본 목록형</A>    
    <span class='menu_divide' >│</span>
    <A href="./list_by_restcateno_grid.do?restcateno=${param.restcateno }&now_page=${param.now_page == null ? 1: param.now_page}&word=${param.word }">갤러리형</A>
  </ASIDE>
  
  <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_restcateno.do'>
      <input type='hidden' name='restcateno' value='${restcateVO.restcateno }'>  <%-- 게시판의 구분 --%>
      
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
                    onclick="location.href='./list_by_restcateno.do?restcateno=${restcateVO.restcateno}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </DIV>

  <DIV class='menu_line'></DIV>
  
  <div style='width: 100%;'> <%-- 갤러리 Layout 시작 --%>
    <c:forEach var="restcontentsVO" items="${list }" varStatus="status">
      <c:set var="title" value="${restcontentsVO.title.trim() }" />
      <c:set var="content" value="${restcontentsVO.content.trim() }" />
      <c:set var="restcateno" value="${restcontentsVO.restcateno }" />
      <c:set var="restcontentsno" value="${restcontentsVO.restcontentsno }" />
      <c:set var="thumb1" value="${restcontentsVO.thumb1 }" />
      <c:set var="size1" value="${restcontentsVO.size1 }" />
        
      <%-- 하나의 행에 이미지를 5개씩 출력후 행 변경, index는 0부터 시작 --%>
      <c:if test="${status.index % 5 == 0 && status.index != 0 }"> 
        <HR class='menu_line'> <%-- 줄바꿈 --%>
      </c:if>
        
      <!-- 4기준 하나의 이미지, 24 * 4 = 96% -->
      <!-- 5기준 하나의 이미지, 19.2 * 5 = 96% -->
      <div onclick="location.href='./read.do?restcontentsno=${restcontentsno }&word=${param.word }&now_page=${param.now_page == null ? 1 : param.now_page }'" class='hover'  
             style='width: 19%; height: 216px; float: left; margin: 0.5%; padding: 0.1%; background-color: #EEEFFF; text-align: left;'>
        
        <c:choose> 
          <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
            <%-- registry.addResourceHandler("/restcontents/storage/**").addResourceLocations("file:///" +  Contents.getUploadDir()); --%>
            <img src="/restcontents/storage/${thumb1 }" style="width: 100%; height: 140px;">
          </c:when>
          <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력: /static/restcontents/images/none1.png -->
            <IMG src="/restcontents/images/none1.png" style="width: 100%; height: 140px;">
          </c:otherwise>
        </c:choose>
        
        <strong>
          <c:choose> 
            <c:when test="${title.length() > 25 }"> <%-- 25 이상이면 25자만 출력, 공백:&nbsp; 6자 --%>
              ${title.substring(0, 25)}.....
            </c:when>
            <c:when test="${title.length() <= 25 }">
              ${title}
            </c:when>
          </c:choose>
        </strong>
        <br>
        
        <div style='font-size: 0.95em; word-break: break-all;'>
          <c:choose>         
            <c:when test="${content.length() > 55 }"> <%-- 55 이상이면 55자만 출력 --%>
              ${content.substring(0, 55)}.....
            </c:when>
            <c:when test="${content.length() <= 55 }">
              ${content}
            </c:when>
          </c:choose>
        </div>
        
      </div>
      
    </c:forEach>
  </div>
  
  <!-- 페이지 목록 출력 부분 시작 -->
  <DIV class='bottom_menu'>${paging }</DIV> <%-- 페이지 리스트 --%>
  <!-- 페이지 목록 출력 부분 종료 -->
  
</DIV>

 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>

