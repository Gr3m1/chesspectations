CREATE SCHEMA IF NOT EXISTS "chessdata";

CREATE TABLE IF NOT EXISTS chessdata.personal_details
(
    id VARCHAR(36) NOT NULL,
    player_name TEXT NOT NULL,
    email_address TEXT NOT NULL,
	date_of_birth DATE NOT NULL,
	games_played INT NOT NULL DEFAULT 0,
    CONSTRAINT personal_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS chessdata.ranking
(
    id VARCHAR(36) NOT NULL,
    player_id VARCHAR(36) NOT NULL,
    ranking int NOT NULL,

    CONSTRAINT ranking_pk PRIMARY KEY (id),
    FOREIGN KEY (player_id) REFERENCES chessdata.personal_details(id)
);

CREATE TABLE IF NOT EXISTS chessdata.matches
(
    id VARCHAR(36) NOT NULL,
    ebony_player_id VARCHAR(36) NOT NULL,
	ebony_rank INT NOT NULL,
    ivory_player_id VARCHAR(36) NOT NULL,
	ivory_rank INT NOT NULL,
	date_played DATE NOT NULL,
	winner VARCHAR(5) NOT NULL,

    CONSTRAINT matches_pk PRIMARY KEY (id),
    FOREIGN KEY (ebony_player_id) REFERENCES chessdata.personal_details(id),
    FOREIGN KEY (ivory_player_id) REFERENCES chessdata.personal_details(id)
);