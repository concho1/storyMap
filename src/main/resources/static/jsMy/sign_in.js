let emailSendFlag = false
let emailSending = false;

function checkPasswordValidity() {
    let password = $('#pwInput').val();
    let password2 = $('#pwInput2').val();

    // 비밀번호 길이 검증
    if(password.length >= 5 && password.length <= 20) {
        $('#pw_length_status').html('<small style="color: green;">유효한 비밀번호입니다.</small>');
    } else {
        $('#pw_length_status').html('<small style="color: red;">비밀번호는 5자리 이상 20자리 이하이어야 합니다.</small>');
    }

    // 두 비밀번호 일치 검증
    if(password && password2 && password === password2) {
        $('#pw_match_status').html('<small style="color: green;">비밀번호가 일치합니다.</small>');
    } else if (password2.length > 0) {
        $('#pw_match_status').html('<small style="color: red;">비밀번호가 일치하지 않습니다.</small>');
    } else {
        $('#pw_match_status').html('');
    }
}

// 비밀번호 입력 필드에 입력이 있을 때마다 검증 함수 호출
$('#pwInput, #pwInput2').on('input', checkPasswordValidity);

// 비밀번호 보기 토글
$('#pw_show').click(function(e) {
    e.preventDefault();
    var type = $('#pwInput').attr('type') === 'password' ? 'text' : 'password';
    $('#pwInput, #pwInput2').attr('type', type);
    $(this).find('small').text(function(i, text) {
        return text === '비밀번호 보기' ? '비밀번호 숨기기' : '비밀번호 보기';
    });
});


$('#id_check').click(() => {
    let email = $('#email_input').val();
    if(email != null){
        sendEmail(email);
    }else{
        alert('이메일이 비었습니다.');
    }
});


function checkNickNameDuplication(){
    let result = false;
    let nickname = $('#nickname').val();
    $.ajax({
        url : '/user/nickname-duplication',
        type : 'POST',
        async: false,
        data: {
            nickname : nickname
        },
        success:(response) =>{
            if(response){
                result = true;
            }
            console.log(response);
        } ,
        error : (error) => {
            alert('오류가 발생했습니다.');
        }
    })
    return result;
}


function checkEmailDuplication(email) {
    let result = false;
    $.ajax({
        url  : '/user/email-duplication',
        type : 'POST',
        contentType : 'application/json',
        async : false,
        data : JSON.stringify({
            email : email
        }),
        success : (responseData) => {
            if(responseData.result ) {
                result = true;
            }
        },
        error : (error) => {
            alert('오류가 발생했습니다.');
        }
    });
    return result;
}

function sendEmail(email) {
    emailSendFlag = true;
    if(emailSending){
        alert('이메일을 보내는 중입니다.');
    }else{
        emailSending = true;
        $.ajax({
            url  : '/user/email-send',
            type : 'POST',
            async : true,
            data : {
                email : email
            },
            success : (responseData) => {
                emailSending = false;
                if(responseData) {
                    alert('이메일 전송 완료');
                }else{
                    alert('이메일 전송 실패');
                }
            },
            error : (error) => {
                emailSending = false;
                alert('오류가 발생했습니다.');
            }
        });
    }

}
function checkTokenByEmail(email){
    let result = false;
    $.ajax({
        url: '/user/token-check',
        type: 'post',
        async: false,
        data : {
            email : email
        },
        success : (responseData) => {
            if(responseData){
                result = true;
            }else{
                result = false;
            }
        },
        error: (error) => {
            alert(error);
            result = false;
        }
    });
    return result;
}

let userResponse = false;
$('#signupForm').submit(function(event) {
    // 폼 제출을 일시적으로 중단
    event.preventDefault();

    if(!emailSendFlag){
        alert('이메일 인증을 해주세요!');
        return false;
    }
    let email = $('#email_input').val();
    if(email != null){
        let resultOut3 = checkTokenByEmail(email);
        if(!resultOut3){
            alert('이메일 인증을 완료해주세요!');
            return false;
        }
        let resultOut1 = checkEmailDuplication(email);
        let resultOut2 = checkNickNameDuplication();

        if(resultOut1 && resultOut2){
            userResponse = true;
        }else if(resultOut1) {
            alert('닉네임이 이미 사용중 입니다 ㅠㅠㅠ');
        }else if(resultOut2) {
            alert('이메일이 이미 사용중 입니다 ㅠㅠㅠ');
        }else{
            alert('이메일과 닉네임이 이미 사용중 입니다 ㅠㅠㅠ');
        }
    }else{
        alert('이메일이 비었습니다.');
    }

    if (userResponse) {
        // 사용자가 '예'를 선택한 경우, 폼 제출
        this.submit();
    }
    // 사용자가 '아니오'를 선택한 경우, 아무것도 하지 않음
});