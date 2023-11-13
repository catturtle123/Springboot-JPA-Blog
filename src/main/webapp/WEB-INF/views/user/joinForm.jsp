<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layout/header.jsp"%>

<div class="container" action="#" method="POST">
<form>
  <div class="form-group">
    <label for="username">Username</label>
    <input type="text" class="form-control" placeholder="Enter username" id="username">
  </div>

   <div class="form-group">
       <label for="password">password</label>
       <input type="password" class="form-control" placeholder="Enter password" id="password">
     </div>

   <div class="form-group">
     <label for="email">Email</label>
     <input type="email" class="form-control" placeholder="Enter email" id="email">
   </div>
    <button id="btn-save" class="btn btn-primary">회원가입 완료</button>
</form>

</div>

<--! /는 바로 static을 찾아감 -->

<br/>

<script src="/js/user.js"> </script>

<%@ include file="../layout/footer.jsp"%>


</html>

