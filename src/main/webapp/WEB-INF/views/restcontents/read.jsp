<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="restcontentsno" value="${restcontentsVO.restcontentsno }" />
<c:set var="restcateno" value="${restcontentsVO.restcateno }" />
<c:set var="title" value="${restcontentsVO.title }" />        
<c:set var="file1" value="${restcontentsVO.file1 }" />
<c:set var="file1saved" value="${restcontentsVO.file1saved }" />
<c:set var="thumb1" value="${restcontentsVO.thumb1 }" />
<c:set var="content" value="${restcontentsVO.content }" />
<c:set var="map" value="${restcontentsVO.map }" />
<c:set var="youtube" value="${restcontentsVO.youtube }" />
<c:set var="word" value="${restcontentsVO.word }" />
<c:set var="size1_label" value="${restcontentsVO.size1_label }" />
<c:set var="rdate" value="${restcontentsVO.rdate.substring(0, 16) }" />
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>동서울 터미널과 함께하는 맛집리스트</title>
<link rel="shortcut icon" href="/images/star.jpg" /> <%-- /static 기준 --%>
 <link href="/css/style.css" rel="Stylesheet" type="text/css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
   <script type="text/javascript">
  $(function(){
      $('#btn_recom').on("click", function() { update_recom_ajax(${restcontentsno}); });
    $('#btn_login').on('click', login_ajax);
    $('#btn_loadDefault').on('click', loadDefault); 
  // ---------------------------------------- 댓글 관련 시작 ----------------------------------------
    var frm_reply = $('#frm_reply');
    $('#content', frm_reply).on('click', check_login);  // 댓글 작성시 로그인 여부 확인
    $('#btn_create', frm_reply).on('click', reply_create);  // 댓글 작성시 로그인 여부 확인

    list_by_restcontentsno_join();
    // ---------------------------------------- 댓글 관련 종료 ----------------------------------------
    
  });


  function loadDefault() {
    $('#id').val('user1');
    $('#passwd').val('1234');
  } 
  
  <%-- 로그인 --%>
  function login_ajax() {
    var params = "";
    params = $('#frm_login').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
    // params += '&${ _csrf.parameterName }=${ _csrf.token }';
    // console.log(params);
    // return;
    
    $.ajax(
      {
        url: '/member/login_ajax.do',
        type: 'post',  // get, post
        cache: false, // 응답 결과 임시 저장 취소
        async: true,  // true: 비동기 통신
        dataType: 'json', // 응답 형식: json, html, xml...
        data: params,      // 데이터
        success: function(rdata) { // 응답이 온경우
          var str = '';
          console.log('-> login cnt: ' + rdata.cnt);  // 1: 로그인 성공
          
          if (rdata.cnt == 1) {
            // 쇼핑카트에 insert 처리 Ajax 호출
            $('#div_login').hide();
            // alert('로그인 성공');
            $('#login_yn').val('YES'); // 로그인 성공 기록
            cart_ajax_post(); // 쇼핑카트에 insert 처리 Ajax 호출     
            
          } else {
            alert('로그인에 실패했습니다.<br>잠시후 다시 시도해주세요.');
            
          }
        },
        // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
        error: function(request, status, error) { // callback 함수
          console.log(error);
        }
      }
    );  //  $.ajax END

  } 

  // 댓글 작성시 로그인 여부 확인
  function check_login() {
    var frm_reply = $('#frm_reply');
    if ($('#memberno', frm_reply).val().length == 0 ) {
      $('#modal_title').html('댓글 등록'); // 제목 
      $('#modal_content').html("로그인해야 등록 할 수 있습니다."); // 내용
      $('#modal_panel').modal();            // 다이얼로그 출력
      return false;  // 실행 종료
    }
  }

  // 댓글 등록
  function reply_create() {
    var frm_reply = $('#frm_reply');
    
    if (check_login() !=false) { // 로그인 한 경우만 처리
      var params = frm_reply.serialize(); // 직렬화: 키=값&키=값&...
      // alert(params);
      // return; 

      // 자바스크립트: 영숫자, 한글, 공백, 특수문자: 글자수 1로 인식
      // 오라클: 한글 1자가 3바이트임으로 300자로 제한
      // alert('내용 길이: ' + $('#content', frm_reply).val().length);
      // return;
      
      if ($('#content', frm_reply).val().length > 300) {
        $('#modal_title').html('댓글 등록'); // 제목 
        $('#modal_content').html("댓글 내용은 300자이상 입력 할 수 없습니다."); // 내용
        $('#modal_panel').modal();           // 다이얼로그 출력
        return;  // 실행 종료
      }

      $.ajax({
        url: "../reply/create.do", // action 대상 주소
        type: "post",          // get, post
        cache: false,          // 브러우저의 캐시영역 사용안함.
        async: true,           // true: 비동기
        dataType: "json",   // 응답 형식: json, xml, html...
        data: params,        // 서버로 전달하는 데이터
        success: function(rdata) { // 서버로부터 성공적으로 응답이 온경우
          // alert(rdata);
          var msg = ""; // 메시지 출력
          var tag = ""; // 글목록 생성 태그
          
          if (rdata.cnt > 0) {
            $('#modal_content').attr('class', 'alert alert-success'); // CSS 변경
            msg = "댓글을 등록했습니다.";
            $('#content', frm_reply).val('');
            $('#passwd', frm_reply).val('');

            list_by_restcontentsno_join(); // 댓글 목록을 새로 읽어옴
            
            $('#reply_list').html(''); // 댓글 목록 패널 초기화, val(''): 안됨
            $("#reply_list").attr("data-replypage", 1);  // 댓글이 새로 등록됨으로 1로 초기화
            
            // list_by_restcontentsno_join_add(); // 페이징 댓글, 페이징 문제 있음.
            // alert('댓글 목록 읽기 시작');
            // global_rdata = new Array(); // 댓글을 새로 등록했음으로 배열 초기화
            // global_rdata_cnt = 0; // 목록 출력 글수
            
            // list_by_restcontentsno_join(); // 페이징 댓글
          } else {
            $('#modal_content').attr('class', 'alert alert-danger'); // CSS 변경
            msg = "댓글 등록에 실패했습니다.";
          }
          
          $('#modal_title').html('댓글 등록'); // 제목 
          $('#modal_content').html(msg);     // 내용
          $('#modal_panel').modal();           // 다이얼로그 출력
        },
        // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
        error: function(request, status, error) { // callback 함수
          var msg = 'ERROR request.status: '+request.status + '/ ' + error;
          console.log(msg); // Chrome에 출력
        }
      });
    }
  }
  
  // contentsno 별 소속된 댓글 목록
  function list_by_restcontentsno_join() {
    var params = 'restcontentsno=' + ${restcontentsVO.restcontentsno };

    $.ajax({
      url: "../reply/list_by_restcontentsno_join.do", // action 대상 주소
      type: "get",           // get, post
      cache: false,          // 브러우저의 캐시영역 사용안함.
      async: true,           // true: 비동기
      dataType: "json",   // 응답 형식: json, xml, html...
      data: params,        // 서버로 전달하는 데이터
      success: function(rdata) { // 서버로부터 성공적으로 응답이 온경우
        // alert(rdata);
        var msg = '';
        
        $('#reply_list').html(''); // 패널 초기화, val(''): 안됨

/*         // -------------------- 전역 변수에 댓글 목록 추가 --------------------
        reply_list = rdata.list;
        // -------------------- 전역 변수에 댓글 목록 추가 --------------------
        // alert('rdata.list.length: ' + rdata.list.length);
        
        var last_index=1; 
        if (rdata.list.length >= 2 ) { // 글이 2건 이상이라면 2건만 출력
          last_index = 2
        } */
        
        for (i=0; i < rdata.list.length; i++) {
          var row = rdata.list[i];
          
          msg += "<DIV id='"+row.replyno+"' style='border-bottom: solid 1px #EEEEEE; margin-bottom: 10px;'>";
          msg += "<span style='font-weight: bold;'>" + row.id + "</span>";
          msg += "  " + row.rdate;
          
          if ('${sessionScope.memberno}' == row.memberno) { // 글쓴이 일치여부 확인, 본인의 글만 삭제 가능함 ★
            msg += " <A href='javascript:reply_delete("+row.replyno+")'><IMG src='/reply/images/delete.png'></A>";
            msg += " <A href='javascript:reply_update_reply("+row.replyno+")'><IMG src='/reply/images/update_reply.png'></A>";
          }
          msg += "  " + "<br>";
          msg += row.content;
          msg += "</DIV>";
        }
        // alert(msg);
        $('#reply_list').append(msg);
      },
      // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
      error: function(request, status, error) { // callback 함수
        console.log(error);
      }
    });
    
  }

  //댓글 삭제 레이어 출력
  function reply_delete(replyno) {
    // alert('replyno: ' + replyno);
    var frm_reply_delete = $('#frm_reply_delete');
    $('#replyno', frm_reply_delete).val(replyno); // 삭제할 댓글 번호 저장
    $('#modal_panel_delete').modal();             // 삭제폼 다이얼로그 출력
  }

  //댓글 수정 레이어 출력
  function reply_update_reply(replyno) {
    // alert('replyno: ' + replyno);
    var frm_reply_update_reply = $('#frm_reply_update_reply');
    $('#replyno', frm_reply_update_reply).val(replyno); // 삭제할 댓글 번호 저장
    $('#modal_panel_update_reply').modal();             // 삭제폼 다이얼로그 출력
  } 

  // 댓글 삭제 처리
  function reply_delete_proc(replyno) {
    // alert('replyno: ' + replyno);
    var params = $('#frm_reply_delete').serialize();
    $.ajax({
      url: "../reply/delete.do", // action 대상 주소
      type: "post",           // get, post
      cache: false,          // 브러우저의 캐시영역 사용안함.
      async: true,           // true: 비동기
      dataType: "json",   // 응답 형식: json, xml, html...
      data: params,        // 서버로 전달하는 데이터
      success: function(rdata) { // 서버로부터 성공적으로 응답이 온경우
        // alert(rdata);
        var msg = "";
        
        if (rdata.passwd_cnt ==1) { // 패스워드 일치
          if (rdata.delete_cnt == 1) { // 삭제 성공

            $('#btn_frm_reply_delete_close').trigger("click"); // 삭제폼 닫기, click 발생 
            
            $('#' + replyno).remove(); // 태그 삭제
              
            return; // 함수 실행 종료
          } else {  // 삭제 실패
            msg = "패스 워드는 일치하나 댓글 삭제에 실패했습니다. <br>";
            msg += " 다시한번 시도해주세요."
          }
        } else { // 패스워드 일치하지 않음.
          // alert('패스워드 불일치');
          // return;
          
          msg = "패스워드가 일치하지 않습니다.";
          $('#modal_panel_delete_msg').html(msg);

          $('#passwd', '#frm_reply_delete').focus();  // frm_reply_delete 폼의 passwd 태그로 focus 설정
          
        }
      },
      // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
      error: function(request, status, error) { // callback 함수
        console.log(error);
      }
    });
  }

  // 댓글 수정 처리
  function reply_update_reply_proc(replyno) {
    // alert('replyno: ' + replyno);
    var params = $('#frm_reply_update_reply').serialize();
    $.ajax({
      url: "../reply/update_reply.do", // action 대상 주소
      type: "post",           // get, post
      cache: false,          // 브러우저의 캐시영역 사용안함.
      async: true,           // true: 비동기
      dataType: "json",   // 응답 형식: json, xml, html...
      data: params,        // 서버로 전달하는 데이터
      success: function(rdata) { // 서버로부터 성공적으로 응답이 온경우
        // alert(rdata);
        var msg = "";
        
        if (rdata.passwd_cnt ==1) { // 패스워드 일치
          if (rdata.delete_cnt == 1) { // 삭제 성공

            $('#btn_frm_reply_update_reply_close').trigger("click"); // 삭제폼 닫기, click 발생 
            
            $('#' + replyno).remove(); // 태그 삭제
              
            return; // 함수 실행 종료
          } else {  // 삭제 실패
            msg = "패스 워드는 일치하나 댓글 삭제에 실패했습니다. <br>";
            msg += " 다시한번 시도해주세요."
          }
        } else { // 패스워드 일치하지 않음.
          // alert('패스워드 불일치');
          // return;
          
          msg = "패스워드가 일치하지 않습니다.";
          $('#modal_panel_update_reply_msg').html(msg);

          $('#passwd', '#frm_reply_update_reply').focus();  // frm_reply_delete 폼의 passwd 태그로 focus 설정
          
        }
      },
      // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
      error: function(request, status, error) { // callback 함수
        console.log(error);
      }
    });
  }

