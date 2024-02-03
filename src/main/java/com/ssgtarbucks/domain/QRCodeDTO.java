package com.ssgtarbucks.domain;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Alias("QRCodeDTO")
public class QRCodeDTO {
	int qrcode_id;
	byte[] qrcode_code;
	String qrcode_path;
	Date qrcode_date;
	String qrcode_type;
	String qrcode_link;
	String qrcode_value;
	String branch_id;
}
