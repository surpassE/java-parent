package com.sirding.javaeight;

import java.nio.file.*;

/**
 * TODO
 *
 * @author zc.ding
 * @create 2018/12/19
 */
public class FileWatchService {
    public static void main(String[] args) throws Exception {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        Paths.get("D:\\test\\watch").register(watcher,
                StandardWatchEventKinds.ENTRY_MODIFY);

        while (true) {
            WatchKey key = watcher.take();
            for (WatchEvent<?> event: key.pollEvents()) {
                System.out.println(event.context() + " comes to " + event.kind());
            }

            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }

    }
}
