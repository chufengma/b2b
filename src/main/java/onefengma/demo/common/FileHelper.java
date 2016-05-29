package onefengma.demo.common;

import com.oreilly.servlet.multipart.FileRenamePolicy;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.Part;

import onefengma.demo.server.config.Config;

/**
 * @author yfchu
 * @date 2016/5/25
 */
public class FileHelper {

    private static final FileRename fileRename = new FileRename();

    public static FileRename getFileRename() {
        return fileRename;
    }

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

    public static File saveFile(InputStream inputStream, String subFolder, String suffix) throws IOException {
        subFolder = StringUtils.isEmpty(subFolder) ? "" : subFolder + File.pathSeparator ;
        String folder = Config.getBaseFilePath() + subFolder + DateHelper.getDataStr() + File.pathSeparator;
        String fileName = folder + MD5Utils.md5(UUID.randomUUID().toString());
        File file = new File(fileName);
        FileUtils.copyInputStreamToFile(inputStream, file);
        return file;
    }

    public static String getFileFolder() {
        return Config.getBaseFilePath() + DateHelper.getDataStr();
    }

    public static class FileRename implements FileRenamePolicy {

        @Override
        public File rename(File file) {
            File newFile = new File(file.getParent() + File.separator + MD5Utils.md5(UUID.randomUUID().toString()) + "." + FileHelper.getFileSuffix(file.getName()));
            file.renameTo(newFile);
            return newFile;
        }

    }

    public static File getFileFromPath(String path) {
        File file = new File(Config.getBasePagePath() + path);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }
}