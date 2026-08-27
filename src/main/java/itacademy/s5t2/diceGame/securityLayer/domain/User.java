package itacademy.s5t2.diceGame.securityLayer.domain;

import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_ACCOUNT_ON;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_CLASS_USER;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_ROLE;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_USERNAME_PASSWORD;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_USER_ID;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_USER_USERNAME;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_ACCOUNT_ON;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_ROLE;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_USERNAME_PASSWORD;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_USER_ID;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_USER_USERNAME;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import jakarta.validation.constraints.NotNull;

@Schema(description = DESCRIPTION_CLASS_USER)
@Entity(name = "User")
@Table(name = "_user")
//uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails {

	//@NotNull
	@Schema(description = DESCRIPTION_USER_ID, name = NAME_USER_ID)
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
	@Schema(description = DESCRIPTION_USER_USERNAME, name = NAME_USER_USERNAME)
	@Column(name = "user_name", nullable = false)
	private String username;

	@NotNull
	@Schema(description = DESCRIPTION_USERNAME_PASSWORD, name = NAME_USERNAME_PASSWORD)
	@Hidden
	@Column(name = "password", nullable = false)
	private String password;

	@Schema(description = DESCRIPTION_ACCOUNT_ON, name = NAME_ACCOUNT_ON)
	private boolean enabled;

	@Schema(description = DESCRIPTION_ROLE, name = NAME_ROLE)
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
		return List.of(new SimpleGrantedAuthority((this.role.name())));
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

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User(int userId, @NotNull String username, @NotNull String password, boolean enabled, Role role) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
	}

	public User() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(Boolean.valueOf(this.enabled), this.password, this.role, Integer.valueOf(this.userId),
				this.username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		return this.enabled == other.enabled && Objects.equals(this.password, other.password) && this.role == other.role
				&& this.userId == other.userId && Objects.equals(this.username, other.username);
	}

}
