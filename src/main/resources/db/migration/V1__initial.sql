create table users (
  id serial primary key,
  username varchar(255) unique ,
  password varchar(255),
  email varchar(255) unique ,
  bio text,
  image varchar(511)
);

create table articles (
  id serial primary key,
  user_id varchar(255),
  slug varchar(255) unique,
  title varchar(255),
  description text,
  body text,
  created_at timestamp not null,
  updated_at timestamp not null default current_timestamp
);

create table article_favorites (
  article_id bigint not null references articles(id),
  user_id bigint not null references users(id),
  primary key(article_id, user_id)
);

create table follows (
  user_id bigint not null references users(id),
  followed_user_id bigint not null references users(id)
);

create table tags (
  id bigint primary key,
  name varchar(255)
);

create table article_tags (
  article_id bigint not null references articles(id),
  tag_id bigint not null references tags(id)
);

create table comments (
  id serial primary key,
  body text,
  article_id bigint not null references articles(id),
  user_id bigint not null references users(id),
  created_at timestamp not null,
  updated_at timestamp not null default current_timestamp
);
