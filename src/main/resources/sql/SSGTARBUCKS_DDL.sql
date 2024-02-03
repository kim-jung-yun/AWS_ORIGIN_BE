# CREATE USER 'SSGtarbucks'@'localhost' IDENTIFIED WITH mysql_native_password BY 'SSGtarbucks';
## drop database SSGtarbucksDB;
#CREATE DATABASE SSGtarbucksDB;
#USE SSGtarbucksDB;
#GRANT ALL PRIVILEGES ON  SSGtarbucksDB.* TO 'SSGtarbucks'@'localhost' WITH GRANT OPTION;
#FLUSH PRIVILEGES;
#COMMIT;

# PW BCRYPT 암호화

/*
# complete
CREATE TABLE IF NOT EXISTS   branch (
  branch_id VARCHAR(45) NOT NULL PRIMARY KEY,
  branch_name VARCHAR(45) NOT NULL,
  branch_address VARCHAR(100) NOT NULL
);

# complete
CREATE TABLE IF NOT EXISTS   user (
  user_id VARCHAR(45) NOT NULL  PRIMARY KEY,
  user_pw VARCHAR(1000) NOT NULL,
  user_name VARCHAR(45) NOT NULL,
  user_type VARCHAR(45) NOT NULL,
  user_phone VARCHAR(45) NOT NULL,
  user_email VARCHAR(45) NOT NULL,
  branch_id VARCHAR(45) NULL,

  CONSTRAINT fk_user_branch1 FOREIGN KEY (branch_id) REFERENCES  branch (branch_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

# complete
CREATE TABLE IF NOT EXISTS temp_pw  (
   temp_pw_id  INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   temp_pw_code  VARCHAR(1000) NOT NULL,
   temp_pw_time  TIME NOT NULL,
   temp_pw_exp  DATE NOT NULL,
   temp_pw_used  BOOLEAN NOT NULL,
   user_id  VARCHAR(45) NULL,

  CONSTRAINT  fk_temp_pw_user1 FOREIGN KEY ( user_id ) REFERENCES   user  ( user_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);


# complete
CREATE TABLE IF NOT EXISTS   category (
  category_id VARCHAR(45) NOT NULL PRIMARY KEY,
  category_name VARCHAR(45) NOT NULL
);

# complete
CREATE TABLE IF NOT EXISTS  image (
  image_id VARCHAR(45) NOT NULL PRIMARY KEY,
  image_name VARCHAR(45) NULL,
  image_path VARCHAR(200) NULL
);

# complete
CREATE TABLE IF NOT EXISTS   product (
  product_id int NOT NULL auto_increment PRIMARY KEY,
  product_code VARCHAR(45) NOT NULL,
  product_name VARCHAR(45) NOT NULL,
  product_standard VARCHAR(45) NOT NULL,
  product_unit VARCHAR(45) NOT NULL,
  product_spec VARCHAR(100) NULL,
  product_quantity VARCHAR(100) NULL,
  category_id VARCHAR(45) NOT NULL,
  image_id VARCHAR(45) NOT NULL,

  CONSTRAINT fk_product_category1 FOREIGN KEY (category_id) REFERENCES   category (category_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

# processing
CREATE TABLE IF NOT EXISTS   qrcode (
  qrcode_id int NOT NULL auto_increment PRIMARY KEY ,
  qrcode_code BLOB NULL,
  qrcode_path VARCHAR(1000) NULL,
  qrcode_date DATE NULL,
  qrcode_type varchar(45) NULL
  ,qrcode_link varchar(1000)
);

# complete
CREATE TABLE IF NOT EXISTS   item (
  item_id int NOT NULL auto_increment  PRIMARY KEY ,
  item_code VARCHAR(45) NOT NULL,
  item_exp DATE NOT NULL,
  product_id int NOT NULL,
  qrcode_id int NOT NULL,

  CONSTRAINT fk_item_product1 FOREIGN KEY (product_id) REFERENCES   product (product_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
        
  CONSTRAINT fk_item_qrcode1 FOREIGN KEY (qrcode_id) REFERENCES   qrcode (qrcode_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);



CREATE TABLE IF NOT EXISTS   stock_location (
  location_id INT NOT NULL auto_increment PRIMARY KEY,
  location_code VARCHAR(45) NOT NULL,
  location_area VARCHAR(45) NOT NULL,
  location_section VARCHAR(45) NOT NULL,
  location_section_name VARCHAR(45) NOT NULL,
  location_column int NOT NULL,
  location_row int NOT NULL,
  location_alias VARCHAR(100) NULL,
  qrcode_id int NOT NULL,
  branch_id VARCHAR(45) NULL,

  CONSTRAINT fk_stock_location_branch1 FOREIGN KEY (branch_id) REFERENCES  branch (branch_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,

  CONSTRAINT fk_stock_location_qrcode1 FOREIGN KEY (qrcode_id) REFERENCES   qrcode (qrcode_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

);


CREATE TABLE IF NOT EXISTS   stock (
  stock_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  stock_quantity INT NOT NULL,
  stock_date DATE NOT NULL,
  branch_id VARCHAR(45) not null,
  item_id int NOT NULL,      
  location_id int NULL,
  
CONSTRAINT fk_stock_stock_location1 FOREIGN KEY (location_id) REFERENCES   stock_location (location_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,

  CONSTRAINT fk_stock_branch1 FOREIGN KEY (branch_id) REFERENCES   branch (branch_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    
  CONSTRAINT fk_stock_item1 FOREIGN KEY (item_id) REFERENCES   item (item_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);



CREATE TABLE IF NOT EXISTS   income (
  income_id INT NOT NULL auto_increment PRIMARY KEY,
  income_code LONG NOT NULL,
  income_date DATE NOT NULL,
  income_amount INT NOT NULL,
  income_status VARCHAR(45) NOT NULL,
  branch_id VARCHAR(45) NOT NULL,

  CONSTRAINT fk_income_branch1 FOREIGN KEY (branch_id) REFERENCES   branch (branch_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);



CREATE TABLE IF NOT EXISTS   income_list (
  income_list_id INT NOT NULL PRIMARY KEY auto_increment,
  income_id int NOT NULL,
  income_list_quantity int null,
  income_list_result varchar(100) null,
  item_id int NOT NULL,

  CONSTRAINT fk_income_list_item1 FOREIGN KEY (item_id) REFERENCES   item (item_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    
  CONSTRAINT fk_income_list_income1 FOREIGN KEY (income_id) REFERENCES  income (income_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);


CREATE TABLE IF NOT EXISTS   outcome (
  outcome_id int NOT NULL auto_increment PRIMARY KEY,
  outcome_code LONG NOT NULL,
  outcome_date DATE NOT NULL,
  outcome_amount INT NOT NULL,
  outcome_status VARCHAR(45) NOT NULL,
  branch_id VARCHAR(45) NOT NULL,

  CONSTRAINT fk_outcome_branch1 FOREIGN KEY (branch_id) REFERENCES   branch (branch_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);


CREATE TABLE IF NOT EXISTS   outcome_list (
  outcome_list_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  outcome_list_quantity INT NOT NULL,
  outcome_list_reason VARCHAR(200) NULL,
  outcome_id int NOT NULL,
  item_id int NOT NULL,

  CONSTRAINT fk_outcome_list_item1 FOREIGN KEY (item_id) REFERENCES   item (item_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,

  CONSTRAINT fk_outcome_list_outcome FOREIGN KEY (outcome_id) REFERENCES   outcome (outcome_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);


CREATE TABLE IF NOT EXISTS   discard (
  discard_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  discard_code LONG NOT NULL,
  discard_date DATE NOT NULL,
  discard_amount INT NOT NULL,
  discard_reason VARCHAR(100) NOT NULL,
  branch_id  VARCHAR(45) NOT NULL,
  
  CONSTRAINT fk_dicard_branch1 FOREIGN KEY (branch_id) REFERENCES   branch (branch_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS   discard_list (
  discard_list_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  discard_list_quantity INT NOT NULL,
  item_id INT NOT NULL,
  discard_id INT,
 
  CONSTRAINT fk_dicard_list_item1 FOREIGN KEY (item_id) REFERENCES  item (item_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    
  CONSTRAINT fk_dicard_list_discard1 FOREIGN KEY (discard_id) REFERENCES   discard (discard_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
*/
/*
CREATE TABLE IF NOT EXISTS inspection (
  inspection_id INT NOT NULL PRIMARY KEY,
  inspection_code LONG NOT NULL,
  inspection_date DATE NOT NULL,
  inspection_amount int NOT NULL,
  inspection_status varchar(100) NULL,
  branch_id varchar(45) NOT NULL,

  CONSTRAINT fk_inspection_branch1 FOREIGN KEY (branch_id) REFERENCES  branch_id (branch_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS inspection_list (
  inspection_list_id INT NOT NULL PRIMARY KEY,
  inspection_list_quantity INT NOT NULL,
  inspection_list_result VARCHAR(45) NOT NULL,
  inspection_list_memo VARCHAR(100) NULL,
  inspection_id INT NOT NULL,
  
  CONSTRAINT fk_inspection_list_inspection1 FOREIGN KEY (inspection_id) REFERENCES   inspection (inspection_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
*/
/*
CREATE TABLE IF NOT EXISTS sale (
  sale_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  sale_code long NOT NULL,
  sale_date DATE NOT NULL,
  sale_amount INT NOT NULL,
  sale_status varchar(100) null, 
  branch_id VARCHAR(45) NOT NULL,
  
  CONSTRAINT fk_sale_branch1 FOREIGN KEY (branch_id) REFERENCES branch (branch_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS sale_list (
  sale_list_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  sale_list_quantity int not null,
  sale_id int NOT NULL,
  item_id int NOT NULL,

  CONSTRAINT fk_sale_list_sale1 FOREIGN KEY (sale_id) REFERENCES sale (sale_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    
  CONSTRAINT fk_sale_list_item1 FOREIGN KEY (item_id) REFERENCES item (item_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
*/