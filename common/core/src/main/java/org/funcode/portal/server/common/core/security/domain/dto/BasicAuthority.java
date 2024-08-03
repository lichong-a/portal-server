/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.funcode.portal.server.common.core.base.entity.BaseEntity;
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
@EqualsAndHashCode(callSuper = true, exclude = {"roles", "users"})
@ToString(callSuper = true, exclude = {"roles", "users"})
@Table(name = "tb_basic_authority")
@Comment("权限表")
@DynamicUpdate
public class BasicAuthority extends BaseEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("权限ID")
    private Long id;

    @Column(nullable = false, length = 100)
    @Comment("权限名称")
    private String authorityName;

    @Column(nullable = false, length = 100, unique = true)
    @Comment("权限标识")
    private String authorityKey;

    @Column(length = 500)
    @Comment("权限描述")
    private String description;

    @ManyToMany(mappedBy = "basicAuthorities")
    @JsonIgnore
    private Set<User> users;

    @ManyToMany(mappedBy = "basicAuthorities")
    @JsonIgnore
    private Set<Role> roles;

    public void setAuthorityKey(String authorityKey) {
        this.authorityKey = authorityKey.toUpperCase();
    }

    @Override
    public String getAuthority() {
        return this.authorityKey;
    }

    public static final class ColumnName {
        public static final String ID = "id";
        public static final String AUTHORITY_NAME = "authority_name";
        public static final String AUTHORITY_KEY = "authority_key";
        public static final String DESCRIPTION = "description";
    }
}
