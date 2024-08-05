-- Create banking_core_user table
CREATE TABLE IF NOT EXISTS `banking_core_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `identification_number` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `identification_number_UNIQUE` (`identification_number`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Create banking_core_account table
CREATE TABLE IF NOT EXISTS `banking_core_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `actual_balance` decimal(38,2) DEFAULT NULL,
  `available_balance` decimal(38,2) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `status` enum('PENDING','ACTIVE','DORMANT','BLOCKED') DEFAULT NULL,
  `type` enum('SAVINGS_ACCOUNT','FIXED_DEPOSIT','LOAN_ACCOUNT') DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt5uqy9p0v3rp3yhlgvm7ep0ij` (`user_id`),
  CONSTRAINT `FKt5uqy9p0v3rp3yhlgvm7ep0ij` FOREIGN KEY (`user_id`) REFERENCES `banking_core_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Create banking_core_transaction table
CREATE TABLE IF NOT EXISTS `banking_core_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT NULL,
  `reference_number` varchar(255) DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `transaction_type` enum('FUND_TRANSFER','UTILITY_PAYMENT') DEFAULT NULL,
  `account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_account_id` (`account_id`),
  CONSTRAINT `fk_account_id` FOREIGN KEY (`account_id`) REFERENCES `banking_core_account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Create banking_core_utility_account table
CREATE TABLE IF NOT EXISTS `banking_core_utility_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `number` varchar(255) DEFAULT NULL,
  `provider_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Create flyway_schema_history table (Flyway will manage this table itself)
-- CREATE TABLE IF NOT EXISTS `flyway_schema_history` (
--   `installed_rank` int NOT NULL,
--   `version` varchar(50) DEFAULT NULL,
--   `description` varchar(200) NOT NULL,
--   `type` varchar(20) NOT NULL,
--   `script` varchar(1000) NOT NULL,
--   `checksum` int DEFAULT NULL,
--   `installed_by` varchar(100) NOT NULL,
--   `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
--   `execution_time` int NOT NULL,
--   `success` tinyint(1) NOT NULL,
--   PRIMARY KEY (`installed_rank`),
--   KEY `flyway_schema_history_s_idx` (`success`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Optionally, insert initial data if it is not already in the database
-- INSERT INTO `banking_core_user` VALUES (1, 'john.doe@example.com', 'John', 'ID78965', 'Doe'), (2, 'jane.smith@example.com', 'Jane', 'ID78565', 'Smith'), ... ;
-- INSERT INTO `banking_core_account` VALUES (1, 4470.00, 4420.00, 'ACC123456', 'ACTIVE', 'SAVINGS_ACCOUNT', 1), ... ;
-- INSERT INTO `banking_core_transaction` VALUES (10, -130.00, 'ACC246810', '08a414a5-3036-45f9-852a-ea4e68d2c5d7', 'FUND_TRANSFER', 1), ... ;
-- INSERT INTO `banking_core_utility_account` VALUES (1, 'UTIL123', 'Electricity_Company'), ... ;
