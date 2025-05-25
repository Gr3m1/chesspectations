ALTER TABLE personal_details
    RENAME TO player_details;

INSERT INTO chessdata.player_details (id, player_name, email_address, date_of_birth, games_played) VALUES
('e1f2c7d1-1234-4b56-a123-1a2b3c4d5e01', 'Alice Knight', 'alice.knight@example.com', '1990-04-15', 42),
('f7d3a2b9-4321-4f98-b234-5c6d7e8f9012', 'Bob Rook', 'bob.rook@example.com', '1985-11-23', 58),
('c3b2a1e0-7890-4c12-c345-678901234567', 'Charlie Bishop', 'charlie.bishop@example.com', '1993-06-10', 33),
('a4f5e6d7-9876-4a98-d456-789012345678', 'Diana Queen', 'diana.queen@example.com', '1991-02-28', 27),
('b1a2c3d4-1111-4e00-e567-890123456789', 'Evan King', 'evan.king@example.com', '1989-07-19', 63),
('09f8e7d6-2222-49d9-f678-901234567890', 'Fay Pawn', 'fay.pawn@example.com', '1995-12-12', 15),
('d4c3b2a1-3333-40aa-a123-234567890123', 'George Knight', 'george.knight@example.com', '1988-08-08', 74),
('1122aabb-4444-41bb-b234-345678901234', 'Hannah Bishop', 'hannah.bishop@example.com', '1992-10-30', 36),
('2233bbcc-5555-42cc-c345-456789012345', 'Ian Rook', 'ian.rook@example.com', '1994-03-14', 29),
('3344ccdd-6666-43dd-d456-567890123456', 'Jenny Queen', 'jenny.queen@example.com', '1990-09-05', 41),
('4455ddee-7777-44ee-e567-678901234567', 'Kyle King', 'kyle.king@example.com', '1987-01-21', 91),
('5566eeff-8888-45ff-f678-789012345678', 'Laura Pawn', 'laura.pawn@example.com', '1996-05-17', 22),
('6677ffaa-9999-4600-a789-890123456789', 'Matt Knight', 'matt.knight@example.com', '1986-03-03', 48),
('7788aabb-aaaa-4711-b890-901234567890', 'Nina Bishop', 'nina.bishop@example.com', '1993-07-26', 53),
('8899bbcc-bbbb-4822-c901-012345678901', 'Oscar Rook', 'oscar.rook@example.com', '1985-12-01', 76),
('9900ccdd-cccc-4933-d012-123456789012', 'Paula Queen', 'paula.queen@example.com', '1989-06-08', 39),
('aabbddcc-dddd-4a44-e123-234567890123', 'Quinn King', 'quinn.king@example.com', '1992-04-25', 18),
('bbccffee-eeee-4b55-f234-345678901234', 'Rachel Pawn', 'rachel.pawn@example.com', '1991-11-09', 24),
('ccddeeff-ffff-4c66-a345-456789012345', 'Steve Knight', 'steve.knight@example.com', '1990-02-14', 66),
('ddeeff00-0000-4d77-b456-567890123456', 'Tina Bishop', 'tina.bishop@example.com', '1988-10-10', 51);