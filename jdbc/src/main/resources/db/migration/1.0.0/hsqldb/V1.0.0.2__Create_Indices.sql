CREATE UNIQUE INDEX ix_customer_email ON CUSTOMER (email_address ASC);
CREATE INDEX ix_customer_normalized ON CUSTOMER (normalized ASC);

CREATE INDEX ix_address_country ON ADDRESS (country ASC);
CREATE INDEX ix_address_customer ON ADDRESS (customer_id ASC);


CREATE INDEX ix_ordering_customer_id ON ORDERING (customer_id ASC);

CREATE UNIQUE INDEX ix_product_name ON PRODUCT (name ASC);
