CREATE TABLE Managers (
	id SERIAL,
	first_name VARCHAR(200),
	last_name VARCHAR(200),
	username VARCHAR(200) NOT NULL,
	password VARCHAR(200) NOT NULL,
	CONSTRAINT pk_managers_id PRIMARY KEY (id),
	CONSTRAINT check_password CHECK (LENGTH(password) >= 10),
	CONSTRAINT u_m_username_m_password UNIQUE (username, password)
);

CREATE TABLE Employees (
	id SERIAL,
	first_name VARCHAR(200),
	last_name VARCHAR(200),
	title VARCHAR(200),
	username VARCHAR(200) NOT NULL,
	password VARCHAR(200) NOT NULL,
	manager_id INTEGER NOT NULL,
	CONSTRAINT pk_employees_id PRIMARY KEY (id),
	CONSTRAINT check_password CHECK (LENGTH(password) >= 10),
	CONSTRAINT fk_managers_employees_Nto1 FOREIGN KEY (manager_id) REFERENCES Managers(id),
	CONSTRAINT u_e_username_e_password UNIQUE (username, password)
);

CREATE TABLE Reimbursment_requests (
	id SERIAL,
	receipt BYTEA,
	pending BOOLEAN DEFAULT true,
	resolved BOOLEAN DEFAULT false,
	approved BOOLEAN DEFAULT false,
	denied BOOLEAN DEFAULT false,
	employee_id INTEGER NOT NULL,
	CONSTRAINT u_receipt UNIQUE (receipt),
	CONSTRAINT pk_reimbursment_requests_id PRIMARY KEY (id),
	CONSTRAINT fk_employees_reimbursment_requests_Nto1 FOREIGN KEY (employee_id) REFERENCES Employees(id)
);