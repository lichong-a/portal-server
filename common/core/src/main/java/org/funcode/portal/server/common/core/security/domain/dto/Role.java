/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.domain.dto;

import jakarta.persistence.*;
import lombok.*;
import org.funcode.portal.server.common.core.base.entity.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

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
@Table(name="tb_role")
public class Role extends BaseEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String roleName;

    @Column(nullable = false, length = 100, unique = true)
    private String roleKey;

    @Column(length = 500)
    private String description;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_role_basic_authority",
            joinColumns = {
                    @JoinColumn(name = "role_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "basic_authority_id")
            })
    private Set<BasicAuthority> basicAuthorities;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<User> users;

    public void setRoleKey(String roleKey) {
        if (roleKey.toUpperCase().startsWith("ROLE_")) {
            this.roleKey = roleKey.toUpperCase();
        } else {
            this.roleKey = "ROLE_" + roleKey.toUpperCase();
        }
    }

    @Override
    public String getAuthority() {
        return this.roleKey;
    }

    public static final class ColumnName {
        public static final String ID = "id";
        public static final String ROLE_NAME = "role_name";
        public static final String ROLE_KEY = "role_key";
        public static final String DESCRIPTION = "description";
    }
}