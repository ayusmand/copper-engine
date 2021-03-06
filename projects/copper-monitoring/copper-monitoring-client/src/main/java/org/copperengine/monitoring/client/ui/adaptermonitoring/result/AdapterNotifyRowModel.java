/*
 * Copyright 2002-2014 SCOOP Software GmbH
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
package org.copperengine.monitoring.client.ui.adaptermonitoring.result;

import java.util.Date;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import org.copperengine.monitoring.core.model.AdapterWfNotifyInfo;

public class AdapterNotifyRowModel {
    public final SimpleStringProperty correlationId;
    public final SimpleStringProperty message;
    public final SimpleObjectProperty<Date> timestamp;
    public final SimpleStringProperty adapterName;

    public AdapterNotifyRowModel(AdapterWfNotifyInfo adapterWfNotifyInfo) {
        correlationId = new SimpleStringProperty(adapterWfNotifyInfo.getCorrelationId());
        message = new SimpleStringProperty(adapterWfNotifyInfo.getMessage());
        timestamp = new SimpleObjectProperty<Date>(adapterWfNotifyInfo.getTimeStamp());
        adapterName = new SimpleStringProperty(adapterWfNotifyInfo.getAdapterName());
    }
}
