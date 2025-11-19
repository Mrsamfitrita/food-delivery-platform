CREATE TABLE public.restaurant (
    id bigserial NOT NULL,
    name varchar(255) NOT NULL,
    cuisine_type varchar(100) NOT NULL,
    location varchar(255) NOT NULL,
    created_at timestamp DEFAULT now() NOT NULL,
    updated_at timestamp DEFAULT now() NOT NULL,
    CONSTRAINT restaurant_pk PRIMARY KEY (id)
);