/*   // // [더보기] 버튼 처리
  function list_by_restcontentsno_join_add() {
    // alert('list_by_restcontentsno_join_add called');
    
    let cnt_per_page = 2; // 2건씩 추가
    let replyPage=parseInt($("#reply_list").attr("data-replyPage"))+cnt_per_page; // 2
    $("#reply_list").attr("data-replyPage", replyPage); // 2
    
    var last_index=replyPage + 2; // 4
    // alert('replyPage: ' + replyPage);
    
    var msg = '';
    for (i=replyPage; i < last_index; i++) {
      var row = reply_list[i];
      
      msg = "<DIV id='"+row.replyno+"' style='border-bottom: solid 1px #EEEEEE; margin-bottom: 10px;'>";
      msg += "<span style='font-weight: bold;'>" + row.id + "</span>";
      msg += "  " + row.rdate;
      
      if ('${sessionScope.memberno}' == row.memberno) { // 글쓴이 일치여부 확인, 본인의 글만 삭제 가능함 ★
        msg += " <A href='javascript:reply_delete("+row.replyno+")'><IMG src='/reply/images/delete.png'></A>";
      }
      msg += "  " + "<br>";
      msg += row.content;
      msg += "</DIV>";

      // alert('msg: ' + msg);
      $('#reply_list').append(msg);
    }    
  } 
   */
  
