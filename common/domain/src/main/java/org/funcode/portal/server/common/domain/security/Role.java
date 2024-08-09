/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.funcode.portal.server.common.domain.base.BaseEntity;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
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
@EqualsAndHashCode(callSuper = true, exclude = {"users", "basicAuthorities"})
@ToString(callSuper = true, exclude = {"users", "basicAuthorities"})
@Table(name = "tb_role")
@Comment("角色表")
@DynamicUpdate
public class Role extends BaseEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("角色ID")
    private Long id;

    @Column(nullable = false, length = 100)
    @Comment("角色名称")
    private String roleName;

    @Column(nullable = false, length = 100, unique = true)
    @Comment("角色标识")
    private String roleKey;

    @Column(length = 500)
    @Comment("角色描述")
    private String description;

    @ManyToMany(targetEntity = BasicAuthority.class)
    @JoinTable(
            name = "tb_role_basic_authority",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "basic_authority_id"))
    @JsonIgnore
    private Set<BasicAuthority> basicAuthorities;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;

    public void setRoleKey(String roleKey) {
        if (roleKey.toUpperCase().startsWith("ROLE_")) {
            this.roleKey = roleKey.toUpperCase();
        } else {
            this.roleKey = "ROLE_" + roleKey.toUpperCase();
        }
    }

    public String getRoleKey() {
        if (roleKey.toUpperCase().startsWith("ROLE_")) {
            return roleKey.toUpperCase();
        }
        return "ROLE_" + roleKey.toUpperCase();
    }

    @Override
    public String getAuthority() {
        return this.roleKey;
    }

}
