let index = {
    init: function() {
      $("#btn-save").on("click", ()=> {     //function(){}, ()=> this 바인딩 하기 위함. =>를 사용해야 윈도우 객체를 가리킴.
        this.save();
      });
        $("#btn-delete").on("click", ()=> {     
        this.deleteById();
      });
       $("#btn-update").on("click", ()=> {    
        this.update();
      });
        $("#btn-reply-save").on("click", ()=> {    
        this.replySave();
      });
    },
    
    save:function(){
      let data = {
        title:$("#title").val(),
        content: $("#content").val()
      };
   // console.log(data);
   
   //ajax  호출시 default 비동기 호출
   //ajax가 통신 성공 후 json을 리턴해주면 서버가 자동으로 자바오브젝트로 변환
      $.ajax({
          type:"POST",
          url:"/api/board",
          data:JSON.stringify(data),     //http body 데이터
          contentType: "application/json; charset=utf-8",
          dataType:"json"               //요청을 서버로해서 응답이 왔을때 기본적으로 모든 것이 문자열 (생긴게 json이라면) 
        }).done(function(resp){
          alert("글쓰기 완료되었습니다,");
          console.log(resp);
          location.href = "/";
        }).fail(function(error){
          alert(JSON.stringify(error));
        }); 
    },
    
  //////////삭제
     deleteById: function(){
        let  id = $("#id").text();

      $.ajax({
          type:"DELETE",
          url:"/api/board/"+id,
          dataType:"json"               //요청을 서버로해서 응답이 왔을때 기본적으로 모든 것이 문자열 (생긴게 json이라면) 
        }).done(function(resp){
          alert("삭제가 완료되었습니다.");
          location.href = "/";
        }).fail(function(error){
          alert(JSON.stringify(error));
        }); 
    },
    
     //////////수정 
      update:function(){
      let id = $("#id").val();
      
      let data = {
        title:$("#title").val(),
        content: $("#content").val()
      };
     
     $.ajax({
          type:"PUT",
          url:"/api/board/"+id,
          data:JSON.stringify(data),     
          contentType: "application/json; charset=utf-8",
          dataType:"json"            
        }).done(function(resp){
          alert("수정이 완료되었습니다.");
          console.log(resp);
          location.href = "/";
        }).fail(function(error){
          alert(JSON.stringify(error));
        }); 
    },
    
    replySave:function(){
      let data = {
        userId : $("#userId").val(),
      boardId : $("#boardId").val(),
      content: $("#reply-content").val()
      };
      
      let boardId = $("#boardId").val();
  
  console.log(data);
      $.ajax({
          type:"POST",
          url:`/api/board/${data.boardId}/reply`,
          data:JSON.stringify(data),    
          contentType: "application/json; charset=utf-8",
          dataType:"json" 
        }).done(function(resp){
          alert("댓글작성 완료");
          console.log(resp);
          location.href = `/board/${data.boardId}`;
        }).fail(function(error){
          alert(JSON.stringify(error));
        }); 
    },

  
  } 
    

index.init();