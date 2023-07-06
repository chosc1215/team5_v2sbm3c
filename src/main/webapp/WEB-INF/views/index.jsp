 <%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
<%--<jsp:include page="./menu/top.jsp" flush='false' /> --%> 

<c:import url="/menu/top.do" />

  <c:choose>
      <c:when test="${sessionScope.id == null}"> <%-- 로그인 안된 경우 기본 이미지만 출력 --%>
        <DIV style='width: 100%; margin: 30px auto; text-align: center;'>
          <%-- /static/images/resort01.jpg --%>
          <IMG src='/images/main1.jpg' style='width: 95%; height: auto;'>
        </DIV>
      </c:when>
      <c:otherwise>
      <a class="dropdown-item" href="javascript:recommend();">관심분야 등록하고 추천받기</a>
        <c:if test="${recommend_sw==false }">
        <DIV style='width: 100%; margin: 30px auto; text-align: center;'> <%-- 로그인된 경우 추천 --%>
          <c:if test="${sessionScope.id != null}">
            <DIV style='width: 70%; margin: 10px auto;'>
              <h2>${sessionScope.mname} 님을 추천 상품</h2>
              <c:import url="/restcontents/recommend_rdate.do" />
            </DIV>
          </c:if>
        </DIV>
        </c:if>
      </c:otherwise>
  </c:choose>
 

  <DIV style='width: 94.8%; margin: 0px auto;'>
  </DIV>  
 
<jsp:include page="./menu/bottom.jsp" flush='false' />
 
</body>
</html>