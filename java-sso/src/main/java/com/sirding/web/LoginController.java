package com.sirding.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/loginController")
public class LoginController {

	@RequestMapping(params = "toIndex")
	public String toIndex(){
		System.out.println("aaa");
		return "redirect:/index.jsp";
	}

	@RequestMapping(params = "getState")
	@ResponseBody
	public String getState(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> map = new HashMap<String, String>();
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				map.put(cookie.getName(), cookie.getValue());
			}
		}
		String msg = com.alibaba.druid.support.json.JSONUtils.toJSONString(map);
		return "jsonpcallback( " + msg + ")";
	}
	
	@RequestMapping(params = "syncState")
	@ResponseBody
	public String syncState(HttpServletRequest request, HttpServletResponse response){
		boolean flag = true;
		try {
			String state = request.getParameter("state");
			if("online".equals(state)){
				this.setCookie(response, 1200);
			}else{
				this.setCookie(response, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		
		return "jsonpcallback( {success:" + String.valueOf(flag) + "})";
	}
	
	/**
	 * 添加或是删除状态的cookie，time设置0表示删除cookie
	 * @author	 zc.ding
	 * @since 	 2017年5月13日
	 * @param response
	 * @param time
	 */
	private void setCookie(HttpServletResponse response, int time){
		Cookie stateCookie = new Cookie("state", "online");
		stateCookie.setMaxAge(time);
		response.addCookie(stateCookie);
		Cookie platformCookie = new Cookie("platform", "hkjf");
		platformCookie.setMaxAge(time);
		response.addCookie(platformCookie);
	}
}
