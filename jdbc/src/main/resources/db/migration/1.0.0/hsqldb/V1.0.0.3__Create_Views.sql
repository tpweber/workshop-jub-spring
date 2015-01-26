create view PERSON_WITH_ADDRESS as (
    select c.ID, c.FIRST_NAME, c.LAST_NAME, c.EMAIL_ADDRESS, a.STREET, a.CITY, a.COUNTRY
    from CUSTOMER c, ADDRESS a
    where a.CUSTOMER_ID = a.ID
);