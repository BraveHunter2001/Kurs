SELECT character_name, meeting_status_name, task_name,task_status_name
FROM charactres
INNER JOIN characters_tasks ON characters_tasks.character_id = charactres.character_id
INNER JOIN tasks ON characters_tasks.task_id = tasks.task_id
INNER JOIN task_status ON tasks.task_status_id = task_status.task_status_id
INNER JOIN meeting_status ON charactres.meeting_status_id = meeting_status.meeting_status_id;