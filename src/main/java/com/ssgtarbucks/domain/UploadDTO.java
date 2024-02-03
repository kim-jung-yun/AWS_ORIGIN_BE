package com.ssgtarbucks.domain;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Alias("UploadDTO")
public class UploadDTO {
	
	String theText;
	MultipartFile theFile;

}
