create table workout_template (id  bigserial not null, created_at timestamp not null, description varchar(255), name varchar(255), user_id int8 not null, primary key (id));
create table workout_template_exercise (id  bigserial not null, rest int4, sequence_number int4 not null, exercise_id int8 not null, workout_template_id int8 not null, primary key (id));

alter table workout_template add constraint FKf847cympga43vxjxr9vre95iw foreign key (user_id) references "user";
alter table workout_template_exercise add constraint FKpt7i1c9vo9balfq5ob2ej58nm foreign key (exercise_id) references exercise;
alter table workout_template_exercise add constraint FK7bd9tcyrrxs9ve1dveoxejvs4 foreign key (workout_template_id) references workout_template;