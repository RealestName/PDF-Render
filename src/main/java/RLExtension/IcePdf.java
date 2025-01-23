package RLExtension;

import org.icepdf.ri.common.MyAnnotationCallback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.common.views.DocumentViewControllerImpl;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;

public class IcePdf {

    private SwingController controller;
    private JPanel viewerComponentPanel;
    private boolean openDocument = false;

    public IcePdf() {
        buildPanel();
    }

    public void buildPanel() {

        controller = new SwingController();

        SwingViewBuilder factory = new SwingViewBuilder(controller);

        viewerComponentPanel = factory.buildViewerPanel();

        //Add interactive mouse link annotation support via callback
        controller.getDocumentViewController().setAnnotationCallback(
                new MyAnnotationCallback(controller.getDocumentViewController()));


    }

    public Component getComponent() {
        return viewerComponentPanel;
    }

    public void setPdfContent(byte[] respBytes, int bodyOffset,String description,String url) {
        if(openDocument) {
            controller.closeDocument();
            openDocument=false;
        }

        controller.openDocument(respBytes, bodyOffset, respBytes.length - bodyOffset,description,url);
        openDocument = true;

        controller.setPageFitMode(DocumentViewControllerImpl.PAGE_FIT_WINDOW_WIDTH,true);
    }

    public void loadPdf(byte[] pdfBytes) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfBytes)) {
            controller.openDocument(inputStream, null, null);
            //Set zoom to 100%
            //controller.setZoom(1f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}