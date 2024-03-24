$('#id_check').click(() => {
    let email = $('#email_input').val();
    if(email != null){
        checkEmailDuplication(email);
    }else{
        alert('이메일이 비었습니다.')
    }
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
            if(responseData.result) {
                alert('사용 가능한 이메일 입니다.');
            }else{
                alert('이미 사용중인 이메일 입니다.');
            }
        },
        error : (error) => {
            alert('오류가 발생했습니다.');
        }
    });
}