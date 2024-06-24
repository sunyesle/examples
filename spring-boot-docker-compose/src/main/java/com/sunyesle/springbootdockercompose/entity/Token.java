package com.sunyesle.springbootdockercompose.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "token") // 설정한 값을 Redis의 key 값 prefix로 사용한다.
public class Token {
    @Id // 키(key) 값이 되며, token:{id} 위치에 auto-increment 된다.
    private String id;

    @Indexed // Redis의 보조인덱스 생성. 조회 작업을 활성화한다.
    private String token;

    private String role;

    @TimeToLive // 만료시간(초)을 설정한다.
    private long ttl;

    public Token update(String token, long ttl) {
        this.token = token;
        this.ttl = ttl;
        return this;
    }
}
