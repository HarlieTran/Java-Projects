INSERT INTO "STATUS" (ID, VERSION, NAME) VALUES
                                             (1, 1, 'In Stock'),
                                             (2, 1, 'Out of Stock'),
                                             (3, 1, 'Discontinued'),
                                             (4, 1, 'Backordered'),
                                             (5, 1, 'Pre-Order');

INSERT INTO "COMPANY" (ID, VERSION, NAME) VALUES
                                              (6, 1, 'Phillips Van Heusen Corp.'),
                                              (7, 1, 'Avaya Inc.'),
                                              (8, 1, 'Laboratory Corporation of America Holdings'),
                                              (9, 1, 'AutoZone, Inc.'),
                                              (10, 1, 'Linens ''n Things Inc.');

INSERT INTO PRODUCT (ID, VERSION, NAME, DESCRIPTION, PRICE, IMAGE_URL, COMPANY_ID, STATUS_ID)
VALUES
    (11, 1, 'Vanilla Soy Candles', '11.6oz, 70 Hours Burning Time, 100% Natural Organic Soy Wax', 999.99, 'https://i.pinimg.com/474x/9e/68/07/9e6807da2355905fad499e6a641f4328.jpg', 8, 1),
    (12, 1, 'Lemon Vanilla Candles', '11.6oz 70 Hour Long Lasting Candles, Stress Relief Candle Gifts for Women and Men', 1499.99, 'https://i.pinimg.com/474x/59/46/b0/5946b02c553a584ce0d8b0b847321760.jpg', 7, 2),
    (13, 1, 'Jasmine & Wood Candles', '11.6oz Natural Soy Wax Fragrance, 70 Hour Burn Time, 2 Wick Aromatherapy, Jar Candle Gift', 199.99, 'https://i.pinimg.com/474x/30/cc/94/30cc947fbe046c8aa0ed5b837d866bd5.jpg', 6, 3),
    (14, 1, 'Lavender ＆ Cedar Candle', '9oz Scented Candles for Men for Home, Soy Candle Gifts for Men', 299.99, 'https://i.pinimg.com/474x/6b/06/63/6b0663d344612449e4ec78956414050f.jpg', 10, 3),
    (15, 1, 'Lavender Wood Candles', '8 Oz Scented Candles for Home Scented, 45 Hour Burn Soy Candle', 129.99, 'https://i.pinimg.com/474x/b2/3c/9b/b23c9b7a8db279b7c7d40638e8e42ac6.jpg', 6, 4),
    (16, 1, 'Coffee Candles', '19.4oz, 110 Hours Burning Time, Organic Natural Soy Wax Candle', 399.99, 'https://i.pinimg.com/474x/82/1a/63/821a63b0c61fe643321d35f2cc90ca7c.jpg', 10, 2),
    (17, 1, 'Sandalwood Candles', '150 Hours Long Lasting, 35oz,Extra Large 3 Wick Natural Soy Candles', 129.99, 'https://i.pinimg.com/474x/21/53/38/215338280de93b0fbdd88cc4e711f48a.jpg', 8, 1),
    (18, 1, 'Cedarwood Candles', '11.6oz 2 Wick Cotton Highly Scented Candle, 70 Hours Long Lasting Candle', 99.99, 'https://i.pinimg.com/474x/33/9f/a0/339fa05868552054c3d0b65a962ef083.jpg', 8, 5)


