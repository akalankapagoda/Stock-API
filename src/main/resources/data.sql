TRUNCATE TABLE stock;

INSERT INTO stock
    (id, current_price, last_updated, name)
VALUES
	(1, 100, now(), 'Company1'),
	(2, 50, now(), 'Company2'),
	(3, 300, now(), 'Company3'),
	(4, 150, now(), 'Company4');

UPDATE hibernate_sequence
    SET next_val = 5;