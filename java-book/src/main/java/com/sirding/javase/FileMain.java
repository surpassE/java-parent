package com.sirding.javase;

import java.io.FileDescriptor;
import java.io.SyncFailedException;

public class FileMain {

	public static void main(String[] args) {
		try {
			FileDescriptor.in.sync();
		} catch (SyncFailedException e) {
			e.printStackTrace();
		}
	}
}
