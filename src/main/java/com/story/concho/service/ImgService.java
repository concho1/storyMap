package com.story.concho.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.story.concho.model.domain.Img;
import com.story.concho.model.repository.ImgRepository;
import com.story.concho.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ImgService {
    private final UserRepository userRepository;
    private final ImgRepository imgRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    // 의존성 주입
    @Autowired
    public ImgService(UserRepository userRepository, ImgRepository imgRepository){
        this.userRepository = userRepository;
        this.imgRepository = imgRepository;
    }

    public void deleteImg(int id){
        imgRepository.deleteById(id);
    }
    // 이미지 id로 이미지 조회
    public Optional<Img> getImageById(int id){
        return imgRepository.findById(id);
    }

    // 이메일로 이미지 찾기
    public String getImagesJson(String email){
        List<Img> imgList = imgRepository.findImgsByEmail(email);

        String result = "";
        try{
            result = objectMapper.writeValueAsString(imgList);
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public List<Img> getPageNumImages(int pageNum, int size, String email){
        PageRequest pageable = PageRequest.of(pageNum, size);
        Page<Img> imgsPage = imgRepository.findPageImgsByEmail(email,pageable);
        List<Img> imgList = imgsPage.getContent();
        return imgList;
    }

    public int getImgCntByEmail(String email, int size){
        long imgCnt = imgRepository.countByEmail(email);
        int maxPage;

        if(imgCnt % size == 0) maxPage = (int)(imgCnt/size);
        else maxPage = (int) (imgCnt / size) +1;
        System.out.println(maxPage);
        return maxPage;
    }


    public String[] tryImgUpload(MultipartFile multipartFile, String email){
        String[] result = {"false", null};
        String folderId = userRepository.findFolderIdByEmail(email);
        System.out.println("스타트" + folderId + multipartFile.isEmpty());
        // 빈값이 있으면 false 리턴
        if(folderId == null) return new String[] {"false", null};

        String fileName = multipartFile.getOriginalFilename();
        if(fileName == null || fileName.contains("mp4") ){
            return new String[] {"false", null};
        }
        Path savePath   = Paths.get(folderId).resolve(fileName);
        try{
            // 폴더가 없으면 만든다.
            if (Files.notExists(savePath.getParent())) {
                Files.createDirectories(savePath.getParent());
            }
            // 여기에서 메타데이터 추출 시작
            Metadata metadata = ImageMetadataReader.readMetadata(multipartFile.getInputStream());
            GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            // 기존 코드에서 메타데이터를 읽는 부분 뒤에 추가
            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

            GeoLocation geoLocation = null;
            String latitude = "0";
            String longitude = "0";
            if (gpsDirectory != null) {
                // 위도와 경도 추출
                geoLocation = gpsDirectory.getGeoLocation();
            }
            if (geoLocation != null) {
                latitude = String.valueOf(geoLocation.getLatitude());
                longitude = String.valueOf(geoLocation.getLongitude());
            }

            String date;
            // directory 객체가 null인지 먼저 확인
            if (directory != null) {
                Date capturedDate = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

                if (capturedDate != null) {
                    // 촬영 날짜가 있는 경우, 해당 날짜를 문자열로 변환
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    date = dateFormat.format(capturedDate);
                } else {
                    // 촬영 날짜가 없는 경우, 현재 날짜와 시간을 사용
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                }
            } else {
                // directory 객체가 null인 경우, 현재 날짜와 시간을 사용
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            }
            String imgUrlPath = savePath.toString();
            imgUrlPath = imgUrlPath.substring(6).replace('\\','/');
            Img img = new Img(
                    email, latitude, longitude, date, imgUrlPath, fileName
            );
            if(imgRepository.existsByNameAndEmail(fileName, email)){
                System.out.println("파일이 이미 있습니다.");
                result[0] = "true";
                result[1] = imgUrlPath;
            }else{
                imgRepository.save(img);
                // StandardCopyOption.REPLACE_EXISTING => 같은 파일 이름이 있으면 덮어씌운다.
                Files.copy(multipartFile.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println(
                        "이미지 저장 완료 : "+imgUrlPath +
                                " GPS :"+latitude + "  " + longitude
                );
                if(latitude.equals("0") || longitude.equals("0")){
                    result[0] = "noGPS";
                }else{
                    result[0] = "true";
                }
                result[1] = imgUrlPath;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
