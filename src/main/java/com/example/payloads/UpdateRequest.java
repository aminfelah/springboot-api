
package com.example.payloads;




import javax.validation.constraints.*;
public class UpdateRequest {
	
	 
	    @NotBlank
	    @Size(max = 50)
	    @Email
	    private String email;
	  
	    @NotBlank
	    @Size(min = 6, max = 40)
	    private String phoneNumber;
	    
	    @NotBlank
	    private int age;
	  
	    public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}


	    public String getEmail() {
	        return email;
	    }
	 
	    public void setEmail(String email) {
	        this.email = email;
	    }
	 
	    
	
}