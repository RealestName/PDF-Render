package RLExtension;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.ui.Selection;
import burp.api.montoya.ui.editor.extension.EditorCreationContext;
import burp.api.montoya.ui.editor.extension.ExtensionProvidedHttpResponseEditor;

import java.awt.*;

public class CustomHttpResponseEditorTab implements ExtensionProvidedHttpResponseEditor {
    MontoyaApi api;
    Logging logging;
    EditorCreationContext creationContext;
    IcePdf propertyPanel;

    public CustomHttpResponseEditorTab(MontoyaApi api, EditorCreationContext creationContext) {
        this.api = api;
        this.propertyPanel = new IcePdf();
        this.creationContext = creationContext;

        api.userInterface().applyThemeToComponent(propertyPanel.getComponent());
    }

    @Override
    public HttpResponse getResponse() {
        return null;
    }

    @Override
    public void setRequestResponse(HttpRequestResponse httpRequestResponse) {
        if (httpRequestResponse == null || httpRequestResponse.response() == null) {
            return; // No response available
        }

        // Get the raw response body
        HttpResponse response = httpRequestResponse.response();
        int bodyOffset = response.bodyOffset(); // Determine where the body starts
        byte[] rawBytes = response.toByteArray().getBytes();

        if (rawBytes.length > bodyOffset) {
            // Extract the body (PDF content)
            byte[] pdfContent = new byte[rawBytes.length - bodyOffset];
            System.arraycopy(rawBytes, bodyOffset, pdfContent, 0, pdfContent.length);

            // Load the PDF content into IcePdf
            try {
                propertyPanel.loadPdf(pdfContent);
            } catch (Exception e) {
                api.logging().logToError("Failed to load PDF: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean isEnabledFor(HttpRequestResponse httpRequestResponse) {
        // Extract the response from the HttpRequestResponse object
        HttpResponse response = httpRequestResponse.response();

        // Ensure the response is not null
        if (response == null) {
            return false; // Disable the tab if there's no response
        }

        // Get the body offset and body bytes
        int bodyOffset = response.bodyOffset();
        byte[] rawBytes = response.toByteArray().getBytes(); // Full response including headers and body

        // Check if the response body starts with the PDF signature
        return PdfUtil.isPdfFile(rawBytes, bodyOffset);
    }

    @Override
    public String caption() {
        return "PDF";
    }

    @Override
    public Component uiComponent() {
        return propertyPanel.getComponent();
    }

    @Override
    public Selection selectedData() {
        return null;
    }

    @Override
    public boolean isModified() {
        return false;
    }
}
