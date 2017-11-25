CREATE TABLE games (
  id             INT          NOT NULL,
  home_team      VARCHAR(100) NOT NULL,
  visitor_team   VARCHAR(100) NOT NULL,
  home_scored    INT          NOT NULL,
  visitor_scored INT          NOT NULL,
  played_at      DATE         NOT NULL,
  PRIMARY KEY (id)
);
