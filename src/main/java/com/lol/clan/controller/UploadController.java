package com.lol.clan.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lol.clan.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
	String uploadFolder = "C:\\upload";
	
	@GetMapping("/uploadForm")
	public void uploadForm() {
		
		log.info("upload form");
	}

	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("-----------------------------");
			log.info("Upload File Name: "+multipartFile.getOriginalFilename());
			log.info("Upload File Size: "+multipartFile.getSize());
			
			
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile);
				
			}catch(Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		
		log.info("upload ajax");
	}
	
	
	@PostMapping(value="/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	
	//메소드에서 리턴되는 값은 View 를 통해서 출력되지 않고 HTTP Response Body 에 직접 쓰여
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		
		log.info("update ajax post..........");
		
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "C:\\upload";
		
		String uploadFolderPath = getFolder();
		
		//mkdir
		File uploadPath = new File(uploadFolder,uploadFolderPath);
		log.info("upload path: "+uploadPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			log.info("-----------------------------");
			log.info("Upload File Name: "+multipartFile.getOriginalFilename());
			log.info("Upload File Size: "+multipartFile.getSize());
			
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			//IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			log.info("only file name: " + uploadFileName);
			attachDTO.setFileName(uploadFileName);
			
			
			//중복방지를 위한 UUID 적용
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString()+"_"+uploadFileName;
			
			
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				
				//check image type file
				if(checkImageType(saveFile)) {
					
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(),thumbnail,100,100);
					
					thumbnail.close();
				}
				
				log.info(attachDTO);
				list.add(attachDTO);
				
			}catch(Exception e) {
				log.error(e.getMessage());
			}

		}
		
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	
	
	//이미지 데이터 전송
	@GetMapping("/display")
	public ResponseEntity<byte[]> getFile(String fileName){
		
		log.info("fileName : " + fileName);
		
		File file = new File("c:\\upload\\"+fileName);
		
		log.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	//파일 다운로드
	//produces : 현재 적용속성은 다운로드 할 수 있는 미디어타입
	//User-Agent : 사용자의 브라우저 정보를 가져옴 ---> IE 한글 꺠짐 방지
	@GetMapping(value="/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	
	//메소드에서 리턴되는 값은 View 를 통해서 출력되지 않고 HTTP Response Body 에 직접 쓰여
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName){
		
		log.info("download file: "+fileName);
		
		Resource resource = new FileSystemResource("c:\\upload\\"+fileName);
		
		if(resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		log.info("resource: "+resource);
		
		String resourceName = resource.getFilename();
		
		//remove UUID
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);
		
		
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			
			String downloadName = null;
			
			log.info("browser type : "+userAgent);
			
			//다운로드 명 : 브라우저 종류에 따라 깨짐
			if(userAgent.contains("Tridnet")) {
				//IE Version Check 필요
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8").replaceAll("\\+", " ");
				
			}else if(userAgent.contains("Edge")) {
				
				log.info("Edge browser");
				
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
				
				log.info("Edge name: "+downloadName);
			}else {
				
				log.info("Chrome browser");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			}
			
			
			
			//다운로드시 저장되는 이름 지정
			headers.add("Content-Disposition", "attachment; filename=" + downloadName); 
			
			
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		
		log.info("deleteFile: "+fileName);
		
		File file;
		
		try {
			file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
			
			file.delete();
			
			if(type.contentEquals("image")) {
				
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				
				log.info("largeFileName: " + largeFileName);
				
				file = new File(largeFileName);
				
				file.delete();
			}
		}catch(UnsupportedEncodingException e) {
			
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
		
		return new ResponseEntity<String>("deleted",HttpStatus.OK);
	}
	
	
	//중복된 이름의 첨부파일 처리
	private String getFolder() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
	}
	
	
	
	//이미지 파일의 판단
	private boolean checkImageType(File file) {
		
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			return contentType.startsWith("image");
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
