
insert into users (username, password, enabled) values ('sam','$2a$12$J93aBznQ1wbb4ctW/uElsufWEs6z2FOJBaMOBb1UUZyrwt4QboSMe', 1);
insert into users (username, password, enabled) values ('rita','$2a$12$hsRR6aAI/jm4gf0b86MnO.xO..m6CNyTP77gq5egIlb4m.Z9UE9z2', 1);

insert into authorities (username, authority) values ('sam', 'ROLE_ADMIN');
insert into authorities (username, authority) values ('sam', 'ROLE_READ');

insert into authorities (username, authority) values ('rita', 'ROLE_READ');