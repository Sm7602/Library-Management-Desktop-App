USE library_db;

INSERT INTO users (username, password, role)
VALUES ('admin', 'admin123', 'ADMIN')
ON DUPLICATE KEY UPDATE username = username;

INSERT INTO books (isbn, title, author, genre, year, quantity, available)
VALUES
('9780134685991', 'Effective Java', 'Joshua Bloch', 'Programming', 2018, 10, 10),
('9780262033848', 'Introduction to Algorithms', 'Thomas Cormen', 'Computer Science', 2009, 8, 8),
('9780132350884', 'Clean Code', 'Robert C. Martin', 'Programming', 2008, 6, 6)
ON DUPLICATE KEY UPDATE isbn = isbn;

INSERT INTO members (name, email, phone, address, status, member_since)
VALUES
('Alice Johnson', 'alice@email.com', '9876543210', 'City Address', 'Active', CURRENT_DATE),
('Bob Wilson', 'bob@email.com', '8765432109', 'City Address', 'Active', CURRENT_DATE)
ON DUPLICATE KEY UPDATE email = email;
