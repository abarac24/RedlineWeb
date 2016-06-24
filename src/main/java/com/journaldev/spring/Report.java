package com.journaldev.spring;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.journaldev.spring.model.Constants;
import com.journaldev.spring.service.TestCaseServiceInterface;
import com.journaldev.spring.service.TestStepServiceInterface;

@Controller
public class Report {
	@Resource(name = "testService")
	private TestStepServiceInterface testService;

	@Resource(name = "testCaseService")
	private TestCaseServiceInterface testCaseService;

	@Autowired(required = true)
	@Qualifier(value = "testService")
	public void setTestService(TestStepServiceInterface testService) {
		this.testService = testService;
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String listTestSteps(Model model) {
		/*
		 * model.addAttribute("teststep", new TestStep());
		 * model.addAttribute("listTestSteps",
		 * this.testService.listTestSteps());
		 * model.addAttribute("listNameSuite",
		 * this.testService.listNameSuite());
		 */
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"servlet-context.xml");

		Constants constant = (Constants) context.getBean("constantsBean");
		File[] files = new File(constant.getREPORT_PATH()).listFiles();
		
		model.addAttribute("files", files);

		return "report";

	}

	@RequestMapping("/remove/{fileName}")
	public String removeTestCase(@PathVariable("fileName") String fileName,Model model) {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"servlet-context.xml");

		Constants constant = (Constants) context.getBean("constantsBean");
		File[] files = new File(constant.getREPORT_PATH()).listFiles();
		
		model.addAttribute("files", files);
		
		for (File file : files) {
			if (file.getName().replace(".html", "").contentEquals(fileName)) {
				file.delete();
			}
		}

		return "redirect:/report";
	}

}
