package itacademy.s5t2.diceGame.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import itacademy.s5t2.diceGame.domain.User;
import itacademy.s5t2.diceGame.domain.UserPrincipal;

public class UserMapper {
	
	public static UserPrincipal userToPrincipal(User user) {
        UserPrincipal userP = new UserPrincipal();
        List<SimpleGrantedAuthority> authorities = user.getRoles()
        					.stream()
        					.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
        					.collect(Collectors.toList());
        userP.setUsername(user.getUsername());
        userP.setPassword(user.getPassword());
        userP.setEnabled(user.isEnabled());
        userP.setAuthorities(authorities);
        return userP;
    }

}
