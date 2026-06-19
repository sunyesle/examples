# k6 성능 테스트 (부하 테스트)
k6는 Grafana Labs에서 만든 오픈소스 성능 테스트 도구로, JavaScript 기반으로 테스트 시나리오를 작성하여 여러 사용자의 동시 요청을 시뮬레이션 할 수 있다.

## 설치
**Windows**
```bash
winget install k6 --source winget
```
**MacOS**
```bash
brew install k6
```

## Grafana 대시보드
k6 성능 테스트를 OpenTelemetry와 통합한다. 테스트 메트릭은 OpenTelemetry 수집기(OTEL Collector)로 전송되고, 수집기는 이를 Prometheus에 전달하여 저장한다. 이렇게 저장된 메트릭은 Grafana 대시보드를 사용하여 시각화할 수 있다.

### docker-compose 실행
```bash
docker-compose up -d
```

### k6 대시보드 접속
http://localhost:3000/d/demo-uid/k6-opentelemetry-prometheus

## 성능 테스트 실행
```bash
K6_OTEL_GRPC_EXPORTER_ENDPOINT=localhost:4317 \
K6_OTEL_GRPC_EXPORTER_INSECURE=true \
K6_OTEL_METRIC_PREFIX=k6_ \
k6 run --tag testid=0 -o experimental-opentelemetry k6/script.js
```

## 1. 메모리 누수
```bash
./run_test.sh memory-leak-test.js 1
```

## 2. 톰캣 스레드 풀 고갈
```bash
./run_test.sh tomcat-thread-test.js 2
```

## 3. DB 커넥션 풀 고갈
```bash
./run_test.sh db-connection-test.js 3
```

## 4. CPU 과부하
```bash
./run_test.sh cpu-contention-test.js 4
```

## 5. 데드락
```bash
./run_test.sh deadlock-test.js 5
```
