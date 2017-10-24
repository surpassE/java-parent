package com.sirding.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/teController")
public class TeController {

	@RequestMapping(params = "method=save")
	@ResponseBody
	public String save(String content) {
		System.out.println(content);
		return "ok";
	}

	@RequestMapping(params = "page")
	@ResponseBody
	public List<Person> page(int start, int end, int page) {
		List<Person> list = new ArrayList<>();
		for (int i = 1; i < 1000; i++) {
			Person p = new Person();
			p.setName("test" + i);
			p.setAge(i);
			list.add(p);
		}
		return list;
	}
}
