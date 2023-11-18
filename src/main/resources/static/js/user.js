let index = {
    init: function() {
    // on은 첫번 째는 어떤일이 하면, 두번째는 어떤 일이 일어나는 지
        $("#btn-save").on("click", () => { // function(){}, ()=>{} this를 바인딩하기 위해서!
            this.save();
        });
        $("#btn-update").on("click", () => { // function(){}, ()=>{} this를 바인딩하기 위해서!
                    this.update();
        });
    },

    save:function(){
//        alert("user의 save함수 호출됨.");
        let data= {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }
//        console.log(data);

      // ajax호출시 default가 비동기 호출
      // ajax가 통신에 성공하고 json을 리턴해주면 자동으로 자바 오브젝트로 변환
      $.ajax({
        // 회원가입 수행 요청 비동기이기 때문에 밑에 꺼 실행하다가 응답이 오면 다시 와서 실행함.
        type:"POST",
        url:"/auth/joinProc", // join은 어짜피 post면 insert이면 알기 때문
        data: JSON.stringify(data), // http body 데이터
        contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입 (Mime)
        dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
      }).done(function(resp){ // 응답의 결과를 여기에 넣음
        if(resp.status == 500) {
            alert("회원가입이 실패되었습니다.");
        } else {
            console.log("회원가입이 완료하였습니다.");
            location.href="/";
        }
      }).fail(function(error){
        alert(JSON.stringify(error));
      }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
    },

     update:function(){
    //        alert("user의 save함수 호출됨.");
            let data= {
                id: $("#id").val(),
                username: $("#username").val(),
                password: $("#password").val(),
                email: $("#email").val()
            }
    //        console.log(data);

          // ajax호출시 default가 비동기 호출
          // ajax가 통신에 성공하고 json을 리턴해주면 자동으로 자바 오브젝트로 변환
          $.ajax({
            // 회원가입 수행 요청 비동기이기 때문에 밑에 꺼 실행하다가 응답이 오면 다시 와서 실행함.
            type:"PUT",
            url:"/user", // join은 어짜피 post면 insert이면 알기 때문
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입 (Mime)
            dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
          }).done(function(resp){ // 응답의 결과를 여기에 넣음
            alert("회원 수정이 완료되었습니다.");
//            console.log(resp);
            location.href="/";
          }).fail(function(error){
            alert(JSON.stringify(error));
          }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
        },
}

index.init();

