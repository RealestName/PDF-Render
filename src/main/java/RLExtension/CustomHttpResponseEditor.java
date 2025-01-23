package RLExtension;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.ui.editor.EditorOptions;
import burp.api.montoya.ui.editor.extension.EditorCreationContext;
import burp.api.montoya.ui.editor.extension.ExtensionProvidedHttpResponseEditor;
import burp.api.montoya.ui.editor.extension.HttpResponseEditorProvider;

public class CustomHttpResponseEditor implements HttpResponseEditorProvider {

    MontoyaApi api;
    Logging logging;
    EditorCreationContext creationContext;
    IcePdf propertyPanel;

    public CustomHttpResponseEditor(MontoyaApi api, EditorCreationContext creationContext) {
        this.api = api;
        this.creationContext = creationContext;

        this.logging = api.logging();
        //requestEditorTab = api.userInterface().applyThemeToComponent(propertyPanel.getComponent());
    }

    public CustomHttpResponseEditor(MontoyaApi api) {
        this.api = api;
    }

    @Override
    public ExtensionProvidedHttpResponseEditor provideHttpResponseEditor(EditorCreationContext editorCreationContext) {
        return new CustomHttpResponseEditorTab(api, editorCreationContext);
    }
}
