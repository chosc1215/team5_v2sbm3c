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
    function checkID() {
      // $('#btn_close').attr("data-focus", "이동할 태그 지정");
      
      let frm = $('#frm'); // id가 frm인 태그 검색
      let id = $('#id', frm).val(); // frm 폼에서 id가 'id'인 태그 검색
      let params = '';
      let msg = '';
    
      if ($.trim(id).length == 0) { // $.trim(id): 문자열 좌우의 공백 제거, length: 문자열 길이, id를 입력받지 않은 경우
        $('#modal_title').html('ID(이메일) 중복 확인'); // 제목 

        $('#modal_content').attr('class', 'alert alert-danger'); // Bootstrap CSS 변경
          msg = '· ID(이메일)를 입력하세요.<br>· ID(이메일) 입력은 필수 입니다.<br>· ID(이메일)는 3자이상 권장합니다.';
        $('#modal_content').html(msg);           // 내용
        
        $('#btn_close').attr("data-focus", "id");  // data-focus: 개발자가 추가한 속성, 닫기 버튼 클릭시 "id" 입력으로 focus 이동
        $('#modal_panel').modal();                 // 다이얼로그 출력, modal: 메시지 창을 닫아야 다음 동작 진행 가능

        return false;  // 회원 가입 진행 중지
        
      } else {  // when ID is entered
        params = 'id=' + id;
        // var params = $('#frm').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
        // alert('params: ' + params);
    
        $.ajax({
          url: './checkID.do', // spring execute, http://localhost:9091/member/checkID.do?id=user1@gmail.com
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
              msg = "이미 사용중인 ID(이메일) 입니다.<br>";
              msg = msg + "다른 ID(이메일)을 지정해주세요.";
              $('#btn_close').attr("data-focus", "id");  // id 입력으로 focus 이동
              
            } else { // 아이디 중복 안됨.
              $('#modal_content').attr('class', 'alert alert-success'); // Bootstrap CSS 변경
              msg = "사용 가능한 ID(이메일) 입니다.";
              $('#btn_close').attr("data-focus", "passwd");  // passwd 입력으로 focus 이동
              // $.cookie('checkId', 'TRUE'); // Cookie 기록
            }
            
            $('#modal_title').html('ID(이메일) 중복 확인'); // 제목 
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
    	msg = '· ID를 입력하세요.<br>· ID 입력은 필수 입니다.<br>· ID는 3자이상 권장합니다.';
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

  <DIV class='title_line'>회원 가입(*: 필수)</DIV>

  <DIV class='content_body'>

  <ASIDE class="aside_right">
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span> 
    <A href='./create.do'>회원 가입</A>
    <span class='menu_divide' >│</span> 
    <A href='./list.do'>목록</A>
  </ASIDE> 

  <div class='menu_line'></div>
  
  <div style="width: 60%; margin: 0px auto ">
  <FORM name='frm' id='frm' method='POST' action='./create.do' class="">
  
    <div class="form-group"> <%-- 줄이 변경되지 않는 패턴 --%>
      <label>아이디*:
        <input type='text' class="form-control form-control-sm" name='id' id='id' value='user1@gmail.com' required="required" placeholder="아이디*" autofocus="autofocus">
      </label>
      <button type='button' id="btn_checkID" onclick="checkID()" class="btn btn-info btn-sm">중복확인</button>
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
      <label>성명*:
        <input type='text' class="form-control form-control-sm" name='mname' id='mname' value='왕눈이' required="required" placeholder="성명*">
      </label>
    </div>   

    <div class="form-group"> <%-- label의 크기에따라 input 태그의 크기가 지정되는 형태, 줄이 변경되지 않는 패턴 --%>
      <label>전화 번호:
        <input type='text' class="form-control form-control-sm" name='tel' id='tel' value='010-0000-0000' required="required" placeholder="전화번호*">
      </label>
      예) 010-0000-0000
    </div>   

    <div class="form-group"> <%-- 줄이 변경되지 않는 패턴 --%>
      <label>우편 번호:
        <input type='text' class="form-control form-control-sm" name='zipcode' id='zipcode' value='' placeholder="우편번호">
      </label>
      <button type="button" id="btn_DaumPostcode" onclick="DaumPostcode()" class="btn btn-info btn-sm">우편번호 찾기</button>
    </div>  

    <div class="form-group">
      <label style="width: 100%;">주소:</label> <%-- label의 크기를 변경하여 주소를 많이 입력받는 패턴 --%>
      <input type='text' class="form-control form-control-sm" name='address1' id='address1' value='' placeholder="주소">
    </div>   

    <div class="form-group">
      <label style="width: 100%;">상세 주소:</label>
      <input type='text' class="form-control form-control-sm" name='address2' id='address2' value='' placeholder="상세 주소">
    </div>   

    <div>

<!-- ------------------------------ DAUM 우편번호 API 시작 ------------------------------ -->
<div id="wrap" style="display:none;border:1px solid;width:500px;height:300px;margin:5px 110px;position:relative">
  <img src="//i1.daumcdn.net/localimg/localimages/07/postcode/320/close.png" id="btnFoldWrap" style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1" onclick="foldDaumPostcode()" alt="접기 버튼">
</div>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
  // 우편번호 찾기 화면을 넣을 element
  var element_wrap = document.getElementById('wrap');

  function foldDaumPostcode() {
      // iframe을 넣은 element를 안보이게 한다.
      element_wrap.style.display = 'none';
  }

  function DaumPostcode() {
      // 현재 scroll 위치를 저장해놓는다.
      var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
      new daum.Postcode({
          oncomplete: function(data) {
              // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

              // 각 주소의 노출 규칙에 따라 주소를 조합한다.
              // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
              var fullAddr = data.address; // 최종 주소 변수
              var extraAddr = ''; // 조합형 주소 변수

              // 기본 주소가 도로명 타입일때 조합한다.
              if(data.addressType === 'R'){
                  //법정동명이 있을 경우 추가한다.
                  if(data.bname !== ''){
                      extraAddr += data.bname;
                  }
                  // 건물명이 있을 경우 추가한다.
                  if(data.buildingName !== ''){
                      extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                  }
                  // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                  fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
              }

              // 우편번호와 주소 정보를 해당 필드에 넣는다.
              $('#zipcode').val(data.zonecode); // 5자리 새우편번호 사용 ★
              $('#address1').val(fullAddr);  // 주소 ★

              // iframe을 넣은 element를 안보이게 한다.
              // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
              element_wrap.style.display = 'none';

              // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌린다.
              document.body.scrollTop = currentScroll;
              
              $('#address2').focus(); //  ★
          },
          // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분. iframe을 넣은 element의 높이값을 조정한다.
          onresize : function(size) {
              element_wrap.style.height = size.height+'px';
          },
          width : '100%',
          height : '100%'
      }).embed(element_wrap);

      // iframe을 넣은 element를 보이게 한다.
      element_wrap.style.display = 'block';
  }
 
</script>
<!-- ------------------------------ DAUM 우편번호 API 종료 ------------------------------ -->

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