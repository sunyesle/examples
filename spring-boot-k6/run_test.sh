#!/bin/bash

# 사용법: ./run_test.sh [스크립트파일명] [테스트ID]
SCRIPT_NAME=$1
TEST_ID=$2

K6_OTEL_GRPC_EXPORTER_ENDPOINT=localhost:4317 \
K6_OTEL_GRPC_EXPORTER_INSECURE=true \
K6_OTEL_METRIC_PREFIX="k6_" \
k6 run --tag testid=$TEST_ID -o experimental-opentelemetry k6/$SCRIPT_NAME
