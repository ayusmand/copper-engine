create table COP_AUDIT_TRAIL_EVENT_EXTENDED (
	SEQ_ID 					BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	OCCURRENCE				TIMESTAMP NOT NULL,
	CONVERSATION_ID 		VARCHAR(64) NOT NULL,
	LOGLEVEL				SMALLINT NOT NULL,
	CONTEXT					VARCHAR(128) NOT NULL,
	INSTANCE_ID				VARCHAR(128),
	CORRELATION_ID 			VARCHAR(128),
	TRANSACTION_ID 			VARCHAR(128),
	LONG_MESSAGE 			CLOB,
	MESSAGE_TYPE			VARCHAR(256),
	CUSTOM_VARCHAR			VARCHAR(256),
	CUSTOM_INT				SMALLINT,
	CUSTOM_TIMESTAMP		TIMESTAMP,
    PRIMARY KEY (SEQ_ID)
)