package onefengma.demo.common;

import javax.servlet.http.Part;

/**
 * @author yfchu
 * @date 2016/5/25
 */
public class FileHelper {

    public static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName;
            }
        }
        return null;
    }

    public static String getFileSuffix(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(fileName)) {
            for (int i = fileName.length() - 1; i >= 0; i--) {
                char c = fileName.charAt(i);
                if (c == '.') {
                    break;
                }
                stringBuilder.append(c);
            }
        }
        return stringBuilder.reverse().toString();
    }

}
