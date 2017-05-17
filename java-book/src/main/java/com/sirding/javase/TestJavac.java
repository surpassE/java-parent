package com.sirding.javase;

import com.sun.source.tree.IfTree;
import com.sun.tools.internal.ws.processor.model.java.JavaParameter;
import com.sun.tools.javac.comp.MemberEnter;
import com.sun.tools.javac.main.Main;
import com.sun.tools.javac.parser.JavacParser;
import com.sun.tools.javac.parser.Tokens.Token;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.TreeMaker;

public class TestJavac {

	public static void main(String[] args) {
		Main compiler = new Main("javac");
		System.out.println(compiler.compile(args));
		Token token = null;
		JavaParameter javaParameter = null;
		JavacParser javacParser = null;
		JCTree jcTree = null;
		IfTree ifTree = null;
		JCClassDecl classDecl = null;
		TreeMaker maker = null;
		MemberEnter memberEnter = null;
		
	}
}
