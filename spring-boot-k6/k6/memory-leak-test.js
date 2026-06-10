import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    stages: [
        { duration: '30s', target: 7 },
        { duration: '10s', target: 0 },
    ],
};

export default function () {
    http.get('http://localhost:8080/api/memory/leak');
    sleep(1);
}
