CREATE TABLE IF NOT EXISTS gift_certificate(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE,
    description VARCHAR(1500),
    price INT NOT NULL,
    duration INT NOT NULL,
    createDate TIMESTAMP(3) NOT NULL,
    lastUpdateDate TIMESTAMP(3) NOT NULL);

CREATE TABLE IF NOT EXISTS tag (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NULL UNIQUE);


CREATE TABLE IF NOT EXISTS gift_certificate_tag (
    gift_certeficate_id INT NOT NULL,
    tag_id INT NOT NULL,
    CONSTRAINT fk_gift_certeficate_tag_gift_certeficate
    FOREIGN KEY (gift_certeficate_id)
    REFERENCES gift_certificate (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT fk_gift_certeficate_tag_tag1
    FOREIGN KEY (tag_id)
    REFERENCES tag (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);