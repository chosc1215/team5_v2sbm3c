<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=5.0, width=device-width" /> 
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

<DIV class='title_line'><A href="/notescontents/list_by_notescateno.do?notescateno=${notescateVO.notescateno }" class="title_link">${notescateVO.name }</A> > ${notescontentsVO.title } > 지도 등록/수정/삭제</DIV>
 
<DIV class='content_body'>
  <ASIDE class="aside_right">
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
  <%--등록/수정/삭제 폼 --%>
  <FORM name='frm_map' method='POST' action='./map.do'>
    <input type="hidden" name="notescontentsno" value="${param.notescontentsno }">
    
    <div>
       <label>지도 스크립트</label>
       <textarea name='map' class="form-control" rows="12" style='width: 100%;'>${notescontentsVO.map }</textarea>
    </div>
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-primary">저장</button>
      <button type="button" onclick="frm_map.map.value=''; frm_map.submit();" class="btn btn-primary">지도 삭제</button>
      <button type="button" onclick="history.back();" class="btn btn-primary">취소</button>
    </div>
  
  </FORM>

  <HR>
  <DIV style="text-align: center;">
      <H4>[참고] 다음 지도의 등록 방법</H4>
      <IMG src='/notescontents/images/map01.jpg' style='width: 60%;'><br><br>
      <IMG src='/notescontents/images/map02.jpg' style='width: 60%;'><br><br>
      <IMG src='/notescontents/images/map03.jpg' style='width: 60%;'><br><br>
      <IMG src='/notescontents/images/map04.jpg' style='width: 60%;'><br><br>
      <IMG src='/notescontents/images/map05.jpg' style='width: 60%;'><br>
  </DIV>
  
</DIV>


<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>


