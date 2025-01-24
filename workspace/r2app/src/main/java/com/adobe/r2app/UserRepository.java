package com.adobe.r2app;


import org.springframework.data.r2dbc.repository.R2dbcRepository;


public interface UserRepository extends  R2dbcRepository<UserEntity, String> {
}
