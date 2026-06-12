import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    scenarios: {
        // 시나리오 1: 무거운 API로 스레드 풀을 꽉 채워 고갈시킴
        heavy_load: {
            executor: 'constant-vus',
            vus: 30, // 톰캣 스레드 풀 최대 크기(10개)보다 많은 30명의 유저 투입
            duration: '20s',
            exec: 'runHeavy',
        },
        // 시나리오 2: 중간에 정상적인 가벼운 API를 호출해봄
        light_check: {
            executor: 'constant-vus',
            vus: 2,
            duration: '20s',
            exec: 'runLight',
            startTime: '3s', // 부하가 걸린 지 3초 뒤부터 시작
        },
    },
};

export function runHeavy() {
    http.get('http://localhost:8080/api/tomcat/heavy');
    sleep(0.1);
}

export function runLight() {
    http.get('http://localhost:8080/api/tomcat/light');
    sleep(0.1);
}
