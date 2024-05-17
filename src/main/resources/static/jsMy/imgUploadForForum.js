// 파일 입력 필드의 change 이벤트에 이벤트 핸들러를 연결합니다.
$('#fileInput').change(function () {
    let totalFiles = this.files.length;

    if (totalFiles === 1) {
        let file = this.files[0];
        let formData = new FormData();
        formData.append('file', file);

        $.ajax({
            url: '/user/upload-img',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                if(data.result === "sessionFalse"){
                    alert("세션이 만료되었습니다. 다시 로그인 해주세요!");
                } else if(data.result === "fileEmpty"){
                    alert("파일이 비었습니다!");
                } else if (data.result === "true"|| data.result === "noGPS") {
                    $('#imgPath').val(data.path);
                    $('#fileKey').val(data.fileKey);
                    $('#selectImg').attr('src',data.path);
                    alert("파일 업로드 성공!");
                } else if(data.result === "imgMax"){
                    alert("이미지 업로드 할당량 초과");
                } else {
                    alert("파일 업로드 실패. 다시 시도해주세요.");
                }
            },
            error: function () {
                alert('파일 업로드 중 오류가 발생했습니다.');
            }
        });
    } else if (totalFiles > 1) {
        alert("한 번에 하나의 이미지만 업로드할 수 있습니다. 다시 선택해 주세요!");
    } else {
        alert("파일을 선택해 주세요!");
    }
});
