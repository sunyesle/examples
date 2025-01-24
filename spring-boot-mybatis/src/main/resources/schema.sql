DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT,
    member_id BIGINT,
    total_amount INT,
    order_status VARCHAR(4),
    PRIMARY KEY (order_id)
);
