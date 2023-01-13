package asia.huayu.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/1/11
 */
@Data
public class SecurityUser implements UserDetails {
    /**
     * 当前登录用户
     */
    private transient SecurityUserInfo securityUserInfo;
    /**
     * 当前用户权限
     */
    private List<String> permissionValueList;


    public SecurityUser() {
    }

    public SecurityUser(SecurityUserInfo user) {
        if (user != null) {
            this.securityUserInfo = user;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String permissionValue : permissionValueList) {
            if (StringUtils.isEmpty(permissionValue)) continue;
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
            authorities.add(authority);
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return securityUserInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return securityUserInfo.getUsername();
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
