ALTER TABLE chessdata.matches
    ADD COLUMN ebony_name VARCHAR(255),
    ADD COLUMN ivory_name VARCHAR(255);

UPDATE chessdata.matches m
SET ebony_name = COALESCE(p1.player_name, 'Unknown'),
    ivory_name = COALESCE(p2.player_name, 'Unknown')
FROM chessdata.player_details p1
JOIN chessdata.player_details p2 ON TRUE
WHERE p1.id = m.ebony_player_id
  AND p2.id = m.ivory_player_id;