</script>
    
</head> 
 
<body>
<c:import url="/menu/top.do" />

    <!-- Modal 알림창 시작 -->
    <div class="modal fade" id="modal_panel" role="dialog">
      <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h4 class="modal-title" id='modal_title'></h4><!-- 제목 -->
          </div>
          <div class="modal-body">
            <p id='modal_content'></p>  <!-- 내용 -->
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div> <!-- Modal 알림창 종료 -->

    <!-- -------------------- 댓글 삭제폼 시작 -------------------- -->
    <div class="modal fade" id="modal_panel_delete" role="dialog">
      <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h4 class="modal-title">댓글 삭제</h4><!-- 제목 -->
          </div>
          <div class="modal-body">
            <form name='frm_reply_delete' id='frm_reply_delete'>
              <input type='hidden' name='replyno' id='replyno' value=''>
              
              <label>패스워드</label>
              <input type='password' name='passwd' id='passwd' class='form-control'>
              <DIV id='modal_panel_delete_msg' style='color: #AA0000; font-size: 1.1em;'></DIV>
            </form>
          </div>
          <div class="modal-footer">
            <button type="submit" class='btn btn-danger' 
                         onclick="reply_delete_proc(frm_reply_delete.replyno.value); frm_reply_delete.passwd.value='';">삭제</button>
    
            <button type="button" class="btn btn-default" data-dismiss="modal" 
                         id='btn_frm_reply_delete_close'>Close</button>
          </div>
        </div>
      </div>
    </div>
    <!-- -------------------- 댓글 삭제폼 종료 -------------------- -->
 
