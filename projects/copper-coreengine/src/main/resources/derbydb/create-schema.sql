create table COP_WORKFLOW_INSTANCE  (
   ID           		VARCHAR(128) not null,
   STATE                SMALLINT not null,
   PRIORITY             SMALLINT not null,
   LAST_MOD_TS          TIMESTAMP not null,
   PPOOL_ID      		VARCHAR(32) not null,
   DATA					VARCHAR(32672) not null,
   CS_WAITMODE			SMALLINT,
   MIN_NUMB_OF_RESP		SMALLINT,
   NUMB_OF_WAITS		SMALLINT,
   TIMEOUT				TIMESTAMP,
   CREATION_TS			TIMESTAMP not null,
   PRIMARY KEY (ID)
);

create table COP_WORKFLOW_INSTANCE_ERROR (
   WORKFLOW_INSTANCE_ID		VARCHAR(128)	not null,
   "EXCEPTION"				VARCHAR(32672)	not null,
   ERROR_TS     	   		TIMESTAMP       not null
);

create index IDX_COP_WFID_WFID on COP_WORKFLOW_INSTANCE_ERROR (
   WORKFLOW_INSTANCE_ID
);

--
-- RESPONSE
--
create table COP_RESPONSE  (
   CORRELATION_ID	VARCHAR(128) not null,
   RESPONSE_TS		TIMESTAMP not null,
   RESPONSE			VARCHAR(32672),
   PRIMARY KEY (CORRELATION_ID)
);

 
--
-- WAIT
--
create table COP_WAIT (
   	CORRELATION_ID			VARCHAR(128) not null,
   	WORKFLOW_INSTANCE_ID  	VARCHAR(128) not null,
	MIN_NUMB_OF_RESP		SMALLINT not null,
	TIMEOUT_TS				TIMESTAMP,
   	STATE					SMALLINT not null,
    PRIORITY            	SMALLINT not null,
    PPOOL_ID      			VARCHAR(32) not null,
    PRIMARY KEY (CORRELATION_ID)
);


create index IDX_COP_WAIT_WFI_ID on COP_WAIT (
   WORKFLOW_INSTANCE_ID
);

--
-- QUEUE
--
create table COP_QUEUE (
   PPOOL_ID      		VARCHAR(32)						not null,
   PRIORITY             SMALLINT                        not null,
   LAST_MOD_TS          TIMESTAMP                       not null,
   WORKFLOW_INSTANCE_ID	VARCHAR(128) 					not null,
   PRIMARY KEY (WORKFLOW_INSTANCE_ID)
);


create table COP_AUDIT_TRAIL_EVENT (
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
    PRIMARY KEY (SEQ_ID)
);