package http;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Classe permettant la creation du contenu de la reponse
 *
 * @version 1.999
 * @author htruchard + pierrot
 * @return contenu
 */
public class FileContent extends Content {
    /**
     *
     */
    private String mime;
     /**
     *
     */
    private BufferedInputStream filestream;
     /**
     *
     */
    private int status;
    /**
     *
     */
    private Long FileLenght;
    /**
     *
     */
    private String pCheminCible;

    // private String Contenu;
    public void openFile(String pCheminCible) {
        this.pCheminCible = pCheminCible;
        this.filestream = null;
        this.FileLenght = null;
        envoyerFichier();

    }

    /**
     *
     */
    private void envoyerFichier() {

        try {

            File file = new File(this.pCheminCible); // Ouverture file

            if (file.exists() && file.canRead()) {
                //file dans stream
           this.filestream = new BufferedInputStream(new FileInputStream(file));
                setLength(file.length());
                setStatus(200);
                setMime();
            } else {
                if (file.exists() && !file.canRead()) {
                    setStatus(403);
                } else {
                    setStatus(404);
                }
            }
        } catch (FileNotFoundException e) {
            setStatus(404);
            this.filestream = null;
            System.out.println(e);
        } catch (Exception e) {
            this.filestream = null;
            setStatus(500);
            System.out.println(e);
        }


    }

    /**
     *
     * @return String
     */
    public final String getMime() {
        return mime;
    }

    /**
     *
     */
    private void setMime() {

        this.mime = Mime.extractTypeMime(this.pCheminCible);

    }

    /**
     *
     * @param code int
     */
    private void setStatus(final int code) {
        this.status = code;
    }

    /**
     *
     * @return int
     */
    public final int getStatus() {
        return this.status;
    }

    /**
     *
     * @return BufferedInputStream
     */
    public final BufferedInputStream getFileContent() {
        return this.filestream;
    }

    /**
     *
     * @param length Long
     */
    private void setLength(final Long length) {
        this.FileLenght = length;
    }

    /**
     * getLength : Retourne la taille du fichier.
     *
     * @return Long taille du fichier pour construction du header
     */
    public final Long getLength() {
        return this.FileLenght;
    }
}
