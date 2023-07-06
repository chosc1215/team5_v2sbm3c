<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  
  <div style='width: 100%;'> <%-- 갤러리 Layout 시작 --%>
    <c:forEach var="restcontentsVO" items="${list_rdate }" varStatus="status">
      <c:set var="title" value="${restcontentsVO.title.trim() }" />
      <c:set var="content" value="${restcontentsVO.content.trim() }" />
      <c:set var="restcateno" value="${restcontentsVO.restcateno }" />
      <c:set var="restcontentsno" value="${restcontentsVO.restcontentsno }" />
      <c:set var="thumb1" value="${restcontentsVO.thumb1 }" />
      <c:set var="size1" value="${restcontentsVO.size1 }" />
                
      <!-- 4기준 하나의 이미지, 24 * 4 = 96% -->
      <!-- 5기준 하나의 이미지, 19.2 * 5 = 96% -->
      <div onclick="location.href='restcontents/read.do?restcontentsno=${restcontentsno }&word=${param.word }&now_page=${param.now_page == null ? 1 : param.now_page }'" class='hover'  
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
            <c:when test="${content.length() > 15 }"> <%-- 55 이상이면 55자만 출력 --%>
              ${content.substring(0, 15)}.....
            </c:when>
            <c:when test="${content.length() <= 15 }">
              ${content}
            </c:when>
          </c:choose>
        </div>
        
      </div>
      
    </c:forEach>
  </div>


