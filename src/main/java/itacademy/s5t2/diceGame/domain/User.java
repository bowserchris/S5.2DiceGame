package itacademy.s5t2.diceGame.domain;

import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
    @NotNull
    private String username;
    
    @NotNull
    private String password;
    
    @NotNull
    private boolean enabled;
    
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
        name="users_roles",
        joinColumns= {@JoinColumn(name="user_id")},
        inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private List<Role> roles;

}
