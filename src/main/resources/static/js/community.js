function post() {
    var questionId = $("#question_id").val();
    var questionText = $("#comment_textarea").val();
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json;charset=UTF-8',
        data: {
            "parent_id": questionId,
            "content": questionText,
            "type": 1
        },
        dataType:"json",
        success:function (response) {
            console.log(response)
        }
    });

}