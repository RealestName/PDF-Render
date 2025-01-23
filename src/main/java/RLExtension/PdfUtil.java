package RLExtension;

public class PdfUtil {
    public static boolean isPdfFile(byte[] responseBytes, int bodyOffset) {
        if (responseBytes == null || responseBytes.length < bodyOffset + 4) {
            return false;
        }
        return responseBytes[bodyOffset] == (byte) '%' &&
                responseBytes[bodyOffset + 1] == (byte) 'P' &&
                responseBytes[bodyOffset + 2] == (byte) 'D' &&
                responseBytes[bodyOffset + 3] == (byte) 'F';
    }
}