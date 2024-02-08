package com.ilabs.AuthService;

import com.ilabs.AuthService.entity.Role;
import com.ilabs.AuthService.entity.User;
import com.ilabs.AuthService.repository.RoleRepository;
import com.ilabs.AuthService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		long roleCount = roleRepository.count();
		if (roleCount == 0) {
			roleRepository.save(new Role("ROLE_ADMIN"));
			roleRepository.save(new Role("ROLE_CUSTOMER"));

		}
		User userEmailIdExists = userRepository.findByEmailId("admin@gmail.com");
		if (ObjectUtils.isEmpty(userEmailIdExists)) {
			userRepository.save(saveDefaultAdmin());
		}
	}
	private User saveDefaultAdmin() {
		User user = new User();
		Role role = roleRepository.findByName("ROLE_ADMIN");
		user.setName("Admin");
		user.setEmailId("admin@gmail.com");
		user.setPassword(passwordEncoder.encode("Admin@123"));
		user.addRole(new Role(role.getId()));
		return user;
	}
}


