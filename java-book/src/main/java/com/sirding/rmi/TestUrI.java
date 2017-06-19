package com.sirding.rmi;

import java.net.URI;
import java.net.URL;

public class TestUrI {

	public static void main(String[] args) throws Exception{
		URI uri = new URI("rmi://127.0.0.1:1000/hello/world");
		System.out.println(uri.getScheme());
		System.out.println(uri.getHost());
		System.out.println(uri.getPath());
		URL url = new URL("http://127.0.0.1:10000/hello/world?name=zcd#1234");
		System.out.println(url.getProtocol());
		System.out.println(url.getHost());
		System.out.println(url.getPort());
		System.out.println(url.getFile());
		System.out.println(url.getPath());
		System.out.println(url.getRef());
		System.out.println(url.getQuery());
	}
}
