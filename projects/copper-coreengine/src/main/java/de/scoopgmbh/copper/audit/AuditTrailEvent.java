/*
 * Copyright 2002-2012 SCOOP Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.scoopgmbh.copper.audit;

import java.io.Serializable;
import java.util.Date;

public class AuditTrailEvent implements Serializable {

	private static final long serialVersionUID = -6195270904233529021L;
	
	int logLevel;
	Date occurrence;
	String conversationId;
	String context;
	String instanceId;
	String correlationId;
	String message;
	String transactionId;
	String messageType;
	
	public AuditTrailEvent(int logLevel, Date occurrence, String conversationId, String context, String instanceId, String correlationId, String transactionId, String message, String messageType) {
		super();
		this.logLevel = logLevel;
		this.occurrence = occurrence;
		this.conversationId = conversationId;
		this.context = context;
		this.instanceId = instanceId;
		this.correlationId = correlationId;
		this.message = message;
		this.transactionId = transactionId;
		this.messageType = messageType;
	}

	public int getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	public Date getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(Date occurrence) {
		this.occurrence = occurrence;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	
	
}
