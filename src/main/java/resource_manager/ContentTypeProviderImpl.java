package resource_manager;

public class ContentTypeProviderImpl implements ContentTypeProvider {
    private static final String CHARSET_UTF_8 = "charset=utf-8";

    @Override
    public String getContentType(String fileName) {
        String fileExtension = getFileExtension(fileName);

        switch (fileExtension) {
            case "gif", "png", "tiff":
                return addSlashBetweenStrings("image", fileExtension);
            case "jpeg", "jpg":
                return addSlashBetweenStrings("image", "jpeg");
            case "csv", "css", "xml":
                return addSlashBetweenStrings("text", fileExtension) + "; " + CHARSET_UTF_8;
            case "html", "htm":
                return addSlashBetweenStrings("text", "html") + "; " + CHARSET_UTF_8;
            case "txt":
            default:
                return addSlashBetweenStrings("text", "plain") + "; " + CHARSET_UTF_8;
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        int indexOfLastDot = fileName.lastIndexOf(".");
        if (indexOfLastDot == -1) {
            return "";
        }

        return fileName.substring(indexOfLastDot + 1);
    }

    private String addSlashBetweenStrings(String string1, String string2) {
        return string1 + "/" + string2;
    }
}