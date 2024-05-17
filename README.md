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

03/26 :
이미지 갤러리 제작 완료. 
(페이징 처리가 아직은 완벽하진 않음(모든 페이지 넘버를 화면에 띄어놓음) => 이후 사진이 늘어나 페이지가 너무 많아지면 페이지 넘버 처리를 어떻게 수정할지 고민중)
==> 처리 완료 현제 페이지를 기준으로 총 7개 까지 표시( 페이지가 중간이라면 앞에 3개 뒤에 3개 + 현제 페이지)
(페이지가 앞에 치우쳐있거나 하면 앞에 1개 뒤에 5개 식으로 조절함)

![image](https://github.com/concho1/storyMap/assets/142205346/5ee75ea7-845b-4828-8b6b-e3f38eae1309)



03/27 :
스토리 게시판 제작중... DB 테이블 제작 + 글쓰기 페이지 제작
최신글, 인기글, 전체 순으로 정렬 가능하게 할 예정.
검색 기능은 넣을까 말까 고민중...
```
CREATE TABLE forum_table
(
    forum_id INT AUTO_INCREMENT PRIMARY KEY, -- 게시글의 고유 식별자
    forum_title VARCHAR(255) NOT NULL, -- 게시글 제목
    forum_content TEXT NOT NULL, -- 게시글 내용
    forum_author_email VARCHAR(100) NOT NULL, -- 작성자
    forum_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 작성 날짜 및 시간
    forum_updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 마지막 수정 날짜 및 시간
    forum_view_count INT DEFAULT 0, -- 조회수
    forum_comment_count INT DEFAULT 0, -- 댓글 수
    forum_like_count INT DEFAULT 0 -- 좋아요 수
);
```
일단 이렇게 제작하고 댓글 처리도 구현 예정, 대댓글 까지는 구현하지 않을 예정.

04/01 : 
1. 게시판 제작 완료.
2. 댓글 기능 구현 환료.
3. 대댓글 기능 구현 완료.(대댓글의 깊이는 최대3까지)
4. 게시판 검색 기능과 게시판 최신순, 인기순 정렬 구현 해야함
5. 페이징 처리 스무스하게 완료.

결국 대댓글까지 구현완료
04/02 : 현제 같은 수강생들에게 test받는중.... 와이파이 ip를통해 접속

04/04 : 
1. 이메일 인증(링크 방식), 비밀번호 검증 구현 완료.
    1) 사용자가 이메일 인증을 시도 => 서버에서 토큰을 발급후 사용자의 이메일로 토큰 인증 링크를 보냄
    2) 사용자가 이메일 링크를 들어가면 서버는 해당 링크의 토큰값(토큰 유효기간 15분)과 이메일의 검증을 거침
    3) 검증이 끝나면 해당 이메일의 검증 여부를 DB에 업데이트 해줌
    4) 사용자가 회원가입완료를 누르면 이메일의 검증 여부를 확인하고 DB에 회원을 추가
![image](https://github.com/concho1/storyMap/assets/142205346/bb374a87-066c-4c24-9a15-a50349b63acf)
![image](https://github.com/concho1/storyMap/assets/142205346/2ac63416-0a84-4922-9bd7-2570a0445185)
![image](https://github.com/concho1/storyMap/assets/142205346/02da5f12-13f2-4ee5-b4e9-e9ae690b702b)
![image](https://github.com/concho1/storyMap/assets/142205346/3b0da441-89ed-40bf-b607-7d5245aed09a)



배포 완료!!!!!!!!!!!!!!!!!!!
배포 완료!!!!!!!!!!!!!!!!!!!
