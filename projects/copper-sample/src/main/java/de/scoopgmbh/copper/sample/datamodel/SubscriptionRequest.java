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
package de.scoopgmbh.copper.sample.datamodel;

import java.io.Serializable;

public class SubscriptionRequest implements Serializable {

	private static final long serialVersionUID = -2745660355362050190L;
	
	private String msisdn;
	private String subscriptionTemplateId;
	private String contractNumber;
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getSubscriptionTemplateId() {
		return subscriptionTemplateId;
	}
	public void setSubscriptionTemplateId(String subscriptionTemplateId) {
		this.subscriptionTemplateId = subscriptionTemplateId;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	
}