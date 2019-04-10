/**
 * 
 */
package main.com.zc.security.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import main.com.zc.loginNeeds.UserData;
import main.com.zc.loginNeeds.UserDataRepository;

/**
 * @author Omnya Alaa
 * 
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	/*
	 * Mock for users from database. In the real application users will be
	 * retrieved from database and proper Spring UserDetails object will be
	 * created then for each database user.
	 */

	/*
	 * @Autowired IUserRepository userRep;
	 */
	
	@Autowired
	UserDataRepository userDataRepository;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		try {

			UserData dao = userDataRepository.getByEmail(username);
			UserDetails user;
			if(dao.getEmail().contains("lts-admin@zewailcity.edu.eg")) {
				Collection<GrantedAuthority> studentAuthorities = new ArrayList<GrantedAuthority>();
				studentAuthorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
				user = new User(dao.getEmail(), dao.getPassword(), true,
						true, true, true, studentAuthorities);
			}else {
				Collection<GrantedAuthority> studentAuthorities = new ArrayList<GrantedAuthority>();
				studentAuthorities.add(new GrantedAuthorityImpl("ROLE_USER"));
				user = new User(dao.getEmail(), dao.getPassword(), true,
						true, true, true, studentAuthorities);
			}
			return user;
		} catch (IndexOutOfBoundsException ex) {
			throw new UsernameNotFoundException("UserAccount for name \""
					+ username + "\" not found.");
		}

		
		
	}

}
