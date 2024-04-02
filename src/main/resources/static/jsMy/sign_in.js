$('#id_check').click(() => {
    let email = $('#email_input').val();
    if(email != null){
        checkEmailDuplication(email);
    }else{
        alert('이메일이 비었습니다.')
    }
});

$('#pw_show').click(function (){
    let pwInput = $(this).parent().siblings('#pwInput');
    pwInput.prop('type', 'text');
    /*
    if(pwInput.type === "password"){

    }else{
        pwInput.prop('type', 'password');
    }
    */
});


function checkEmailDuplication(email) {
    $.ajax({
        url  : '/user/email-duplication',
        type : 'POST',
        contentType : 'application/json',
        data : JSON.stringify({
            email : email
        }),
        success : (responseData) => {
            if(responseData.result ) {
                alert('사용 가능한 이메일 입니다!');
            }else{
                alert('이미 사용중인 이메일 입니다 ㅠㅠㅠ');
            }
        },
        error : (error) => {
            alert('오류가 발생했습니다.');
        }
    });
}