
CREATE DATABASE `short_url` CHARACTER SET 'utf8mb4';

CREATE USER 'ShortUrl'@'%' IDENTIFIED BY 'ShortUrl@123';

GRANT ALL ON `short_url`.* TO 'ShortUrl'@'%';

GRANT ALL PRIVILEGES ON `short_url`.* TO 'ShortUrl'@'%' IDENTIFIED BY 'ShortUrl@123' WITH GRANT OPTION;