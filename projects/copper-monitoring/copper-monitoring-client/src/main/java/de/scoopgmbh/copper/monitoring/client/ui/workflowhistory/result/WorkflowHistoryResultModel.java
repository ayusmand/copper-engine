/*
 * Copyright 2002-2013 SCOOP Software GmbH
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
package de.scoopgmbh.copper.monitoring.client.ui.workflowhistory.result;

import java.util.Date;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import de.scoopgmbh.copper.monitoring.core.model.WorkflowInstanceHistory;

public class WorkflowHistoryResultModel {
	public final SimpleObjectProperty<Date> timestamp;
	public final SimpleStringProperty  message;
	public final SimpleStringProperty instanceId;
	public final SimpleStringProperty classname;

	public WorkflowHistoryResultModel(WorkflowInstanceHistory workflowInstanceHistory) {
		this.timestamp = new SimpleObjectProperty<Date>(new Date(workflowInstanceHistory.getTimestamp()));
		this.message = new SimpleStringProperty(workflowInstanceHistory.getMessage());
		this.instanceId = new SimpleStringProperty(workflowInstanceHistory.getInstanceId());
		this.classname = new SimpleStringProperty(workflowInstanceHistory.getClassname());
	}
	
	
	
	
}