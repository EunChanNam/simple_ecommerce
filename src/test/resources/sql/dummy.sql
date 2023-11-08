insert into item values (1000, null, 1, null, 'itemA');
insert into item values (2000, null, 2, null, 'itemB');
insert into item_detail values (0, 100, null, 1, 1, null, 'red', null, 'free');
insert into item_detail values (0, 100, null, 2, 1, null, 'blue', null, 'free');
insert into item_detail values (0, 100, null, 3, 2, null, 'blue', null, 'free');
insert into item_detail values (0, 100, null, 4, 2, null, 'yellow', null, 'free');

insert into product values (1000, null, 1, 1, null, 'brand', 'productA');
insert into product values (2000, null, 2, 1, null, 'brand', 'productB');
insert into promotion values (10, null, 1, null, 'promotionA');

insert into product_item values (null, 1, 1, 1, null);
insert into product_item values (null, 2, 2, 2, null);

insert into orders values (3, 10000, null, 1, 1, null, 1);
insert into orders values (3, 10000, null, 2, 2, null, 1);

insert into order_detail values (1, 1, 1);
insert into order_detail values (2, 3, 2);