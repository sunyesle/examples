import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    scenarios: {
        cpu_spike: {
            executor: 'constant-vus',
            vus: 25,
            duration: '30s',
        },
    },
};

export default function () {
    http.get('http://localhost:8080/api/cpu/contention');
}
