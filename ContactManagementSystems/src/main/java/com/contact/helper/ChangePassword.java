package com.contact.helper;

public class ChangePassword 
{
	private String oldpass;
	private String newpass;
	private String retypenewpass;
	
	public ChangePassword()
	{
		super();
	}

	public ChangePassword(String oldpass, String newpass, String retypenewpass) 
	{
		super();
		this.oldpass = oldpass;
		this.newpass = newpass;
		this.retypenewpass = retypenewpass;
	}

	public String getOldpass() {
		return oldpass;
	}

	public void setOldpass(String oldpass) {
		this.oldpass = oldpass;
	}

	public String getNewpass() {
		return newpass;
	}

	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}

	public String getRetypenewpass() {
		return retypenewpass;
	}

	public void setRetypenewpass(String retypenewpass) {
		this.retypenewpass = retypenewpass;
	}

	@Override
	public String toString() {
		return "ChangePassword [oldpass=" + oldpass + ", newpass=" + newpass + ", retypenewpass=" + retypenewpass + "]";
	}
	
	
	
	
	
	
	
}
