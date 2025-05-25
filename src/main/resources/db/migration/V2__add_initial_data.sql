ALTER TABLE chessdata.personal_details
    RENAME TO player_details;

INSERT INTO chessdata.player_details (id, player_name, email_address, date_of_birth) VALUES
('e1f2c7d1-1234-4b56-a123-1a2b3c4d5e01', 'Alice Knight', 'alice.knight@example.com', '1990-04-15'),
('f7d3a2b9-4321-4f98-b234-5c6d7e8f9012', 'Bob Rook', 'bob.rook@example.com', '1985-11-23'),
('c3b2a1e0-7890-4c12-c345-678901234567', 'Charlie Bishop', 'charlie.bishop@example.com', '1993-06-10'),
('a4f5e6d7-9876-4a98-d456-789012345678', 'Diana Queen', 'diana.queen@example.com', '1991-02-28'),
('b1a2c3d4-1111-4e00-e567-890123456789', 'Evan King', 'evan.king@example.com', '1989-07-19'),
('09f8e7d6-2222-49d9-f678-901234567890', 'Fay Pawn', 'fay.pawn@example.com', '1995-12-12'),
('d4c3b2a1-3333-40aa-a123-234567890123', 'George Knight', 'george.knight@example.com', '1988-08-08'),
('1122aabb-4444-41bb-b234-345678901234', 'Hannah Bishop', 'hannah.bishop@example.com', '1992-10-30'),
('2233bbcc-5555-42cc-c345-456789012345', 'Ian Rook', 'ian.rook@example.com', '1994-03-14'),
('3344ccdd-6666-43dd-d456-567890123456', 'Jenny Queen', 'jenny.queen@example.com', '1990-09-05'),
('4455ddee-7777-44ee-e567-678901234567', 'Kyle King', 'kyle.king@example.com', '1987-01-21'),
('5566eeff-8888-45ff-f678-789012345678', 'Laura Pawn', 'laura.pawn@example.com', '1996-05-17'),
('6677ffaa-9999-4600-a789-890123456789', 'Matt Knight', 'matt.knight@example.com', '1986-03-03'),
('7788aabb-aaaa-4711-b890-901234567890', 'Nina Bishop', 'nina.bishop@example.com', '1993-07-26'),
('8899bbcc-bbbb-4822-c901-012345678901', 'Oscar Rook', 'oscar.rook@example.com', '1985-12-01'),
('9900ccdd-cccc-4933-d012-123456789012', 'Paula Queen', 'paula.queen@example.com', '1989-06-08'),
('aabbddcc-dddd-4a44-e123-234567890123', 'Quinn King', 'quinn.king@example.com', '1992-04-25'),
('bbccffee-eeee-4b55-f234-345678901234', 'Rachel Pawn', 'rachel.pawn@example.com', '1991-11-09'),
('ccddeeff-ffff-4c66-a345-456789012345', 'Steve Knight', 'steve.knight@example.com', '1990-02-14'),
('ddeeff00-0000-4d77-b456-567890123456', 'Tina Bishop', 'tina.bishop@example.com', '1988-10-10');


INSERT INTO chessdata.ranking (id, player_id, ranking) VALUES
('8e35a755-d74b-4be0-b099-8c480b24456e', '5566eeff-8888-45ff-f678-789012345678', 1),
('08a5c9e9-e093-4d56-a0b9-410fa4eebc59', 'a4f5e6d7-9876-4a98-d456-789012345678', 2),
('31abf7bb-38e0-4d7f-a057-858ed859fb84', '6677ffaa-9999-4600-a789-890123456789', 3),
('61c4dbe9-06ae-41e5-aef6-310d91de6401', '3344ccdd-6666-43dd-d456-567890123456', 4),
('5a50d803-fcd0-4fdf-9ef8-fb79a8a239b3', 'ccddeeff-ffff-4c66-a345-456789012345', 5),
('6d6a6dc7-3d38-41e6-a6b2-9c08d8d1c138', '4455ddee-7777-44ee-e567-678901234567', 6),
('d8ae918f-b27e-4ec4-8102-3e59a29dd712', 'f7d3a2b9-4321-4f98-b234-5c6d7e8f9012', 7),
('a73dded2-2288-4cb2-9a66-03798f17957f', '2233bbcc-5555-42cc-c345-456789012345', 8),
('95f0e385-020c-4f43-9f79-75c0c1559a34', '9900ccdd-cccc-4933-d012-123456789012', 9),
('5123bcd1-08cb-460e-bf77-622ab2792c43', '1122aabb-4444-41bb-b234-345678901234', 10),
('4f8a5ff9-3a3b-4451-9083-9f123c77088d', '09f8e7d6-2222-49d9-f678-901234567890', 11),
('bbca19a7-8cbe-4971-83ae-670d6fc32e3d', 'aabbddcc-dddd-4a44-e123-234567890123', 12),
('3c28bb42-91f4-4fe7-a2ec-9cabcdfba22c', 'c3b2a1e0-7890-4c12-c345-678901234567', 13),
('d76e4ab0-066e-42ae-8cf9-e10eabc37061', 'bbccffee-eeee-4b55-f234-345678901234', 14),
('93f614e7-dfb4-42ef-b3d6-3094c0b4e19b', '7788aabb-aaaa-4711-b890-901234567890', 15),
('ef12379d-9b75-4c4d-a114-55ffb9793a33', 'd4c3b2a1-3333-40aa-a123-234567890123', 16),
('9a3cfe22-1d25-4b4a-95c2-0afaa12c83b1', 'e1f2c7d1-1234-4b56-a123-1a2b3c4d5e01', 17),
('cf2ff24d-d1d4-4ae1-9949-d4be34b5a527', 'ddeeff00-0000-4d77-b456-567890123456', 18),
('723bd3f9-5072-4218-bc81-3e0c9b93e0f8', '8899bbcc-bbbb-4822-c901-012345678901', 19),
('7d028b61-d09b-4b94-9620-ff985fc8aef9', 'b1a2c3d4-1111-4e00-e567-890123456789', 20);
