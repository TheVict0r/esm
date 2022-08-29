CREATE TABLE IF NOT EXISTS gift_certificate
(
    id               INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(30)  NOT NULL UNIQUE,
    description      VARCHAR(1500),
    price            INT          NOT NULL,
    duration         INT          NOT NULL,
    create_date      TIMESTAMP(3) NOT NULL,
    last_update_date TIMESTAMP(3) NOT NULL
);

CREATE TABLE IF NOT EXISTS tag
(
    id   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS gift_certificate_tag
(
    gift_certificate_id INT NOT NULL,
    tag_id              INT NOT NULL,
    CONSTRAINT fk_gift_certificate_tag_gift_certificate
        FOREIGN KEY (gift_certificate_id)
            REFERENCES gift_certificate (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_gift_certificate_tag_tag1
        FOREIGN KEY (tag_id)
            REFERENCES tag (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `user`
(
    id   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS purchase
(
    id      INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    date    TIMESTAMP(3) NULL,
    cost    INT          NULL,
    user_id INT          NOT NULL,
    CONSTRAINT fk_order_user1
        FOREIGN KEY (user_id)
            REFERENCES `user` (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS purchase_gift_certificate
(
    purchase_id         INT NOT NULL,
    gift_certificate_id INT NOT NULL,
    CONSTRAINT fk_order_gift_certificate_order1
        FOREIGN KEY (purchase_id)
            REFERENCES purchase (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT fk_order_gift_certificate_gift_certificate1
        FOREIGN KEY (gift_certificate_id)
            REFERENCES gift_certificate (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);