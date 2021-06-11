
CREATE TABLE characters(
	id INT NOT NULL PRIMARY KEY ,
	name VARCHAR(100) NOT NULL,
	apperance VARCHAR(100) NOT NULL,
	location VARCHAR(100) NOT NULL,
	meetingstatus VARCHAR(100) NOT NULL
);

CREATE TABLE tasks(
	id INT NOT NULL PRIMARY KEY ,
	name VARCHAR(100) NOT NULL,
	taskstatus VARCHAR(100) NOT NULL
);

CREATE TABLE characters_tasks(
	character_id INT NOT NULL REFERENCES characters,
	task_id INT NOT NULL REFERENCES tasks,
	PRIMARY KEY (character_id, task_id)
);