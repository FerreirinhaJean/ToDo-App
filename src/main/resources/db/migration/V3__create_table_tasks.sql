CREATE TABLE tasks
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    status      VARCHAR(80)  NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NOT NULL,
    user_id     UUID         NOT NULL,
    CONSTRAINT tasks_users_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT status_check CHECK (status IN ('PENDING', 'COMPLETED'))
);