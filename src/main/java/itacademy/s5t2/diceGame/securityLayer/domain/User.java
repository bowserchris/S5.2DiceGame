package itacademy.s5t2.diceGame.securityLayer.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
//import org.springframework.data.annotation.Id;		//THIS MOTHERFUCKER IS WHAT WAS CAUSING THE BEAN FACTORY ERROR!!!!
import jakarta.persistence.Id;							//this is correct method for id annotation
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Details of User object")
@Entity(name = "User")
@Table(name = "_user")
//uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails {

	//@NotNull
	@Schema(description = "Unique id of the User for Database", name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", updatable = false)
	private int userId;
	
	/*@NotNull
	@Schema(description = "Unique id of the User for Security implementation", name="serialVersionUID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private static final long serialVersionUID = 1L;*/

	@NotNull
	@Schema(description = "User name", name="username")
    @Column(name = "user_name", nullable = false)
    private String username;
    
	@NotNull
	@Schema(description = "User password", name="password")
	@Hidden
    @Column(name = "password", nullable = false)
    private String password;
    
	@Schema(description = "If account is enabled or not", name="enabled")
    private boolean enabled;
    
	@Schema(description = "Role given to account", name="enabled")
	@Column(name = "role", nullable = false)
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
