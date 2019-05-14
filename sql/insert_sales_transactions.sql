CREATE PROCEDURE insert_sales_transactions(IN cartId int, IN paymentToken varchar(50))
BEGIN

    INSERT INTO sales (email, movieId, quantity, saleDate)
    SELECT carts.email, carts.movieId, carts.quantity, CURDATE()
    FROM carts
    WHERE carts.id = cartId;

    DELETE FROM carts WHERE carts.id = cartId;

    INSERT INTO transactions (sId, token) VALUES (LAST_INSERT_ID(), paymentToken);

END;