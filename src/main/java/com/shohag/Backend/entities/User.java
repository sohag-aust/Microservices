package com.shohag.Backend.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = "user")
public class User implements UserDetails { // If i don't want to implement all the method of UserDetails interface
                                            // important doc-1: https://www.geeksforgeeks.org/different-ways-to-prevent-method-overriding-in-java/
                                            // important doc-2: https://stackoverflow.com/questions/11437097/not-implementing-all-of-the-methods-of-interface-is-it-possible

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false) // column name inside the db table
    private String name;
    private String email;
    private String password;
    private String about;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Post> posts;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), // joinColumns refers first column of one entity, referencedColumnName refers the foreign key, here id refers user table primary key
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") // inverseJoinColumns refers second column of another entity, referencedColumnName refers the foreign key, here id refers role table primary key
    )
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
