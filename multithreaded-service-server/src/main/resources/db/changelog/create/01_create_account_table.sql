CREATE TABLE [account]
(
    [id] bigint NOT NULL IDENTITY (1, 1),
    [last_updated_time] datetime NOT NULL,
    [balance] bigint NOT NULL,
    PRIMARY KEY ([id])
)
