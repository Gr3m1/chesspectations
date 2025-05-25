CREATE INDEX IF NOT EXISTS player_name_idx ON chessdata.player_details (player_name);
CREATE INDEX IF NOT EXISTS player_email_idx ON chessdata.player_details (email_address);

CREATE INDEX IF NOT EXISTS ranking_idx ON chessdata.ranking (ranking);