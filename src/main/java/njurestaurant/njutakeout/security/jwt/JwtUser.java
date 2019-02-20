package njurestaurant.njutakeout.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class JwtUser implements UserDetails {
    private final String username;
    private final String password;
    private final int uid;
    private final int tableId;
    private final int role;
    private final Collection<JwtRole> authorities;
    private final boolean accountNonExpired = true;
    private final boolean accountNonLocked = true;
    private final boolean credentialsNonExpired = true;
    private final boolean enabled = true;

    public JwtUser(String username, String password, int uid, int tableId, int role) {
        this.username = username;
        this.password = password;
        this.tableId = tableId;
        this.role = role;
        this.uid = uid;
        this.authorities = new ArrayList<>();
    }

    //    public JwtUser(String username, String password, Collection<JwtRole> authorities) {
//        this.username = username;
//        this.password = password;
//        this.authorities = authorities;
//    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<JwtRole> getAuthorities() {
        return authorities;
    }
}
