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
package de.scoopgmbh.copper.monitoring.client.ui.worklowinstancedetail.result;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import de.java2html.converter.JavaSource2HTMLConverter;
import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.options.JavaSourceConversionOptions;
import de.scoopgmbh.copper.monitoring.client.adapter.GuiCopperDataProvider;
import de.scoopgmbh.copper.monitoring.client.form.FxmlController;
import de.scoopgmbh.copper.monitoring.client.form.filter.FilterResultController;
import de.scoopgmbh.copper.monitoring.client.ui.worklowinstancedetail.filter.WorkflowInstanceDetailFilterModel;

public class WorkflowInstanceDetailResultController implements Initializable, FilterResultController<WorkflowInstanceDetailFilterModel,WorkflowInstanceDetailResultModel>, FxmlController {
	GuiCopperDataProvider copperDataProvider;

	public WorkflowInstanceDetailResultController(GuiCopperDataProvider copperDataProvider) {
		super();
		this.copperDataProvider = copperDataProvider;
	}


    @FXML //  fx:id="errorInfo"
    private TextArea errorInfo; // Value injected by FXMLLoader

    @FXML //  fx:id="sourceView"
    private WebView sourceView; // Value injected by FXMLLoader


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert errorInfo != null : "fx:id=\"errorInfo\" was not injected: check your FXML file 'WorkflowInstanceDetailResult.fxml'.";
        assert sourceView != null : "fx:id=\"sourceView\" was not injected: check your FXML file 'WorkflowInstanceDetailResult.fxml'.";
  
        errorInfo.setStyle("-fx-font: 12px \"Courier New\"");
    
    }
    

	
	@Override
	public URL getFxmlRessource() {
		return getClass().getResource("WorkflowInstanceDetailResult.fxml");
	}

	@Override
	public void showFilteredResult(List<WorkflowInstanceDetailResultModel> filteredResult, WorkflowInstanceDetailFilterModel usedFilter) {
		if ( usedFilter.filteredWithworkflowInstanceInfo!=null){
			errorInfo.setText(usedFilter.filteredWithworkflowInstanceInfo.errorInfos.get());
		}
		
		
		// Create a reader of the raw input text
		StringReader stringReader = new StringReader("/** Simple Java2Html Demo */\r\n" + "public static int doThis(String text){ return text.length() + 2; }");

		// Parse the raw text to a JavaSource object
		JavaSource source = null;
		try {
			source = new JavaSourceParser().parse(stringReader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// Create a converter and write the JavaSource object as Html
		JavaSource2HTMLConverter converter = new JavaSource2HTMLConverter();
		StringWriter writer = new StringWriter();
		try {
			converter.convert(source, JavaSourceConversionOptions.getDefault(), writer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		sourceView.getEngine().loadContent(writer.toString());
	}

	@Override
	public List<WorkflowInstanceDetailResultModel> applyFilterInBackgroundThread(WorkflowInstanceDetailFilterModel filter) {
		return Arrays.asList(copperDataProvider.getWorkflowDetails(filter));
	}
	
	@Override
	public boolean canLimitResult() {
		return true;
	}

	@Override
	public void clear() {
		//TODO
	}
}