/* 기존 파일 업로드 */
/*
LOAD DATA INFILE 'DB_sample\\branch.csv'
INTO TABLE branch
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

select * from branch;

LOAD DATA INFILE 'DB_sample\\user.CSV'
INTO TABLE user
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES;

select * from user;


LOAD DATA INFILE 'DB_sample\\category.csv'
INTO TABLE category
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES;

select * from category;

SET FOREIGN_KEY_CHECKS = 0;

LOAD DATA INFILE 'DB_sample\\image.CSV'
INTO TABLE image
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES;
*/
/*
LOAD DATA INFILE 'DB_sample\\qrcode.CSV'
INTO TABLE qrcode
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;
*/
/* 함수 추가 */
/*
CREATE DEFINER=`root`@`localhost` FUNCTION `insert_qrcode`() RETURNS varchar(100) CHARSET utf8mb4
BEGIN
    DECLARE count INT DEFAULT 1;

    WHILE count <= 20000 DO
        -- 데이터 삽입
        INSERT INTO qrcode (qrcode_code, qrcode_path, qrcode_date, qrcode_type)
        VALUES (NULL, NULL, NULL, NULL);

        SET count = count + 1;
    END WHILE;

    RETURN 'All NULL QRCode data inserted successfully.';
END
*/
/*
select insert_qrcode();




LOAD DATA INFILE 'DB_sample\\product.CSV'
INTO TABLE product
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES;


LOAD DATA INFILE 'DB_sample\\item.CSV'
INTO TABLE item
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

LOAD DATA INFILE 'DB_sample\\stock_location.CSV'
INTO TABLE stock_location
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES;

LOAD DATA INFILE 'DB_sample\\stock.CSV'
INTO TABLE stock
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

LOAD DATA INFILE 'DB_sample\\income.CSV'
INTO TABLE income
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
SET income_code = CAST(income_code AS DECIMAL(20, 0))
;

select * from income;


LOAD DATA INFILE 'DB_sample\\income_list.CSV'
INTO TABLE income_list
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES
;

select * from income_list;

LOAD DATA INFILE 'DB_sample\\outcome.CSV'
INTO TABLE outcome
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
SET outcome_code = CAST(outcome_code AS DECIMAL(20, 0))
;

LOAD DATA INFILE 'DB_sample\\outcome_list.CSV'
INTO TABLE outcome_list
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;


LOAD DATA INFILE 'DB_sample\\discard.CSV'
INTO TABLE discard
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
SET discard_code = CAST(discard_code AS DECIMAL(20, 0))
;

LOAD DATA INFILE 'DB_sample\\discard_list.CSV'
INTO TABLE discard_list
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

LOAD DATA INFILE 'DB_sample\\sale.CSV'
INTO TABLE sale
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
SET sale_code = CAST(sale_code AS DECIMAL(20, 0))
;

LOAD DATA INFILE 'DB_sample\\sale_list.CSV'
INTO TABLE sale_list
CHARACTER SET euckr
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

select * from sale_list;
commit;
*/