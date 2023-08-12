package com.springboot.blog.payload;



import com.springboot.blog.entity.Account;
import com.springboot.blog.entity.User;

import lombok.Data;

@Data
public class AccountDto {
	private long id;
	private User user;
	private String accountName;
	private long balance;
	
	
	public User getUser() {
		return user;
	}
	
	public void setAccountDtoForTransfer(Account account) {
		this.setAccountName(account.getAccountName());
		this.setBalance(account.getBalance());
		this.setId(account.getId());
		this.setUser(account.getUser());
	}
}





