-- ================================================================
-- 1. TB_CODE (총 11건)
-- ================================================================
INSERT INTO PUBLIC.TB_CODE (CODE_TYPE, CODE, VALUE, ACTIVE, CREATED, UPDATED)
VALUES
-- SR_TYPE (5건)
('SR_TYPE', 'TYPE01', '일반 문의', 'Y', NOW(), NOW()),
('SR_TYPE', 'TYPE02', '불만 접수', 'Y', NOW(), NOW()),
('SR_TYPE', 'TYPE03', '서비스 요청', 'Y', NOW(), NOW()),
('SR_TYPE', 'TYPE04', '기술 지원', 'Y', NOW(), NOW()),
('SR_TYPE', 'TYPE05', '기타', 'Y', NOW(), NOW()),

-- SR_STATUS (6건)
('SR_STATUS', 'STATUS01', '신규', 'Y', NOW(), NOW()),
('SR_STATUS', 'STATUS02', '접수', 'Y', NOW(), NOW()),
('SR_STATUS', 'STATUS03', '처리중', 'Y', NOW(), NOW()),
('SR_STATUS', 'STATUS04', '보류', 'Y', NOW(), NOW()),
('SR_STATUS', 'STATUS05', '완료', 'Y', NOW(), NOW()),
('SR_STATUS', 'STATUS06', '종료', 'Y', NOW(), NOW());

-- ================================================================
-- 2. TB_CUSTOMER (1000건)
-- ================================================================
INSERT INTO PUBLIC.TB_CUSTOMER (ID, "NAME", BIRTHDAY, GENDER, ADDRESS, PHONE_NUMBER, "TYPE", CREATED, UPDATED)
SELECT
    'CUST_' || LPAD(i::text, 5, '0') AS ID,
    '고객_' || i,
    TO_CHAR(DATE '1980-01-01' + (RANDOM() * 15000)::INT * INTERVAL '1 day', 'YYYY-MM-DD') AS BIRTHDAY,
    CASE WHEN RANDOM() < 0.5 THEN 'M' ELSE 'F' END AS GENDER,
    '서울시 구 ' || (i % 25 + 1) || '동',
    '010-' || LPAD((1000 + (RANDOM() * 8999)::INT)::text, 4, '0') || '-' || LPAD((1000 + (RANDOM() * 8999)::INT)::text, 4, '0'),
    CASE WHEN RANDOM() < 0.5 THEN 'VIP' ELSE 'NORMAL' END AS "TYPE",
    NOW(),
    NOW()
FROM generate_series(1, 1000) AS s(i);

-- ================================================================
-- 3. TB_USER (500건)
-- ================================================================
INSERT INTO PUBLIC.TB_USER (ID, "NAME", PHONE_NUMBER, EMAIL, CREATED, UPDATED)
SELECT
    'USER_' || LPAD(i::text, 5, '0'),
    '상담원_' || i,
    '010-' || LPAD((1000 + (RANDOM() * 8999)::INT)::text, 4, '0') || '-' || LPAD((1000 + (RANDOM() * 8999)::INT)::text, 4, '0'),
    'user' || i || '@example.com',
    NOW(),
    NOW()
FROM generate_series(1, 500) AS s(i);

-- ================================================================
-- 4. TB_SERVICE_REQUEST (1만건)
-- ================================================================
INSERT INTO PUBLIC.TB_SERVICE_REQUEST (
    ID, TITLE, CUSTOMER_ID, "TYPE", DETAIL, STATUS,
    CALL_AGENT_ID, VOC_ASSGNEE_ID, VOC_ASSGNEE_DEPT_ID, CREATED, UPDATED
)
SELECT
    'SR_' || LPAD(i::text, 6, '0'),
    '서비스 요청 제목 ' || i,
    'CUST_' || LPAD(((RANDOM() * 9999)::INT + 1)::text, 5, '0'),
    'TYPE0' || ((RANDOM() * 4)::INT + 1),  -- SR_TYPE 1~5 중 랜덤
    '테스트용 서비스 요청 내용입니다. 요청번호: ' || i,
    'STATUS0' || ((RANDOM() * 5)::INT + 1), -- SR_STATUS 1~6 중 랜덤
    'USER_' || LPAD(((RANDOM() * 4999)::INT + 1)::text, 5, '0'),
    'USER_' || LPAD(((RANDOM() * 4999)::INT + 1)::text, 5, '0'),
    NULL,
    NOW() - (RANDOM() * INTERVAL '180 days'),
    NOW()
FROM generate_series(1, 10000) AS s(i);