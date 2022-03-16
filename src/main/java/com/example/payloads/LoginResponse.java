package com.example.payloads;




public class LoginResponse {

	private String username;
	private String email;
	private int age ; 
	private String phoneNumber ;

	

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	


	
	
	public LoginResponse(String username, String email,int age ,String phoneNumber) {
		
		this.username = username;
		this.email = email;
		this.age = age ;
		this.phoneNumber = phoneNumber ;
		
	}
	

	


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
}