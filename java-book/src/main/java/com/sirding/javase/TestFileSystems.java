package com.sirding.javase;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class TestFileSystems {

	public static void main(String[] args) {
		try {
			WatchService watchService = FileSystems.getDefault().newWatchService();
			WatchKey watchKey = watchService.take();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
