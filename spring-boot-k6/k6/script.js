import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    vus: 5, // 가상 유저 수
    duration: '10s', // 테스트 시간
};

// 가상 유저가 반복해서 실행할 함수
export default function () {
    http.get('http://localhost:8080/api/ping'); // API 호출
    sleep(0.5); // 0.5초 대기
}
