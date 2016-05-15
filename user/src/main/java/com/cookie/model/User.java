package com.cookie.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
public class User {

	@Id
	private String id;

	@NotBlank
	@Size(min=2)
	private String firstName;
	@NotBlank
	@Size(min=2)
	private String lastName;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@Size(min=6)
	@Transient
	private String password;
}
