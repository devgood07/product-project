-- Brand 테이블 생성
CREATE TABLE brand (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(30) NOT NULL UNIQUE
);

-- Category 테이블 생성
CREATE TABLE category (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(20) NOT NULL UNIQUE
);

-- Product 테이블 생성
CREATE TABLE product (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         brand_id BIGINT NOT NULL,
                         category_id BIGINT NOT NULL,
                         price BIGINT NOT NULL,
                         CONSTRAINT fk_product_brand FOREIGN KEY (brand_id) REFERENCES brand(id) ON DELETE CASCADE,
                         CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

-- Product 테이블 인덱스 생성
CREATE INDEX idx_brand_price ON product (brand_id, price);
CREATE INDEX idx_category_price ON product (category_id, price);
