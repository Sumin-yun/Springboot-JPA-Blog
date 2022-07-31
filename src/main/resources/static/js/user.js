let index = {
    init: function() {
      $("#btn-save").on("click", ()=> {
        this.save();
      });
    },
    
    save:function(){
      alert("알리로즈~~~");
      let data = {
          username : $("#username").val(),
          password: $("#password").val(),
          email:$("#email").val()
      }
   // console.log(data);
      $.ajax().data().fail()    //ajax 통신을 이용해 3개의 파라미터를 json으로 변경하여 insert 요
    }
  } 
    

index.init();