import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    vus: 10, // DB 커넥션 풀(3개)보다 많은 10명의 유저 투입
    duration: '30s',
};

export default function () {
    http.get('http://localhost:8080/api/conn/leak');
    sleep(0.1);
}
