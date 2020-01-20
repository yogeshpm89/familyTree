package com.family.tree.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.family.tree.entity.AppUser;
import com.family.tree.repository.AppUserRepository;

@Service("DefaultUserDetailsService")
@Transactional
public class DefaultUserDetailsService implements UserDetailsService {

	@Autowired
    private final AppUserRepository appUserRepository;
	
	public final String Test = "Yogesh";

    public DefaultUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	username = username.replaceAll(" ", "+");
        final Optional<AppUser> userEntity = appUserRepository.findById(username);

        if (userEntity.isPresent()) {	
            final AppUser appUser = userEntity.get();

            return new User(appUser.getUserEmail(),
                    passwordNoEncoding(appUser),
                    Collections.singletonList(new SimpleGrantedAuthority(appUser.getUserRole())));
        }

        return null;
    }

    
    
    private String passwordNoEncoding(AppUser appUser) {
        // you can use one of bcrypt/noop/pbkdf2/scrypt/sha256
        // more: https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-encoding
        return  appUser.getUserPass();
    }
}
