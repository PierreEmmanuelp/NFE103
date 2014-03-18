package http;

import debug.Trace;
import org.apache.tika.Tika;

/**
 *
 * @author Pierre-Emmanuel Pourquier, Benjamin Pierrot
 * @version 2.0
 */
public final class Mime {

    /**
     * constructeur.
     */
    protected Mime() {
        super();
    }

    /**
     * @param pFichier String Url du fichier en entrée.
     * @return String Type mime
     */
    public static String extractTypeMime(final String pFichier) {

        String mediaType = "";
        Tika tika = new Tika();

        try {
            mediaType = tika.detect(pFichier);
            Trace.trace(mediaType);
        } catch (Exception e) {
            Trace.trace(e.getMessage());
        }

        if (mediaType.equals("application/octet-stream")) {
            mediaType = "text/html";
        }
        return mediaType;
    }
}
