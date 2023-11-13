<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layout/header.jsp"%>

<div class="container" action="#" method="POST">
<form>
  <input type="hidden" id="id" value="${principal.user.id}" />
  <div class="form-group">
    <label for="username">Username</label>
    <input type="text" value="${principal.user.username}" class="form-control" placeholder="Enter username" id="username" readonly>
  </div>

   <div class="form-group">
       <label for="password">password</label>
       <input type="password" class="form-control" placeholder="Enter password" id="password">
     </div>

   <div class="form-group">
     <label for="email">Email</label>
     <input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email">
   </div>
    <button id="btn-update" class="btn btn-primary">회원 수정 완료</button>
</form>

</div>

<br/>

<script src="/js/user.js"> </script>

<%@ include file="../layout/footer.jsp"%>


</html>
