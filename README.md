지도위의 발자취 웹앱 프로젝트
기본 : [Spring Boot, JPA(Java Persistence API), Thymeleaf] 
[Google OAuth 2.0] : Google 인증을 위해 사용
[Google Drive API v3] : 노트북 용량 없어서 임시사용
[gson] : JSON 객체 <-> java 객체
[metadata-extractor] : img metadata추출

02/~  : html, css, js 템플릿 수정+적용 완료.

02/~  : thymeleaf 적용 완료.

02/~  : 회원가입 로직 구현 완료.               

[email 인증, 카카오, 네이버 로그인 구현 필요]

02/~  : 로그인 구현 완료.

02/~  : 비회원 페이지 컨트롤러 제작 완료.

02/~  : 세션 인증 방식의 회원 페이지 컨트롤러 제작 완료.


03/01 : 구글 드라이브 api 연동 완료.


03/04 : 구글 드라이브 api CRUD 중 C, R 구현 완료.

03/04 : 구글 드라이브 api img url => html 이슈 해결, 외부 테스트중 https -> http 포워딩시 파일 업로드 불가 오류 해결

03/05 : 3. sgis api 사용법 알아보기  https://sgis.kostat.go.kr/developer/html/newOpenApi/api/intro.html#2

03/10 : 
구글 드라이브 이미지 url 호스팅 지원 종료(2024.01부터 점차적으로 막는듯 함)
외부 저장소로 변경

03/13 :
구글 드라이브에서 이미지 url 호스팅을 점점 막고있기 때문에 노트북의 C:\static\member_imgs 저장소로 변경
배포할때 쯤 aws s3 서비스 이용예정.

03/14 - 03/21 :
프로젝트 리팩토링 작업 (코드 효율화와 리뷰)

03/24 :
sgis map api 를 이용해 커스텀 마커(이미지)와 info윈도우 생성 추가
마우스를 작게 나타나는 사진에 올리거나 클릭하면 사진이 확대되어 나타남 
![image](https://github.com/concho1/conchoWeb/assets/142205346/1804e90f-25cd-4fd0-90f4-66997312b1dd)


todo list
2. 이미지 업로드시 gps정보 [wgs84 좌표] => utm-k 변환 db 저장 구현하기 ==> sgis api 에서 제공해 줌

