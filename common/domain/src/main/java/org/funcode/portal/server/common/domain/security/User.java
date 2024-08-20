/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.funcode.portal.server.common.domain.base.BaseEntity;
import org.funcode.portal.server.common.domain.ielts.Course;
import org.funcode.portal.server.common.domain.ielts.CourseColumn;
import org.funcode.portal.server.common.domain.ielts.Order;
import org.funcode.portal.server.common.domain.ielts.RedeemCode;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
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
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString(callSuper = true)
@Table(name = "tb_user")
@Comment("人员表")
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("人员ID")
    private Long id;
    @Column(length = 100)
    @Comment("昵称")
    private String nickName;
    @Column(length = 100)
    @Comment("真实姓名")
    private String realName;
    @Column(nullable = false, unique = true, length = 100)
    @Comment("用户名")
    private String username;
    @Column(length = 100)
    @Comment("性别")
    private String gender;
    @Column(unique = true, length = 100)
    @Comment("邮箱")
    private String email;
    @Column(unique = true, length = 100)
    @Comment("手机号")
    private String phone;
    @JsonIgnore
    @Column(nullable = false, length = 100)
    @Comment("密码")
    private String password;
    @Column(length = 300)
    @Comment("头像")
    private String avatar;
    @Column
    @Temporal(TemporalType.DATE)
    @Comment("生日")
    private LocalDate birthday;
    @Column(unique = true, length = 100)
    @Comment("微信ID")
    private String wechatId;
    @Column(nullable = false)
    @Builder.Default
    @Comment("账号是否未过期")
    private boolean accountNonExpired = true;
    @Column(nullable = false)
    @Builder.Default
    @Comment("账号是否未锁定")
    private boolean accountNonLocked = true;
    @Column(nullable = false)
    @Builder.Default
    @Comment("凭据是否未过期")
    private boolean credentialsNonExpired = true;
    @Column(nullable = false)
    @Builder.Default
    @Comment("是否启用")
    private boolean enabled = true;
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<RedeemCode> redeemCodes;

    @ManyToMany(mappedBy = "users")
    private Set<CourseColumn> courseColumns;

    @ManyToMany(mappedBy = "users")
    private Set<Course> courses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(roles)) {
            return result;
        }
        result.addAll(roles);
        roles.forEach(role -> result.addAll(role.getBasicAuthorities()));
        return result;
    }

}