<DIV class='title_line'><A href="./list_by_restcateno.do?restcateno=${restcateno }" class='title_link'>${restcateVO.name }</A></DIV>

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
      <A href="./update_text.do?restcontentsno=${restcontentsno}&now_page=${param.now_page}&word=${param.word }">글 수정</A>
      <span class='menu_divide' >│</span>
      <A href="./update_file.do?restcontentsno=${restcontentsno}&now_page=${param.now_page}">파일 수정</A>  
      <span class='menu_divide' >│</span>
      <A href="./map.do?restcateno=${restcateno }&restcontentsno=${restcontentsno}">지도</A>
      <span class='menu_divide' >│</span>
      <A href="./youtube.do?restcateno=${restcateno }&restcontentsno=${restcontentsno}">Youtube</A>
      <span class='menu_divide' >│</span>
      <A href="./delete.do?restcontentsno=${restcontentsno}&now_page=${param.now_page}&restcateno=${restcateno}">삭제</A>  
      <span class='menu_divide' >│</span>
    </c:if>

    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>    
    <A href="./list_by_restcateno.do?restcateno=${restcateno }&now_page=${param.now_page}&word=${param.word }">기본 목록형</A>    
    <span class='menu_divide' >│</span>
    <A href="./list_by_restcateno_grid.do?restcateno=${restcateno }&now_page=${param.now_page}&word=${param.word }">갤러리형</A>
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
  
  

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">
        <DIV style="width: 100%; word-break: break-all;">
          <c:choose>
            <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
              <%-- /static/restcontents/storage/ --%>
              <img src="/restcontents/storage/${file1saved }" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:when>
            <c:otherwise> <!-- 기본 이미지 출력 -->
              <img src="/restcontents/images/none1.png" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:otherwise>
          </c:choose>

          <span style="font-size: 1.5em; font-weight: bold;">${title }</span><br>
          <div style="font-size: 1em;">${mname } /  ${rdate }</div><br>
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
          <br><br><br><br>
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
            첨부 파일: <a href='/download?dir=/restcontents/storage&filename=${file1saved}&downname=${file1}' title="이미지 다운로드">${file1}<IMG SRC="/restcontents/images/download.png" title="이미지 다운로드"></a> (${size1_label})  
          </c:if>
        </DIV>
      </li>   
    </ul>
  </fieldset>

