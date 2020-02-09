package com.hr;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class AddChannelToBoxApk {
    private static final String CHANNEL_PREFIX = "/META-INF/";
    private static final String CHANNEL_PATH_MATCHER = "regex:/META-INF/kw_[0-9a-zA-Z]{1,5}";

    public static boolean changeChannel(final String zipFilename, final String channel, final String newFilename) throws IOException {
        Files.copy(new File(zipFilename).toPath(), new File(newFilename).toPath());

        String channelName = "kw_" + channel;
        try (FileSystem zipfs = createZipFileSystem(newFilename, false)) {

            final Path root = zipfs.getPath("/META-INF/");
            ChannelFileVisitor visitor = new ChannelFileVisitor();
            Files.walkFileTree(root, visitor);

            Path existChannel = visitor.getChannelFile();
            Path newChannel = zipfs.getPath(CHANNEL_PREFIX + channelName);
            if (existChannel != null) {
                Files.move(existChannel, newChannel, StandardCopyOption.ATOMIC_MOVE);
            } else {
                Files.createFile(newChannel);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("打包失败：修改渠道信息失败");
            return false;
        }
    }

    public static FileSystem createZipFileSystem(String zipFilename, boolean create) throws IOException {
        final Path path = Paths.get(zipFilename);
        final URI uri = URI.create("jar:file:" + path.toUri().getPath());

        final Map<String, String> env = new HashMap<>();
        if (create) {
            env.put("create", "true");
        }
        return FileSystems.newFileSystem(uri, env);
    }

    private static class ChannelFileVisitor extends SimpleFileVisitor<Path> {
        private Path channelFile;
        private PathMatcher matcher = FileSystems.getDefault().getPathMatcher(CHANNEL_PATH_MATCHER);

        public Path getChannelFile() {
            return channelFile;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (matcher.matches(file)) {
                channelFile = file;
                return FileVisitResult.TERMINATE;
            } else {
                return FileVisitResult.CONTINUE;
            }
        }
    }

    public static void main(String[] args){
        try {
            // kw_渠道名_盒子子包id
            changeChannel("/Users/yym/Desktop/base.apk", "smart", "/Users/yym/Desktop/channel_smart.apk");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
