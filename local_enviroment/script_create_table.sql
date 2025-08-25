CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    identity_document VARCHAR(20),
    birth_date DATE,
    address VARCHAR(200),
    phone_number VARCHAR(20),
    email VARCHAR(100),
    base_salary NUMERIC(15,2)
);


INSERT INTO users (
    first_name,
    last_name,
    identity_document,
    birth_date,
    address,
    phone_number,
    email,
    base_salary
) VALUES (
    'John',
    'Doe',
    '22229850',
    '1990-05-15',
    '123 Main Street, Lima, Peru',
    '+51987654321',
    'john.doe@example.com',
    3500.50
);