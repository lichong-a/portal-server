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
@EqualsAndHashCode(callSuper = false, of = {"id", "authorityKey"})
@ToString(callSuper = true, exclude = {"roles"})
@Table(name = "tb_basic_authority")
@Comment("权限表")
@Schema(description = "权限")
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BasicAuthority extends BaseEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("权限ID")
    @Schema(description = "权限ID")
    private Long id;

    @Column(nullable = false, length = 100)
    @Comment("权限名称")
    @Schema(description = "权限名称")
    private String authorityName;

    @Column(nullable = false, length = 100, unique = true)
    @Comment("权限标识")
    @Schema(description = "权限标识")
    private String authorityKey;

    @Column(length = 500)
    @Comment("权限描述")
    @Schema(description = "权限描述")
    private String description;

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

}
