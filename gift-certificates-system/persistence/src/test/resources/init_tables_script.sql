INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (1, 'Excursion', 'Excursion immersion in the gentry culture with a lunch of national dishes', 2999.99, 5, '2018-11-18 17:33:42', '2018-11-20 7:33:42');
INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (2, 'Diving with dolphins', 'Diving with dolphins - an incredible meeting with the underwater world and its inhabitants', 1500.00, 1, '2020-03-20 16:34:49', '2020-05-20 16:34:49');
INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (3, 'Horse ride', 'Horseback ride for lovers - a Hollywood-style date', 500.00, 2, '2017-05-22 12:46:31', '2020-03-20 16:34:49');
INSERT INTO tag (id, name) VALUES (1, 'travel');
INSERT INTO tag (id, `name`) VALUES (2, 'gift');
INSERT INTO gift_certificate_tag_connection (gift_certificate_id, tag_id) VALUES (1, 1);
INSERT INTO gift_certificate_tag_connection (gift_certificate_id, tag_id) VALUES (1, 2);
INSERT INTO gift_certificate_tag_connection (gift_certificate_id, tag_id) VALUES (2, 2);