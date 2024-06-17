-- 用户表
CREATE TABLE tb_user
(
    id                      SERIAL PRIMARY KEY,
    nick_name               VARCHAR(100) NULL,
    real_name               VARCHAR(100) NULL,
    username                VARCHAR(100) NOT NULL UNIQUE,
    gender                  VARCHAR(100) NULL,
    email                   VARCHAR(100) NULL UNIQUE,
    phone                   VARCHAR(100) NULL UNIQUE,
    password                VARCHAR(100) NOT NULL,
    avatar                  VARCHAR(100) NULL,
    birthday                TIMESTAMP    NULL,
    account_non_expired     BOOLEAN      NOT NULL DEFAULT TRUE,
    account_non_locked      BOOLEAN      NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN      NOT NULL DEFAULT TRUE,
    enabled                 BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at              TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
comment on table tb_user is '用户表';
comment on column tb_user.id is '用户ID';
comment on column tb_user.nick_name is '昵称';
comment on column tb_user.real_name is '真实姓名';
comment on column tb_user.username is '用户名';
comment on column tb_user.gender is '性别';
comment on column tb_user.email is '邮箱';
comment on column tb_user.phone is '手机号';
comment on column tb_user.password is '密码';
comment on column tb_user.avatar is '头像';
comment on column tb_user.birthday is '生日';
comment on column tb_user.account_non_expired is '账号是否未过期';
comment on column tb_user.account_non_locked is '账号是否未锁定';
comment on column tb_user.credentials_non_expired is '凭据是否未过期';
comment on column tb_user.enabled is '是否启用';
comment on column tb_user.created_at is '创建时间';
comment on column tb_user.updated_at is '更新时间';

-- 角色表
CREATE TABLE tb_role
(
    id          SERIAL PRIMARY KEY,
    role_name   VARCHAR(100) NOT NULL,
    role_key    VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500) NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
comment on table tb_role is '角色表';
comment on column tb_role.id is '角色ID';
comment on column tb_role.role_name is '角色名称';
comment on column tb_role.role_key is '角色标识';
comment on column tb_role.description is '角色描述';
comment on column tb_role.created_at is '创建时间';
comment on column tb_role.updated_at is '更新时间';

-- 权限表
CREATE TABLE tb_basic_authority
(
    id             SERIAL PRIMARY KEY,
    authority_name VARCHAR(100) NOT NULL,
    authority_key  VARCHAR(100) NOT NULL UNIQUE,
    description    VARCHAR(500) NULL,
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
comment on table tb_basic_authority is '权限表';
comment on column tb_basic_authority.id is '权限ID';
comment on column tb_basic_authority.authority_name is '权限名称';
comment on column tb_basic_authority.authority_key is '权限标识';
comment on column tb_basic_authority.description is '权限描述';
comment on column tb_basic_authority.created_at is '创建时间';
comment on column tb_basic_authority.updated_at is '更新时间';

-- 用户角色关联表
CREATE TABLE tb_user_role
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);
comment on table tb_user_role is '用户角色关联表';
comment on column tb_user_role.user_id is '用户ID';
comment on column tb_user_role.role_id is '角色ID';
ALTER TABLE tb_user_role
    ADD CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES tb_user (id);
ALTER TABLE tb_user_role
    ADD CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES tb_role (id);

-- 角色权限关联表
CREATE TABLE tb_role_basic_authority
(
    role_id           INT NOT NULL,
    basic_authority_id INT NOT NULL,
    PRIMARY KEY (role_id, basic_authority_id)
);
comment on table tb_role_basic_authority is '角色权限关联表';
comment on column tb_role_basic_authority.role_id is '角色ID';
comment on column tb_role_basic_authority.basic_authority_id is '权限ID';
ALTER TABLE tb_role_basic_authority
    ADD CONSTRAINT fk_role_basic_authority_role_id FOREIGN KEY (role_id) REFERENCES tb_role (id);
ALTER TABLE tb_role_basic_authority
    ADD CONSTRAINT fk_role_basic_authority_basic_authority_id FOREIGN KEY (basic_authority_id) REFERENCES tb_basic_authority (id);

-- 用户权限关联表
CREATE TABLE tb_user_basic_authority
(
    user_id            INT NOT NULL,
    basic_authority_id INT NOT NULL,
    PRIMARY KEY (user_id, basic_authority_id)
);
comment on table tb_user_basic_authority is '用户权限关联表';
comment on column tb_user_basic_authority.user_id is '用户ID';
comment on column tb_user_basic_authority.basic_authority_id is '权限ID';
ALTER TABLE tb_user_basic_authority
    ADD CONSTRAINT fk_user_basic_authority_user_id FOREIGN KEY (user_id) REFERENCES tb_user (id);
ALTER TABLE tb_user_basic_authority
    ADD CONSTRAINT fk_user_basic_authority_basic_authority_id FOREIGN KEY (basic_authority_id) REFERENCES tb_basic_authority (id);
