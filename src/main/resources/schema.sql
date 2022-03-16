CREATE TABLE coin_balance(
                         id int primary key AUTO_INCREMENT,
                         coin varchar(255) NOT NULL,
                         balance int,
                         user_id varchar(255),
                         created_at varchar(255),
                         updated_at varchar(255)
);