</DIV>
   

  <%-- ******************** Ajax 기반 로그인 폼 시작 ******************** --%>
  <DIV id='div_login' style='width: 80%; margin: 0px auto; display: none;'>
  <FORM name='frm_login' id='frm_login' method='POST' action='/member/login_ajax.do' class="form-horizontal">
    <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }">
    <input type="hidden" name="restcontentsno" id="restcontentsno" value="restcontentsno">
    <input type="hidden" name="login_yn" id="login_yn" value="NO">
          
    <div class="form-group">
      <label class="col-md-4 control-label" style='font-size: 0.8em;'>아이디</label>    
      <div class="col-md-8">
        <input type='text' class="form-control" name='id' id='id' 
                   value='${ck_id }' required="required" 
                   style='width: 30%;' placeholder="아이디" autofocus="autofocus">
        <Label>   
          <input type='checkbox' name='id_save' value='Y' 
                    ${ck_id_save == 'Y' ? "checked='checked'" : "" }> 저장
        </Label>                   
      </div>
 
    </div>   
 
    <div class="form-group">
      <label class="col-md-4 control-label" style='font-size: 0.8em;'>패스워드</label>    
      <div class="col-md-8">
        <input type='password' class="form-control" name='passwd' id='passwd' 
                  value='${ck_passwd }' required="required" style='width: 30%;' placeholder="패스워드">
        <Label>
          <input type='checkbox' name='passwd_save' value='Y' 
                    ${ck_passwd_save == 'Y' ? "checked='checked'" : "" }> 저장
        </Label>
      </div>
    </div>   
 
    <div class="form-group">
      <div class="col-md-offset-4 col-md-8">
        <button type="button" id='btn_login' class="btn btn-primary btn-md">로그인</button>
        <button type='button' onclick="location.href='./create.do'" class="btn btn-primary btn-md">회원가입</button>
        <button type='button' id='btn_loadDefault' class="btn btn-primary btn-md">테스트 계정</button>
        <button type='button' id='btn_cancel' class="btn btn-primary btn-md"
                    onclick="$('#div_login').hide();">취소</button>
      </div>
    </div>   
    
  </FORM>
  </DIV>
  <%-- ******************** Ajax 기반 로그인 폼 종료 ******************** --%>

<!-- ------------------------------ 댓글 영역 시작 ------------------------------ -->
<DIV style='width: 80%; margin: 0px auto;'>
    <HR>
    <FORM name='frm_reply' id='frm_reply'> <%-- 댓글 등록 폼 --%>
        <input type='hidden' name='restcontentsno' id='restcontentsno' value='${restcontentsno}'>
        <input type='hidden' name='memberno' id='memberno' value='${sessionScope.memberno}'>
        
        <textarea name='content' id='content' style='width: 100%; height: 60px;' placeholder="댓글 작성, 로그인해야 등록 할 수 있습니다."></textarea>
        <input type='password' name='passwd' id='passwd' placeholder="비밀번호">
        <button type="button" id='btn_create'>등록</button>

    </FORM>
    <HR>
    <DIV id='reply_list' data-replypage='0'>  <%-- 댓글 목록 --%>
    
    </DIV>
    <!-- <DIV id='reply_list_btn' style='border: solid 1px #EEEEEE; margin: 0px auto; width: 100%; background-color: #EEFFFF;'>
        <button id='btn_add' style='width: 100%;'>더보기 ▽</button>
    </DIV>   -->
</DIV>

<!-- ------------------------------ 댓글 영역 종료 ------------------------------ -->
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>


 
</html>


