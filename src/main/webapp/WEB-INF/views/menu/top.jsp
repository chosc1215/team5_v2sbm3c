<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="dev.mvc.restcate.RestcateVO" %>

<DIV class='container_main'> 
    <!-- 헤더 start -->
    <div class="header">
        <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
            <a class="navbar-brand" href="/">동서울터미널과 함께하는 맛집</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle Navigation">
              <span class="navbar-toggler-icon"></span>
            </button>    
            
            
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="navbar-nav mr-auto">
                
                <li class="nav-item"> <%-- 서브 메뉴가 없는 독립메뉴 --%>
                    <a class="nav-link" href="/notescate/list_all.do">공지사항</a>
                  </li>
                  
                  <c:forEach var="restcateVO" items="${list}">
                    <c:set var="restcateno" value="${restcateVO.restcateno }" />
                    <c:set var="name" value="${restcateVO.name }" />
                    <li class="nav-item"> <%-- 서브 메뉴가 없는 독립메뉴 --%>
                      <a class="nav-link" href="/restcontents/list_by_restcateno.do?restcateno=${restcateno }&now_page=1" >${name }</a>
                    </li>
                  </c:forEach>
                  
                  
                  
                  <li class="nav-item"> <%-- 서브 메뉴가 없는 독립메뉴 --%>
                    <a class="nav-link" href="/restcontents/list_all.do">전체 글 목록</a>
                  </li>
                  
                    
                  <li class="nav-item">
                      <c:choose>
                          <c:when test="${sessionScope.id == null}">
                              <a class="nav-link" href="/member/login.do">로그인</a>
                          </c:when>
                          <c:otherwise>
                              <a class="nav-link" href='/member/logout.do'>${sessionScope.id } 로그아웃</a>
                          </c:otherwise>
                      </c:choose>
                  </li>
                <c:if test="${sessionScope.id != null }">  
                  <li class="nav-item dropdown"> <%-- 회원 서브 메뉴 --%>
                      <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">회원</a>
                      <div class="dropdown-menu">
                          <a class="dropdown-item" href="http://localhost:8000/ais/recommend_form/?memberno=${sessionScope.memberno }">관심분야 등록하고 추천받기</a>
                          <a class="dropdown-item" href="/member/create.do">회원 가입</a>
                          <a class="dropdown-item" href="/member/read.do">가입 정보 조회/수정</a>
                          <a class="dropdown-item" href="#">아이디 찾기</a>
                          <a class="dropdown-item" href="#">비밀번호 찾기</a>
                          <a class="dropdown-item" href="/member/passwd_update.do?memberno=${memberno }">비밀번호 변경</a>
                          <a class="dropdown-item" href="#">회원 탈퇴</a>
                      </div>
                  </li>
                </c:if>  
                  
                  <c:choose>
                    <c:when test="${sessionScope.admin_id == null }">
                      <li class="nav-item">
                        <a class="nav-link" href="/admin/login.do">관리자 로그인</a>
                      </li>
                    </c:when>
                    <c:otherwise>
                      <li class="nav-item dropdown"> <%-- 관리자 서브 메뉴 --%>
                        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">관리자</a>
                        <div class="dropdown-menu">
                          <a class="dropdown-item" href='/restcate/list_all.do'>카테고리 전체 목록</a>
                          <a class="dropdown-item" href="/admin/create.do">회원 가입</a>
                          <a class="dropdown-item" href='/member/list.do'>회원 목록</a>
                          <a class="dropdown-item" href='/admin/list.do'>관리자 목록</a>
                          <a class="dropdown-item" href='/admin/logout.do'>관리자 ${sessionScope.admin_id } 로그아웃</a>
                        </div>
                      </li>
                    </c:otherwise>
                  </c:choose>     
                </ul>
            </div>    
        </nav>

    </div>
    <!-- 헤더 end -->
    
    <%-- 내용 --%> 
    <DIV class='content'>
      <div style='clear: both; height: 50px;'></div>