
-- SQL to create databse
-- CREATE DATABASE sbs WITH ENCODING UTF8;

-- Creating table 'accounts'
DO $$
BEGIN 
	IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'accounts') THEN
		RAISE NOTICE 'Creating table ''accounts''...';
		CREATE TABLE "accounts" (
			"account_id" SERIAL NOT NULL,
			PRIMARY KEY ("account_id"),
			"account_holder_name" VARCHAR(50) NULL,
			"balance" NUMERIC NULL
		);
	ELSE 
		RAISE NOTICE 'Table ''accounts'' already exists';
	END IF;
END $$;

-- Creating table 'transactions'
DO $$
BEGIN 
	IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'transactions') THEN
		RAISE NOTICE 'Creating table ''transactions''...';
		CREATE TABLE "transactions" (
			"transaction_id" SERIAL NOT NULL,
			PRIMARY KEY ("transaction_id"),
			"account_id" INT NOT NULL,
			"account_holder_name" VARCHAR(50) NULL,
			"transaction_type" VARCHAR(20) CHECK (transaction_type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')),
			"amount" NUMERIC NULL,
			"timestamp" TIMESTAMP
		);
	ELSE 
		RAISE NOTICE 'Table ''transactions'' already exists';
	END IF;
END $$;

/*
 psql -h 192.168.0.130 -U postgres -d postgres -f sql\database-sql.sql
 */