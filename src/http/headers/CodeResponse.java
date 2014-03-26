package http.headers;

import http.FileContent;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ben
 */
public enum CodeResponse {

    /**
     *
     */
    OK(200, "OK", "200 OK", "HELLO"),
    /**
     *
     */
    BAD_REQUEST(400, "BAD_REQUEST", "400 Bad Request", ""),
    /**
     *
     */
    FORBIDDEN(403, "FORBIDDEN", "403 Forbidden", ""),
    /**
     *
     */
    NOT_FOUND(404, "NOT_FOUND", "404 Not Found", ""),
    /**
     *
     */
    INTERNAL_ERROR(500, "INTERNAL_ERROR", "500 Internal Server Error", "");
    /**
     *
     */
    private final int code;
    /**
     *
     */
    private String label;
    /**
     *
     */
    private String description;
    /**
     *
     */
    private String content;
    /**
     * A mapping between the integer code and its corresponding CodeResponse to
     * facilitate lookup by code.
     */
    private static Map<Integer, CodeResponse> codeToCodeResponseMapping;

    private CodeResponse(int code, String label, String description, String content) {
        this.code = code;
        this.label = label;
        this.description = description;
        this.content = "hello";//content;
       // FileContent file = new FileContent();
    }

    /**
     *
     * @param i int
     * @return codeToCodeResponseMapping
     */
    public static CodeResponse getCodeResponse(final int i) {
        if (codeToCodeResponseMapping == null) {
            initMapping();
        }
        return codeToCodeResponseMapping.get(i);
    }

    /**
     *
     */
    private static void initMapping() {
        codeToCodeResponseMapping = new HashMap<>();
        for (CodeResponse s : values()) {
            codeToCodeResponseMapping.put(s.code, s);
        }
    }

    /**
     *
     * @return code
     */
    public final int getCode() {
        return code;
    }

    /**
     *
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return content
     */
    public String getContent() {
        return content;
    }
}
