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

<script type="text/javascript">
    // jQuery ajax 요청
    function checkid() {
      // $('#btn_close').attr("data-focus", "이동할 태그 지정");
      
      let frm = $('#frm'); // id가 frm인 태그 검색
      let id = $('#id', frm).val(); // frm 폼에서 id가 'id'인 태그 검색
      let params = '';
      let msg = '';
    
      if ($.trim(id).length == 0) { // $.trim(id): 문자열 좌우의 공백 제거, length: 문자열 길이, id를 입력받지 않은 경우
        $('#modal_title').html('ID중복 확인'); // 제목 

        $('#modal_content').attr('class', 'alert alert-danger'); // Bootstrap CSS 변경
          msg = '· ID를 입력하세요.<br>· ID 입력은 필수 입니다.';
        $('#modal_content').html(msg);           // 내용
        
        $('#btn_close').attr("data-focus", "id");  // data-focus: 개발자가 추가한 속성, 닫기 버튼 클릭시 "id" 입력으로 focus 이동
        $('#modal_panel').modal();                 // 다이얼로그 출력, modal: 메시지 창을 닫아야 다음 동작 진행 가능

        return false;  // 회원 가입 진행 중지
        
      } else {  // when ID is entered
        params = 'id=' + id;
        // var params = $('#frm').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
        // alert('params: ' + params);
    
        $.ajax({
          url: './checkid.do', // spring execute, http://localhost:9093/admin/checkid.do?id=admin1
          type: 'get',  // post
          cache: false, // 응답 결과 임시 저장 취소
          async: true,  // true: 비동기 통신
          dataType: 'json', // 응답 형식: json, html, xml...
          data: params,      // 데이터
          success: function(rdata) { // 서버로부터 성공적으로 응답이 온경우, 통신 성공: {"cnt":1}
            // alert(rdata);
            let msg = "";
            
            if (rdata.cnt > 0) { // 아이디 중복
              $('#modal_content').attr('class', 'alert alert-danger'); // Bootstrap CSS 변경
              msg = "이미 사용중인 ID 입니다.<br>";
              msg = msg + "다른 ID를 지정해주세요.";
              $('#btn_close').attr("data-focus", "id");  // id 입력으로 focus 이동
              
            } else { // 아이디 중복 안됨.
              $('#modal_content').attr('class', 'alert alert-success'); // Bootstrap CSS 변경
              msg = "사용 가능한 ID 입니다.";
              $('#btn_close').attr("data-focus", "passwd");  // passwd 입력으로 focus 이동
              // $.cookie('checkId', 'TRUE'); // Cookie 기록
            }
            
            $('#modal_title').html('ID 중복 확인'); // 제목 
            $('#modal_content').html(msg);        // 내용
            $('#modal_panel').modal();              // 다이얼로그 출력
          },
          // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
          error: function(request, status, error) { // callback 함수
            console.log(error);
          }
        });
        
        // 처리중 출력
    /*     var gif = '';
        gif +="<div style='margin: 0px auto; text-align: center;'>";
        gif +="  <img src='/member/images/ani04.gif' style='width: 10%;'>";
        gif +="</div>";
        
        $('#panel2').html(gif);
        $('#panel2').show(); // 출력 */
        
      }
    
    }

  function setFocus() {  // focus 이동
    // console.log('btn_close click!');
    
    let tag = $('#btn_close').attr('data-focus'); // data-focus 속성에 선언된 값을 읽음 
    // alert('tag: ' + tag);
    
    $('#' + tag).focus(); // data-focus 속성에 선언된 태그를 찾아서 포커스 이동
  }
  
  function send() { // 회원 가입 처리
    let id = $('#id').val(); // 태그의 아이디가 'id'인 태그의 값
    if ($.trim(id).length == 0) { // id를 입력받지 않은 경우
      $('#modal_title').html('ID 중복 확인'); // 제목 
      
      $('#modal_content').attr('class', 'alert alert-danger'); // Bootstrap CSS 변경
      msg = '· ID를 입력하세요.<br>· ID 입력은 필수 입니다.';
      $('#modal_content').html(msg);        // 내용

      $('#btn_close').attr("data-focus", "id");  // 닫기 버튼 클릭시 id 입력으로 focus 이동
      $('#modal_panel').modal();               // 다이얼로그 출력

        return false; // 가입 중지
    } 
         
    // 패스워드를 정상적으로 2번 입력했는지 확인
    if ($('#passwd').val() != $('#passwd2').val()) {
      $('#modal_title').html('패스워드 일치 여부  확인'); // 제목   
      
      $('#modal_content').attr('class', 'alert alert-danger'); // CSS 변경
      msg = '입력된 패스워드가 일치하지 않습니다.<br>';
      msg += "패스워드를 다시 입력해주세요.<br>"; 
      $('#modal_content').html(msg);  // 내용
      $('#btn_close').attr('data-focus', 'passwd');
      $('#modal_panel').modal();         // 다이얼로그 출력
      
      return false; // submit 중지
    }

    let mname = $('#mname').val(); // 태그의 아이디가 'id'인 태그의 값
    if ($.trim(mname).length == 0) { // id를 입력받지 않은 경우
      $('#modal_title').html('이름 입력 누락'); // 제목 
        
      $('#modal_content').attr('class', 'alert alert-danger'); // Bootstrap CSS 변경  
      msg = '· 이름을 입력하세요.<br>· 이름 입력은 필수입니다.';
      $('#modal_content').html(msg);        // 내용
      $('#btn_close').attr("data-focus", "mname");  // 닫기 버튼 클릭시 mname 입력으로 focus 이동
      $('#modal_panel').modal();               // 다이얼로그 출력

      return false;
    } 

    $('#frm').submit(); // required="required" 작동 안됨.
  }  
