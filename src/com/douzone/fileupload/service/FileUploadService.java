package com.douzone.fileupload.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	private static final String SAVA_PATH = "/uploads";
	private static final String URL = "/images";

	public String restore(MultipartFile multipartFile) {
		String url = "";
		
		try {
			if (multipartFile.isEmpty()) {
				return url;
			}

			String originalFileName = multipartFile.getOriginalFilename();			
			// 확장자..
			// 확장자를 가져오기 위해서 뒤에서부터 check
			String extName = originalFileName.substring(originalFileName.lastIndexOf('.') + 1); 
			
			// file의 originName과 저장될 이름은 다르다.
			// customizing..
			String saveFileName = generalSaveFileName(extName);
			long fileSize = multipartFile.getSize();

			System.out.println("##########" + originalFileName);
			System.out.println("##########" + extName);
			System.out.println("##########" + saveFileName);
			System.out.println("##########" + fileSize);
			
			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVA_PATH + "/" + saveFileName);

			os.write(fileData);
			os.close();

			url = URL + "/" + saveFileName;
			
		} catch (FileNotFoundException e) {
			new RuntimeException("upload fail");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return url;
	}

	private String generalSaveFileName(String extName) {
		String fileName = "";
		Calendar calendar = Calendar.getInstance();

		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += ("." + extName);

		return fileName;
	}

}