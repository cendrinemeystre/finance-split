CREATE TABLE finance_data
(
    id          UUID NOT NULL,
    date_time   TIMESTAMP WITHOUT TIME ZONE,
    person_id   UUID NOT NULL,
    amount      DOUBLE PRECISION,
    description VARCHAR(255),
    CONSTRAINT pk_finance_data PRIMARY KEY (id)
);

CREATE TABLE person
(
    id    UUID NOT NULL,
    name  VARCHAR(255),
    total DOUBLE PRECISION,
    CONSTRAINT pk_person PRIMARY KEY (id)
);

ALTER TABLE finance_data
    ADD CONSTRAINT FK_FINANCE_DATA_ON_PERSON FOREIGN KEY (person_id) REFERENCES person (id);