DECLARE @current_timestamp DATETIME = (SELECT CURRENT_TIMESTAMP)

INSERT INTO [account] ([last_updated_time], [balance]) VALUES
    (@current_timestamp, 0),
    (@current_timestamp, 100),
    (@current_timestamp, 200),
    (@current_timestamp, 300),
    (@current_timestamp, 400)
