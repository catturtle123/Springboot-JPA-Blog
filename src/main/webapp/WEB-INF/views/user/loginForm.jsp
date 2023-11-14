<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layout/header.jsp"%>

<div class="container">
<form action="/auth/loginProc" method="POST">
  <div class="form-group">
    <label for="username">Username</label>
    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
  </div>

  <div class="form-group">
    <label for="password">password</label>
    <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
  </div>

  <button id="btn-login" class="btn btn-primary">로그인</button>
  <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${client}&redirect_uri=${redirect}"><img height="38px" src="/image/kakao_login_button.png"></a>
</form>
  <br>
</div>

<%@ include file="../layout/footer.jsp"%>
</html>

