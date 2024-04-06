----------------------------------------------------------------------------
create table "User" (
	id SERIAL PRIMARY KEY,
	phone text UNIQUE,       -- номер телефона при регистрации
	email text,              -- почта при регистрации
	username text UNIQUE,           -- имя пользователя
	password text,
	pfp integer,                -- фото/аватарка
	gender boolean,             -- гендер
	birthdate date,          -- дата рождения, при регистрации
	topics integer[]         -- темы, которые интересны
)
----------------------------------------------------------------------------

----------------------------------------------------------------------------
create table Status (
	id SERIAL PRIMARY KEY,
	status bool
)
----------------------------------------------------------------------------

----------------------------------------------------------------------------
create table UserStatus (
	id SERIAL PRIMARY KEY,
	iduser integer REFERENCES "User",
	idstatus integer REFERENCES Status,
	timebeg  timestamp,
	timeend  timestamp
)
----------------------------------------------------------------------------

----------------------------------------------------------------------------
create table Topic (
	id SERIAL PRIMARY KEY,
	topic text
)
----------------------------------------------------------------------------

----------------------------------------------------------------------------
create table Entry (
	id SERIAL PRIMARY KEY,
	iduser integer REFERENCES "User", -- id автора поста
	posted datetime,                       -- опубликован?
	moderated bool,                    -- отмодерирован?
	"public" bool,                     -- публичный пост?
	topics integer[],                  -- под какие темы подходит
	"content" text                     -- текст поста 
)
----------------------------------------------------------------------------

----------------------------------------------------------------------------
create table "Comment" (
	id SERIAL PRIMARY KEY,
	identry integer REFERENCES Entry,   -- к какому посту относится
	iduser integer REFERENCES "User", -- id автора
	idanscomment integer,         -- id комментария, на который отвечают, если такой есть
	posted datetime,                       -- опубликован?
	сontent text,                      -- текст комментария
	moderated bool                     -- отмодерирован?
)
----------------------------------------------------------------------------
create table "Worker" (
	id SERIAL PRIMARY KEY,
	phone text UNIQUE,       -- номер телефона 
	email text,              -- почта 
	username text UNIQUE,           
    name text,
	password text,
	role as enum ('psychologist', 'moderator')
)
