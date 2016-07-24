package onefengma.demo.common;

import com.oreilly.servlet.multipart.FileRenamePolicy;

import com.tinify.Options;
import com.tinify.Source;
import com.tinify.Tinify;
import onefengma.demo.server.core.LogUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.Part;

import onefengma.demo.server.config.Config;
import sun.rmi.runtime.Log;

/**
 * @author yfchu
 * @date 2016/5/25
 */
public class FileHelper {

    private static final FileRename fileRename = new FileRename();
    private static final MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();


    public static FileRename getFileRename() {
        return fileRename;
    }

    public static String getContentType(String fileName) {
        mimetypesFileTypeMap.addMimeTypes("image/png png");
        mimetypesFileTypeMap.addMimeTypes("image/tif tif");
        mimetypesFileTypeMap.addMimeTypes("image/bmp bmp");
        return mimetypesFileTypeMap.getContentType(new File(fileName));
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
        String folder = Config.getDownLoadFilePath() + subFolder + DateHelper.getDataStr() + File.pathSeparator;
        String fileName = folder + IdUtils.md5(UUID.randomUUID().toString());
        File file = new File(fileName);
        FileUtils.copyInputStreamToFile(inputStream, file);
        return file;
    }

    public static String getFileFolder() {
        String subFolder = DateHelper.getYear() + File.separator + DateHelper.getMonth() + File.separator + DateHelper.getDay();
        return Config.getDownLoadFilePath() + subFolder;
    }

    public static class FileRename implements FileRenamePolicy {

        @Override
        public File rename(File file) {
            File newFile = new File(file.getParent() + File.separator + IdUtils.id() + "." + FileHelper.getFileSuffix(file.getName()));
            file.renameTo(newFile);
            return newFile;
        }

    }

    public static void compressImage(final String fileNameA) {
        final String fileName = fileNameA.replace("./res", "res");
        ThreadUtils.instance().post(new Runnable() {
            @Override
            public void run() {
                try {
                    LogUtils.i("start compress : " + fileName);
                    Source source = Tinify.fromFile(fileName);
                    Options options = new Options()
                            .with("method", "scale")
                            .with("width", 320);
                    Source resized = source.resize(options);
                    resized.toFile(fileName);
                    LogUtils.i("compress done : " + fileName);
                } catch (IOException e) {
                    LogUtils.i("compress error : " + e.getMessage() + "for" + fileName);
                }
            }
        });
    }

    public static File getFileFromPath(String path) {
        File file = new File(Config.getBasePagePath() + path);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    public static String generateRelativeInternetUri(File file) {
        if (file == null) {
            return "";
        }
        return generateRelativeInternetUri(file.getPath());
    }


    public static String generateRelativeInternetUri(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return "";
        }
        return filePath.replace(Config.getDownLoadFilePath(), "./" + Config.getDownLoadFileRequestPath());
    }

    public static String generateInternetPath(String path) {
        if (StringUtils.isEmpty(path)) {
            return "";
        }
        path = path.replace('\\', '/');
        return path.replace(Config.getDownLoadFilePath(), "");
    }
}
