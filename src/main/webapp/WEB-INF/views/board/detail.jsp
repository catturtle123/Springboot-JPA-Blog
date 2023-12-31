<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layout/header.jsp"%>

<br />

<div class="container">
      <br />
      <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
       <c:if test="${board.user.id == principal.user.id}">
            <a href="/board/${board.id}/updateForm" class="btn-warning">수정</a>
            <button id="btn-delete" class="btn-danger">삭제</button>
       </c:if>
       <br /> <br />
       <div>
        글 번호 : <span id="id"><i>${board.id} </i></span>
        작성자 : <span><i>${board.user.username} </i></span>
       </div>
       <br>
      <div class="form-group">
        <h3>${board.title}</h3>
      </div>
       <hr/>
      <div class="form-group">
        <div>${board.content}</div>
      </div>
    <hr/>
    <div class="card">
        <form>
            <input  type="hidden" id="userId" value="${principal.user.id}" />
            <input  type="hidden" id="boardId" value="${board.id}" />
            <div class="card-body"><textarea id="reply-content" class="form-control" rows="1"></textarea></div>
            <div class="card-footer"><button type="button" id="btn-reply-save" class="btn btn-primary">등록</button></div>
        </form>
    </div>

    <br>
    <div class="card">
        <div class="card-header">댓글 리스트</div>
        <ul id="reply-box" class="list-group">
            <c:forEach items="${board.replys}" var="reply">
                <li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
                    <div>${reply.content}</div>
                    <div class="d-flex">
                        <div class="font-italic">작성자 : ${reply.user.username} &nbsp;</div>
                        <button onclick="index.replyDelete(${board.id},${reply.id})" class="badge">삭제</button>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<br>

<script>
      $('.summernote').summernote({
        tabsize: 2,
        height: 300
      });
</script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>