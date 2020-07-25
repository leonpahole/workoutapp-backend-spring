create table "user" (id  bigserial not null, created_at timestamp, email varchar(255), enabled boolean not null, name varchar(255), password varchar(255), weight_unit varchar(255), primary key (id));
create table exercise (id  bigserial not null, category varchar(255) not null, created_at timestamp, name varchar(255), "author_id" int8, primary key (id));
create table exercise_performed (id  bigserial not null, exercise_order int4 not null, exercise_id int8 not null, workout_id int8 not null, primary key (id));
create table exercise_performed_set (id  bigserial not null, set_order int4 not null, repetitions int4, time int4, weight float8, exercise_performed_id int8 not null, primary key (id));
create table workout (id  bigserial not null, comment varchar(255), created_at timestamp not null, end_time TIME not null, name varchar(255), start_date DATE not null, start_time TIME not null, user_id int8 not null, primary key (id));
alter table exercise add constraint FKc4ud5a646t632iopb17ij5paq foreign key ("author_id") references "user";
alter table exercise_performed add constraint FKrj881ut55n9uj9yxvhgfsbhsj foreign key (exercise_id) references exercise;
alter table exercise_performed add constraint FKtcarw87vtyqlkgaqh5n61bo37 foreign key (workout_id) references workout;
alter table exercise_performed_set add constraint FKg2k336s8wst1v1454dunl9isb foreign key (exercise_performed_id) references exercise_performed;
alter table workout add constraint FKmf4d7k220j0blivd37f9g4qme foreign key (user_id) references "user";