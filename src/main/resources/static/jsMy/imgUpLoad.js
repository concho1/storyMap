$(document).ready(function() {

    $('#uploadButton').click(function (e) {
        e.preventDefault();
        let files = $('#fileInput')[0].files;
        let totalFiles = files.length;
        let uploadedCount = 0;
        let ajaxCnt = 0;
        let noGPS = 0;
        if (totalFiles > 0) {
            $.each(files, function (i, file) {
                let formData = new FormData();
                formData.append('file', file);

                $.ajax({
                    url: '/user/upload-img',
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        ajaxCnt++;
                        if(data.result === "sessionFalse"){
                            alert("세션이 만료되었습니다. 다시 로그인 해주세요!");
                        }else if(data.result === "fileEmpty"){
                            alert(uploadedCount + "번 파일이 비었습니다.!");
                        }else if (data.result === "true" || data.result === "noGPS") {
                            if(data.result === "noGPS"){
                                noGPS++;
                            }
                            uploadedCount++;
                            let progressValue = (uploadedCount / totalFiles) * 100;
                            $('#uploadProgress').val(progressValue);
                            if (uploadedCount >= totalFiles) {
                                console.log("업로드 파일 갯수 : " + uploadedCount);
                                alert(`모든 파일 업로드 성공! GPS 정보 없는 이미지 : ${noGPS}`);
                                location.reload();
                            }else if(ajaxCnt === totalFiles){
                                alert(`파일 업로드 성공 : ${uploadedCount}  GPS 정보 없는 이미지 : ${noGPS}  실패 : ${totalFiles - uploadedCount}`);
                                location.reload();
                            }
                        }else{
                            if(ajaxCnt === totalFiles){
                                alert(`파일 업로드 성공 : ${uploadedCount} 실패 : ${totalFiles - uploadedCount}`);
                                location.reload();
                            }
                        }
                    },
                    error: function () {
                        console.log('error');
                    }
                });
            });
        } else {
            alert("파일을 선택해 주세요!");
        }
    });
});