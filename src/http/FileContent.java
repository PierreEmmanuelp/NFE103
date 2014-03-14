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

    private String mime;
    protected BufferedInputStream filestream;
    private int status;
    private Long FileLenght;
    //private String contenu;
    private String pCheminCible;

    // private String Contenu;
    public void openFile(String pCheminCible) {
        this.pCheminCible = pCheminCible;
        this.filestream = null;
        this.FileLenght = null;
        envoyerFichier();

    }

    private void envoyerFichier() {

        try {

            File file = new File(this.pCheminCible); // Ouverture file
            this.filestream = new BufferedInputStream(new FileInputStream(file)); //file dans stream
            setLength(file.length());

        } catch (FileNotFoundException e) {
            setStatus(404);
            this.filestream = null;
            System.out.println(e);
        } catch (Exception e) {
            this.filestream = null;
            setStatus(500);
            System.out.println(e);
        }
        setStatus(200);
        setMime();

    }

    public String getMime() {
        return mime;
    }

    private void setMime() {

        Mime Mime = new Mime();
        this.mime = Mime.extractTypeMime(this.pCheminCible);

    }

    private void setStatus(int code) {
        this.status = code;
    }

    public int getStatus() {
        return this.status;
    }

    public BufferedInputStream getFileContent() {
        return this.filestream;
    }

    private void setLength(Long length) {
        this.FileLenght = length;
    }

    /**
     * getLength : Retourne la taille du fichier
     *
     * @return Long taille du fichier pour construction du header
     */
    public Long getLength() {
        return this.FileLenght;
    }
}
