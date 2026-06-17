import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    vus: 10,
    duration: '15s',
};

export default function () {
    // 1번과 2번 엔드포인트를 교차 호출하여 충돌 유도
    if (__VU % 2 === 0) {
        http.get('http://localhost:8080/api/deadlock/1');
    } else {
        http.get('http://localhost:8080/api/deadlock/2');
    }

    sleep(0.1);
}
