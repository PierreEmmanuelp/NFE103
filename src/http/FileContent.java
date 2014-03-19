package http;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Classe permettant la creation du contenu de la reponse.
 *
 * @version 1.999
 * @author htruchard + pierrot
 * @return contenu
 */
public class FileContent extends Content {

    /**
     * String pour recevoir type mime.
     */
    private String mime;
    /**
     * Flux "beffurisé" de la lecture du fichier.
     */
    private BufferedInputStream filestream;
    /**
     * Code de retour en fonction de la disponibilité du fichier.
     */
    private int status;
    /**
     * Taille du fichier.
     */
    private Long fileLenght;
    /**
     * Chemin du fichier.
     */
    private String pCheminCible;

    /**
     * Ouverture du ficher.
     *
     * @param cheminCible Path /machin/fichier | c:\bidule\machin\fichier
     */
    public final void openFile(final String cheminCible) {
        this.pCheminCible = cheminCible;
        this.filestream = null;
        this.fileLenght = null;
        envoyerFichier();

    }

    /**
     * envoyerFichier : envoie le fichier.
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
     * getMime : Retrouver le type mime du fichier.
     *
     * @return String
     */
    public final String getMime() {
        return mime;
    }

    /**
     * setMime : Setting type mime.
     */
    private void setMime() {

        this.mime = Mime.extractTypeMime(this.pCheminCible);

    }

    /**
     * setStatus : Set du code retour.
     *
     * @param code int
     */
    private void setStatus(final int code) {
        this.status = code;
    }

    /**
     * getStatus : Récupère le code retour.
     *
     * @return int
     */
    public final int getStatus() {
        return this.status;
    }

    /**
     * BufferedInputStream : Retourne le flux de la lecture fichier.
     *
     * @return BufferedInputStream
     */
    public final BufferedInputStream getFileContent() {
        return this.filestream;
    }

    /**
     * setLength : Set de la taille du fichier.
     *
     * @param length Long
     */
    private void setLength(final Long length) {
        this.fileLenght = length;
    }

    /**
     * getLength : Retourne la taille du fichier.
     *
     * @return Long taille du fichier pour construction du header
     */
    public final Long getLength() {
        return this.fileLenght;
    }
}
