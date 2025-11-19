CREATE TABLE public.dish (
    id bigserial NOT NULL,
    name varchar(255) NOT NULL,
    description text NULL,
    price numeric(10, 2) NOT NULL,
    restaurant_id int8 NOT NULL,
    created_at timestamp DEFAULT now() NOT NULL,
    updated_at timestamp DEFAULT now() NOT NULL,
    CONSTRAINT dish_pk PRIMARY KEY (id)
);

ALTER TABLE public.dish ADD CONSTRAINT fk_dish_restaurant
    FOREIGN KEY (restaurant_id)
    REFERENCES public.restaurant(id)
    ON DELETE CASCADE ON UPDATE CASCADE;