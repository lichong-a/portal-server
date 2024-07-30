/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.funcode.portal.server.common.core.base.entity.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString
@Table(name = "tb_user")
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(length = 100)
    private String nickName;
    @Column(length = 100)
    private String realName;
    @Column(nullable = false, unique = true, length = 100)
    private String username;
    @Column(length = 100)
    private String gender;
    @Column(unique = true, length = 100)
    private String email;
    @Column(unique = true, length = 100)
    private String phone;
    @JsonIgnore
    @Column(nullable = false, length = 100)
    private String password;
    @Column(length = 300)
    private String avatar;
    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;
    @Column(unique = true, length = 100)
    private String wechatId;
    @Column(nullable = false)
    @Builder.Default
    private boolean accountNonExpired = true;
    @Column(nullable = false)
    @Builder.Default
    private boolean accountNonLocked = true;
    @Column(nullable = false)
    @Builder.Default
    private boolean credentialsNonExpired = true;
    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true;
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            })
    private Set<Role> roles;
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_user_basic_authority",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "basic_authority_id")
            })
    private Set<BasicAuthority> basicAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            result.addAll(roles);
        }
        if (!CollectionUtils.isEmpty(basicAuthorities)) {
            result.addAll(basicAuthorities);
        }
        return result;
    }

    public static final class ColumnName {
        public static final String ID = "id";
        public static final String NICK_NAME = "nick_name";
        public static final String REAL_NAME = "real_name";
        public static final String USERNAME = "username";
        public static final String GENDER = "gender";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
        public static final String PASSWORD = "password";
        public static final String AVATAR = "avatar";
        public static final String BIRTHDAY = "birthday";
        public static final String WECHAT_ID = "wechat_id";
        public static final String ACCOUNT_NON_EXPIRED = "account_non_expired";
        public static final String ACCOUNT_NON_LOCKED = "account_non_locked";
        public static final String CREDENTIALS_NON_EXPIRED = "credentials_non_expired";
        public static final String ENABLED = "enabled";
    }
}
