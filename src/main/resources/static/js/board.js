let index = {
    init: function() {
    // on은 첫번 째는 어떤일이 하면, 두번째는 어떤 일이 일어나는 지
        $("#btn-save").on("click", () => { // function(){}, ()=>{} this를 바인딩하기 위해서!
            this.save();
        });
        $("#btn-delete").on("click", () => { // function(){}, ()=>{} this를 바인딩하기 위해서!
              this.deleteById();
        });
        $("#btn-update").on("click", () => { // function(){}, ()=>{} this를 바인딩하기 위해서!
              this.update();
        });
        $("#btn-reply-save").on("click", () => { // function(){}, ()=>{} this를 바인딩하기 위해서!
            this.replySave();
        });
    },

    save:function(){
        let data= {
            title: $("#title").val(),
            content: $("#content").val(),
        }

      $.ajax({
        type:"POST",
        url:"/api/board", // join은 어짜피 post면 insert이면 알기 때문
        data: JSON.stringify(data), // http body 데이터
        contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입 (Mime)
        dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
      }).done(function(resp){ // 응답의 결과를 여기에 넣음
        alert("글쓰기가 완료되었습니다.");
        console.log(resp);
        location.href="/";
      }).fail(function(error){
        alert(JSON.stringify(error));
      }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
    },

    deleteById:function(){
          let id = $("#id").text();

          $.ajax({
            type:"DELETE",
            url:"/api/board/"+id, // join은 어짜피 post면 insert이면 알기 때문 // http body 데이터
          }).done(function(resp){ // 응답의 결과를 여기에 넣음
            alert("삭제가 완료되었습니다.");
            location.href="/";
          }).fail(function(error){
            alert(JSON.stringify(error));
          }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
        },

     update:function(){
            let id= $("#id").val();

             let data= {
                 title: $("#title").val(),
                 content: $("#content").val(),
             }

           $.ajax({
             type:"PUT",
             url:"/api/board/" + id, // join은 어짜피 post면 insert이면 알기 때문
             data: JSON.stringify(data), // http body 데이터
             contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입 (Mime)
             dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
           }).done(function(resp){ // 응답의 결과를 여기에 넣음
             alert("글수정이 완료되었습니다.");
             console.log(resp);
             location.href="/";
           }).fail(function(error){
             alert(JSON.stringify(error));
           }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
         },

    replySave:function(){
        let data= {
            content: $("#reply-content").val()
        };
        let boardId = $("#boardId").val();


        $.ajax({
            type:"POST",
            url:`/api/board/${boardId}/reply`, // join은 어짜피 post면 insert이면 알기 때문
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입 (Mime)
            dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
        }).done(function(resp){ // 응답의 결과를 여기에 넣음
            alert("댓글 쓰기가 완료되었습니다.");
            console.log(resp);
            location.href=`/board/${boardId}`;
        }).fail(function(error){
            alert(JSON.stringify(error));
        }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
    },

}

index.init();

