CREATE TABLE IF NOT EXISTS DATA.USERS (
                                             chatID varchar PRIMARY KEY NOT NULL,
                                             token varchar,
                                             replyToCommand varchar,
                                             email varchar
);

CREATE TABLE IF NOT EXISTS DATA.DICTIONARY (
                                          word varchar PRIMARY KEY NOT NULL,
                                          translation varchar,
                                          audioUrl varchar,
                                          countOfRightAnswer integer

);