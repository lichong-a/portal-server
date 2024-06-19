/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.domain.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@Table(name = "tb_basic_authority")
public class BasicAuthority extends BaseEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String authorityName;

    @Column(nullable = false, length = 100)
    private String authorityKey;

    @Column(length = 500)
    private String description;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "basicAuthorities")
    private Set<User> users;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "basicAuthorities")
    private Set<Role> roles;

    public void setAuthorityKey(String authorityKey) {
        this.authorityKey = authorityKey.toUpperCase();
    }

    @Override
    public String getAuthority() {
        return this.authorityKey;
    }
}
