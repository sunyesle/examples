CREATE TABLE team_identity (
    team_id BIGINT AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (team_id)
);

CREATE TABLE member_identity (
    member_id BIGINT AUTO_INCREMENT,
    name VARCHAR(255),
    age INT,
    team_id BIGINT,
    PRIMARY KEY (member_id)
);

CREATE TABLE team_uuid (
    team_id BINARY(16) NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (team_id)
);

CREATE TABLE member_uuid (
    member_id BINARY(16) NOT NULL,
    name VARCHAR(255),
    age INT,
    team_id BINARY(16),
    PRIMARY KEY (member_id)
);
