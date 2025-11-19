INSERT INTO public."role" (id, "name") VALUES (1, 'ADMIN');
INSERT INTO public."role" (id, "name") VALUES (2, 'USER');

-- Обновление последовательности (Sequence) bigserial после ручных вставок
-- Это гарантирует, что следующий auto-generated ID будет корректным
SELECT setval('role_id_seq', (SELECT MAX(id) FROM public."role"));