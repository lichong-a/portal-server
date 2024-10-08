/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.domain.security;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.funcode.portal.server.common.domain.base.BaseEntity;
import org.funcode.portal.server.common.domain.ielts.Course;
import org.funcode.portal.server.common.domain.ielts.CourseColumn;
import org.funcode.portal.server.common.domain.ielts.Order;
import org.funcode.portal.server.common.domain.ielts.RedeemCode;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;

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
@Table(name = "tb_user")
@Comment("人员表")
@Schema(description = "人员")
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("人员ID")
    @Schema(description = "人员ID")
    private Long id;
    @Column(length = 100)
    @Comment("昵称")
    @Schema(description = "昵称")
    private String nickName;
    @Column(length = 100)
    @Comment("真实姓名")
    @Schema(description = "真实姓名")
    private String realName;
    @Column(nullable = false, unique = true, length = 100)
    @Comment("用户名")
    @Schema(description = "用户名")
    private String username;
    @Column(length = 100)
    @Comment("性别")
    @Schema(description = "性别")
    private String gender;
    @Column(unique = true, length = 100)
    @Comment("邮箱")
    @Schema(description = "邮箱")
    private String email;
    @Column(unique = true, length = 100)
    @Comment("手机号")
    @Schema(description = "手机号")
    private String phone;
    @JsonIgnore
    @Column(nullable = false, length = 100)
    @Comment("密码")
    @Schema(description = "密码")
    private String password;
    @Column(length = 300)
    @Comment("头像")
    @Schema(description = "头像")
    private String avatar;
    @Column
    @Temporal(TemporalType.DATE)
    @Comment("生日")
    @Schema(description = "生日")
    private LocalDate birthday;
    @Column(unique = true, length = 100)
    @Comment("微信ID")
    @Schema(description = "微信ID")
    private String wechatId;
    @Column(nullable = false)
    @Builder.Default
    @Comment("账号是否未过期")
    @Schema(description = "账号是否未过期")
    private boolean accountNonExpired = true;
    @Column(nullable = false)
    @Builder.Default
    @Comment("账号是否未锁定")
    @Schema(description = "账号是否未锁定")
    private boolean accountNonLocked = true;
    @Column(nullable = false)
    @Builder.Default
    @Comment("凭据是否未过期")
    @Schema(description = "凭据是否未过期")
    private boolean credentialsNonExpired = true;
    @Column(nullable = false)
    @Builder.Default
    @Comment("是否启用")
    @Schema(description = "是否启用")
    private boolean enabled = true;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Set<Role> roles;

    @OneToMany
    @ToString.Exclude
    @JsonBackReference
    private Set<Order> orders;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @ToString.Exclude
    private Set<RedeemCode> redeemCodes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_user_course_column",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_column_id"))
    @ToString.Exclude
    private Set<CourseColumn> courseColumns;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_user_course",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @ToString.Exclude
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
