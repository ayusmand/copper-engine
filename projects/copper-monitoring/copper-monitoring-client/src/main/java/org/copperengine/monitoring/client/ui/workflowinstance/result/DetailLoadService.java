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
package org.copperengine.monitoring.client.ui.workflowinstance.result;

import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import org.copperengine.monitoring.client.form.FxmlForm;
import org.copperengine.monitoring.client.form.filter.FilterResultController;
import org.copperengine.monitoring.client.form.issuereporting.IssueReporter;
import org.copperengine.monitoring.client.ui.workflowinstance.filter.WorkflowInstanceFilterModel;
import org.copperengine.monitoring.client.ui.worklowinstancedetail.filter.WorkflowInstanceDetailFilterModel;
import org.copperengine.monitoring.client.ui.worklowinstancedetail.result.WorkflowInstanceDetailResultModel;
import org.copperengine.monitoring.client.util.ComponentUtil;

public final class DetailLoadService extends Service<Void> {
    private WorkflowInstanceResultModel workflowInstanceResultModel;
    private final StackPane stackDetailPane;
    private final FxmlForm<FilterResultController<WorkflowInstanceDetailFilterModel, WorkflowInstanceDetailResultModel>> detailForm;
    private final WorkflowInstanceFilterModel usedFilter;
    private final IssueReporter issueReporter;

    public DetailLoadService(
            WorkflowInstanceFilterModel usedFilter,
            WorkflowInstanceResultModel workflowInstanceResultModel,
            StackPane stackDetailPane,
            FxmlForm<FilterResultController<WorkflowInstanceDetailFilterModel,
                    WorkflowInstanceDetailResultModel>> detailForm,
            IssueReporter issueReporter) {
        this.workflowInstanceResultModel = workflowInstanceResultModel;
        this.stackDetailPane = stackDetailPane;
        this.detailForm = detailForm;
        this.usedFilter = usedFilter;
        this.issueReporter = issueReporter;
    }

    public void setWorkflowInstanceResultModel(WorkflowInstanceResultModel workflowInstanceResultModel) {
        this.workflowInstanceResultModel = workflowInstanceResultModel;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            final Node indicator = ComponentUtil.createProgressIndicator();
            private WorkflowInstanceDetailFilterModel filter;
            private List<WorkflowInstanceDetailResultModel> result;

            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        stackDetailPane.getChildren().add(indicator);
                    }
                });
                filter = new WorkflowInstanceDetailFilterModel(workflowInstanceResultModel.id.getValue(), usedFilter.selectedEngine.get());
                filter.workflowInstanceId.setValue(workflowInstanceResultModel.id.getValue());
                try {
                    result = detailForm.getController().applyFilterInBackgroundThread(filter);
                } catch (Exception e) {
                    issueReporter.reportError(e);
                }
                return null;
            }

            @Override
            protected void succeeded() {
                detailForm.getController().showFilteredResult(result, filter);
                stackDetailPane.getChildren().remove(indicator);
                final Throwable exception = getException();
                if (exception != null) {
                    throw new RuntimeException(exception);
                }
                super.succeeded();
            }

            @Override
            protected void failed() {
                stackDetailPane.getChildren().remove(indicator);
                final Throwable exception = getException();
                if (exception != null) {
                    throw new RuntimeException(exception);
                }
                super.failed();
            }

        };
    }
}