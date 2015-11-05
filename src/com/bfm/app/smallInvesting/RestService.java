package com.bfm.app.smallInvesting;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping
@Transactional
public class RestService {

	@RequestMapping(value = "/checkPermission/**", method = RequestMethod.GET)
	@ResponseBody
	public void checkPermission(HttpServletRequest request) {
		long startTime = System.nanoTime();
		Boolean hasPermission = false;
		System.out.println("yoo");
	}
}
