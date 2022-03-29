package partFour.youn.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import partFour.youn.dto.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${partFour.upload.path}")
    private String uploadPath;


    @PostMapping("/uploadAjax")
    public  ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){

        List<UploadResultDTO> resultDTOList = new ArrayList<>();


        for (MultipartFile uploadFile : uploadFiles) {

            if(uploadFile.getContentType().startsWith("image")== false){
                log.warn("this is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            System.out.println("fileName = " + fileName);

            //날짜 폴더생성
            String folderPath = makeFolder();

            //UUID
            String uuid = UUID.randomUUID().toString();

            //저장할 파일 이름 중간에 "_"를 이용해서 구분
            String saveName = uploadPath + File.separator+folderPath+File.separator+uuid+"_"+fileName;

            Path savePath = Paths.get(saveName);//경로 정의하기
            try{
                uploadFile.transferTo(savePath);//선택한 파일들을 savePath에 저장합니다.

                String thumbnailSaveName = uploadPath+File.separator+folderPath+File.separator+"s_"+uuid+"_"+fileName;

                File thumbnailFile = new File(thumbnailSaveName);

                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,100,100);
                resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));


            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(resultDTOList,HttpStatus.OK);
    }
    private String makeFolder(){
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath);

        if(uploadPathFolder.exists() == false){
            log.info("mkdirs가 실행되엇습니다.");
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName,String size){

        ResponseEntity<byte[]> result = null;

        try{
            String srcFileName = URLDecoder.decode(fileName,"UTF-8");
            //URL 인코딩된 파일 이름을 디코딩
            log.info("filName : "+srcFileName);
            File file = new File(uploadPath + File.separator + srcFileName);
            if(size != null && size.equals("1")){
                file = new File(file.getParent(),file.getName().substring(2));
            }
            // 해당 URL의 파일을 생성
            log.info("File : "+file);

            HttpHeaders header = new HttpHeaders();

            //File객체를 Path로 변환하여 MIME 타입을 판단하여 HTTPHeaders의 Content-Type에  값으로 들어갑니다.
            header.add("Content-Type",Files.probeContentType(file.toPath()));

            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  result;
    }
    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){

        String srcFileName = null;

        try{
            srcFileName = URLDecoder.decode(fileName,"UTF-8");
            File file = new File(uploadPath+ File.separator+srcFileName);
            boolean result = file.delete();

            log.info("getParent(): "+file.getParent());
            log.info("getName(): "+file.getName());

            File thumbnail = new File(file.getParent(),"s_"+file.getName());

            result = thumbnail.delete();

            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
