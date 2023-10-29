package itacademy.s5t2.diceGame.securityLayer.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.media.Schema;
import itacademy.s5t2.diceGame.securityLayer.dto.RegisterUserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
//import org.springframework.data.annotation.Id;		//THIS MOTHERFUCKER IS WHAT WAS CAUSING THE BEAN FACTORY ERROR!!!!
import jakarta.persistence.Id;							//this is correct method for id annotation
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Details of User object")
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails {

	//@NotNull
	@Schema(description = "Unique id of the User for Database", name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	
	/*@NotNull
	@Schema(description = "Unique id of the User for Security implementation", name="serialVersionUID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private static final long serialVersionUID = 1L;*/

	@Schema(description = "User name", name="username")
    @NotNull
    private String username;
    
	@Schema(description = "User password", name="password")
    @NotNull
    private String password;
    
	@Schema(description = "If account is enabled or not", name="enabled")
   // @NotNull
    private boolean enabled;
    
	@Schema(description = "Role given to account", name="enabled")
    //@NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
    
    /* @ManyToMany(fetch=FetchType.LAZY) if re implement, create role class
    @JoinTable(
        name="users_roles",
        joinColumns= {@JoinColumn(name="user_id")},
        inverseJoinColumns = {@JoinColumn(name="role_id")}
    )*/
    
    @Override //if implment userdetails interace on this class
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority((role.name())));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}
