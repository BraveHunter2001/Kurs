CREATE TABLE meeting_status(
	id BIGSERIAL NOT NULL PRIMARY KEY ,
	meeting_status_name VARCHAR(100) NOT NULL
);

CREATE TABLE task_status(
	id BIGSERIAL NOT NULL PRIMARY KEY ,
	task_status_name VARCHAR(100) NOT NULL
);

CREATE TABLE characters(
	id BIGSERIAL NOT NULL PRIMARY KEY ,
	character_name VARCHAR(100) NOT NULL,
	apperance VARCHAR(100) NOT NULL,
	location VARCHAR(100) NOT NULL,
	meeting_status_id BIGSERIAL NOT NULL REFERENCES meeting_status
);

CREATE TABLE tasks(
	id BIGSERIAL NOT NULL PRIMARY KEY ,
	task_name VARCHAR(100) NOT NULL,
	task_status_id BIGSERIAL NOT NULL REFERENCES task_status
);

CREATE TABLE characters_tasks(
	character_id BIGSERIAL NOT NULL REFERENCES characters,
	task_id BIGSERIAL NOT NULL REFERENCES tasks,
	PRIMARY KEY (character_id, task_id)
);