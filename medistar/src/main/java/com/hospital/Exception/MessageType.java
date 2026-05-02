package com.hospital.Exception;

import lombok.Getter;

@Getter
public enum MessageType {
	NO_RECORD_EXIST("1000", "kayıt bulunamadı."), 
	USERNAME_NOT_FOUND("1002" , "kullanıcı bilgisi bulunamadı"),
	USERNAME_OR_PASSWORD_INVALID("1003" , "kullanıcı adı veya şifre hatalı"),
	REFRESH_TOKEN_NOT_FOUND("1004" , "refresh token bulunamadı"),
	REFRESH_TOKEN_IS_EXPIRED("1005" , "refresh token'ın süresi bitmiştir"),
	GENERAL_EXCEPTION("9999", "genel bir hata oluştu");

	
	MessageType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	MessageType() {
		// TODO Auto-generated constructor stub
	}

	private String code;

	private String message;

}
