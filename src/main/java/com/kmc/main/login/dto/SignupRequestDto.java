package com.kmc.main.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
	private String email;
    private String password;
    private String name;
}
