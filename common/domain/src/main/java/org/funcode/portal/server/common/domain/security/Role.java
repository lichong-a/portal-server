/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.funcode.portal.server.common.domain.base.BaseEntity;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
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
@ToString(callSuper = true)
@Table(name = "tb_role",
        indexes = @Index(name = "index_tb_role_role_key", columnList = Role_.ROLE_KEY))
@Comment("角色表")
@Schema(description = "角色")
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role extends BaseEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("角色ID")
    @Schema(description = "角色ID")
    private Long id;

    @Column(nullable = false, length = 100)
    @Comment("角色名称")
    @Schema(description = "角色名称")
    private String roleName;

    @Column(nullable = false, length = 100, unique = true)
    @Comment("角色标识")
    @Schema(description = "角色标识")
    private String roleKey;

    @Column(length = 500)
    @Comment("角色描述")
    @Schema(description = "角色描述")
    private String description;

    @ManyToMany(targetEntity = BasicAuthority.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_role_basic_authority",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "basic_authority_id"))
    @ToString.Exclude
    private Set<BasicAuthority> basicAuthorities;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    @ToString.Exclude
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
        return getRoleKey();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Role role = (Role) o;
        return getId() != null && Objects.equals(getId(), role.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