</script>
</head> 


<body>
<c:import url="/menu/top.do" />

  <!-- ******************** Modal 알림창 시작 ******************** -->
  <div id="modal_panel" class="modal fade"  role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title" id='modal_title'></h4><%-- 제목 --%>
          <button type="button" id='btn_close_modal' class="close" data-dismiss="modal">×</button>
        </div>
        <div class="modal-body">
          <p id='modal_content'></p>  <%-- 내용 --%>
        </div>
        <div class="modal-footer"> <%-- data-focus="": 캐럿(커서)을 보낼 태그의 id --%>
          <button type="button" id="btn_close" data-focus="" onclick="setFocus()" class="btn btn-default" 
                      data-dismiss="modal">닫기</button> <%-- data-focus: 캐럿이 이동할 태그 --%>
        </div>
      </div>
    </div>
  </div>
  <!-- ******************** Modal 알림창 종료 ******************** -->

  <DIV class='title_line'>관리자 회원 가입(*: 필수)</DIV>

  <DIV class='content_body'>

  <ASIDE class="aside_right">
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span> 
    <A href='./create.do'>관리자 회원 가입</A>
    <span class='menu_divide' >│</span> 
    <A href='./list.do'>목록</A>
  </ASIDE> 

  <div class='menu_line'></div>
  
  <div style="width: 60%; margin: 0px auto ">
  <FORM name='frm' id='frm' method='POST' action='./create.do' class="">
  
    <div class="form-group"> <%-- 줄이 변경되지 않는 패턴 --%>
      <label>아이디*:
        <input type='text' class="form-control form-control-sm" name='id' id='id' value='admin1' required="required" placeholder="아이디*" autofocus="autofocus">
      </label>
      <button type='button' id="btn_checkid" onclick="checkid()" class="btn btn-info btn-sm">중복확인</button>
    </div>   
                
    <div class="form-group"> <%-- label의 크기에따라 input 태그의 크기가 지정되는 형태 --%>
      <label>패스워드*: 
        <input type='password' class="form-control form-control-sm" name='passwd' id='passwd' value='1234' required="required" placeholder="패스워드*">
      </label>
    </div>   

    <div class="form-group"> <%-- label의 크기에따라 input 태그의 크기가 지정되는 형태 --%>
      <label>패스워드 확인*: 
        <input type='password' class="form-control form-control-sm" name='passwd2' id='passwd2' value='1234' required="required" placeholder="패스워드 확인*">
      </label>
    </div>   
    
    <div class="form-group"> <%-- label의 크기에따라 input 태그의 크기가 지정되는 형태 --%>
      <label>관리자 성명*:
        <input type='text' class="form-control form-control-sm" name='mname' id='mname' value='관리자1' required="required" placeholder="성명*">
      </label>
    </div>   
    
    </div>
    
    <div class="form_input">
      <button type="button" id='btn_send' onclick="send()" class="btn btn-info btn-sm">가입</button>
      <button type="button" onclick="history.back()" class="btn btn-info btn-sm">취소</button>
    </div>   
  </FORM>
  </DIV>
  
  </DIV>
  
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>

</html>