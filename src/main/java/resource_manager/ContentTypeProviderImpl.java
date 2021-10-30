package resource_manager;

public class ContentTypeProviderImpl implements ContentTypeProvider {
    @Override
    public String getContentType(String fileName) {
        String fileExtension = getFileExtension(fileName);

        switch (fileExtension) {
            case "gif":
            case "png":
            case "tiff":
                return addSlashBetweenStrings("image", fileExtension);
            case "jpeg":
            case "jpg":
                return addSlashBetweenStrings("image", "jpeg");
            case "csv":
            case "css":
            case "xml":
                return addSlashBetweenStrings("text", fileExtension) + "; charset=utf-8";
            case "html":
            case "htm":
                return addSlashBetweenStrings("text", "html") + "; charset=utf-8";
            case "txt":
            default:
                return addSlashBetweenStrings("text", "plain") + "; charset=utf-8";
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