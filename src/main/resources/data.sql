insert into my_db.user_entity(user_name, password, user_authorities) values ('info@mail.com', '{bcrypt}$2a$12$7kvOrCilXAL1LvftUJqAdu0U499fWMjQdRUiCGc2lbE.lKxCOq22u', 'VIEW_INFO');
insert into my_db.user_entity(user_name, password, user_authorities) values ('admin@mail.com', '{bcrypt}$2a$12$7kvOrCilXAL1LvftUJqAdu0U499fWMjQdRUiCGc2lbE.lKxCOq22u', 'VIEW_ADMIN');
insert into my_db.user_entity(user_name, password, user_authorities) values ('full@mail.com', '{bcrypt}$2a$12$7kvOrCilXAL1LvftUJqAdu0U499fWMjQdRUiCGc2lbE.lKxCOq22u', 'VIEW_ADMIN;VIEW_INFO');

