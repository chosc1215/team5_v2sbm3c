<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="notesnotescontentsno" value="${notesnotescontentsVO.notesnotescontentsno }" />
<c:set var="notesnotescateno" value="${notesnotescontentsVO.notesnotescateno }" />
<c:set var="title" value="${notesnotescontentsVO.title }" />        
<c:set var="file1" value="${notesnotescontentsVO.file1 }" />
<c:set var="file1saved" value="${notesnotescontentsVO.file1saved }" />
<c:set var="thumb1" value="${notesnotescontentsVO.thumb1 }" />
<c:set var="content" value="${notesnotescontentsVO.content }" />
<c:set var="map" value="${notesnotescontentsVO.map }" />
<c:set var="youtube" value="${notesnotescontentsVO.youtube }" />
<c:set var="word" value="${notesnotescontentsVO.word }" />
<c:set var="size1_label" value="${notesnotescontentsVO.size1_label }" />
<c:set var="rdate" value="${notesnotescontentsVO.rdate.substring(0, 16) }" />
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head> 
 
<body>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'><A href="./list_by_notesnotescateno.do?notesnotescateno=${notesnotescateno }" class='title_link'>${notesnotescateVO.name }</A></DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.admin_id != null }">
      <%--
      http://localhost:9091/notesnotescontents/create.do?notesnotescateno=1
      http://localhost:9091/notesnotescontents/create.do?notesnotescateno=2
      http://localhost:9091/notesnotescontents/create.do?notesnotescateno=3
      --%>
      <A href="./create.do?notesnotescateno=${notesnotescateVO.notesnotescateno }">등록</A>
      <span class='menu_divide' >│</span>
      <A href="./update_text.do?notesnotescontentsno=${notesnotescontentsno}&now_page=${param.now_page}&word=${param.word }">글 수정</A>
      <span class='menu_divide' >│</span>
      <A href="./update_file.do?notesnotescontentsno=${notesnotescontentsno}&now_page=${param.now_page}">파일 수정</A>  
      <span class='menu_divide' >│</span>
      <A href="./map.do?notesnotescateno=${notesnotescateno }&notesnotescontentsno=${notesnotescontentsno}">지도</A>
      <span class='menu_divide' >│</span>
      <A href="./youtube.do?notesnotescateno=${notesnotescateno }&notesnotescontentsno=${notesnotescontentsno}">Youtube</A>
      <span class='menu_divide' >│</span>
      <A href="./delete.do?notesnotescontentsno=${notesnotescontentsno}&now_page=${param.now_page}&notesnotescateno=${notesnotescateno}">삭제</A>  
      <span class='menu_divide' >│</span>
    </c:if>

    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>    
    <A href="./list_by_notesnotescateno.do?notesnotescateno=${notesnotescateno }&now_page=${param.now_page}&word=${param.word }">기본 목록형</A>    
    <span class='menu_divide' >│</span>
    <A href="./list_by_notesnotescateno_grid.do?notesnotescateno=${notesnotescateno }&now_page=${param.now_page}&word=${param.word }">갤러리형</A>
  </ASIDE> 
  
  <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_notesnotescateno.do'>
      <input type='hidden' name='notesnotescateno' value='${notesnotescateVO.notesnotescateno }'>  <%-- 게시판의 구분 --%>
      
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
                    onclick="location.href='./list_by_notesnotescateno.do?notesnotescateno=${notesnotescateVO.notesnotescateno}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </DIV>
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">
        <DIV style="width: 100%; word-break: break-all;">
          <c:choose>
            <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
              <%-- /static/notesnotescontents/storage/ --%>
              <img src="/notesnotescontents/storage/${file1saved }" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:when>
            <c:otherwise> <!-- 기본 이미지 출력 -->
              <img src="/notesnotescontents/images/none1.png" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:otherwise>
          </c:choose>

          <span style="font-size: 1.5em; font-weight: bold;">${title }</span><br>
          <div style="font-size: 1em;">${mname } ${rdate }</div><br>
          ${content }
        </DIV>
      </li>
      
      <c:if test="${youtube.trim().length() > 0 }">
        <li class="li_none" style="clear: both; padding-top: 5px; padding-bottom: 5px;">
          <DIV style="text-align: center;">
            ${youtube }
          </DIV>
        </li>
      </c:if>
      
      <c:if test="${map.trim().length() > 0 }">
        <li class="li_none" style="clear: both; padding-top: 5px; padding-bottom: 5px;">
          <DIV style='text-align: center; width:640px; height: 360px; margin: 0px auto;'>
            ${map }
          </DIV>
        </li>
      </c:if>
      
      <li class="li_none" style="clear: both;">
        <DIV style='text-decoration: none;'>
          <br>
          검색어(키워드): ${word }
        </DIV>
      </li>
      <li class="li_none">
        <DIV>
          <c:if test="${file1.trim().length() > 0 }"> <%-- ServletRegister.java: registrationBean.addUrlMappings("/download"); --%>
            첨부 파일: <a href='/download?dir=/notesnotescontents/storage&filename=${file1saved}&downname=${file1}'>${file1}</a> (${size1_label})  
          </c:if>
        </DIV>
      </li>   
    </ul>
  </fieldset>

</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>


