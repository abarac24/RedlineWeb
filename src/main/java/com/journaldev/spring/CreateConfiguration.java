package com.journaldev.spring;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

//import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.journaldev.spring.model.Configuration;
import com.journaldev.spring.model.Constants;
import com.journaldev.spring.model.TestCase;
import com.journaldev.spring.model.TestStep;
import com.journaldev.spring.service.ConfigurationServiceInterface;
import com.journaldev.spring.service.TestCaseServiceInterface;
import com.journaldev.spring.service.TestStepServiceInterface;
import com.redline.selenium.ExecuteTestCases;

@Controller
public class CreateConfiguration extends Thread {

	@Resource(name = "configurationService")
	private ConfigurationServiceInterface configurationService;

	@Resource(name = "testService")
	private TestStepServiceInterface testService;

	@Resource(name = "testCaseService")
	private TestCaseServiceInterface testCaseService;

	@Autowired(required = true)
	@Qualifier(value = "configurationService")
	public void setTestService(
			ConfigurationServiceInterface configurationService) {
		this.configurationService = configurationService;
	}

	@Autowired(required = true)
	@Qualifier(value = "testCaseService")
	public void setTestCaseService(TestCaseServiceInterface testCaseService) {
		this.testCaseService = testCaseService;
	}

	@RequestMapping(value = "/configuration", method = RequestMethod.GET)
	public String listConfigurations(Model model) {
		model.addAttribute("configuration", new Configuration());
		model.addAttribute("listConfigurations",
				this.configurationService.listConfigurations());
		model.addAttribute("listTestCases",
				this.testCaseService.listTestCases());

		return "configuration";

	}

	@RequestMapping(value = "/project/{project}", method = RequestMethod.GET)
	public String listTestCases(Model model,
			@PathVariable("project") String project) {
		model.addAttribute("configuration", new Configuration());
		model.addAttribute("listConfigurations",
				this.configurationService.listConfigurations());
		model.addAttribute("listTestCases",
				this.testCaseService.getTestCaseByProject(project));

		return "table";

	}

	@RequestMapping(value = "/configuration/add", method = RequestMethod.POST)
	public String addConfiguration(
			@ModelAttribute("configuration") Configuration configuration) {

		if (configuration.getId() == 0) {
			// new config, add it
			this.configurationService.addConfiguration(configuration);
		}

		else {
			// existing config, call update
			this.configurationService.updateConfiguration(configuration);
		}

		return "redirect:/configuration";

	}

	@RequestMapping("/removeconfiguration/{id}")
	public String removeConfiguration(@PathVariable("id") int id) {

		this.configurationService.removeConfiguration(id);
		return "redirect:/configuration";
	}

	@RequestMapping("/editconfiguration/{id}")
	public String editConfiguration(@PathVariable("id") int id, Model model) {
		model.addAttribute("Configuration",
				this.configurationService.getConfigurationById(id));
		model.addAttribute("listConfigurations",
				this.configurationService.listConfigurations());

		return "configuration";
	}

	@RequestMapping(value = "/configuration/run", method = RequestMethod.POST)
	public String runTestCase(
			@ModelAttribute("configuration") Configuration configuration)
			throws IOException, InterruptedException {
		

		FileWriter fstreamGlobal=null;
		TestCase testCase=null;
		Integer testSelected[] = configuration.getTestSelected();
		// List list = Arrays.asList(testSelected);

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(Calendar.getInstance().getTime());

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"servlet-context.xml");

		Constants constant = (Constants) context.getBean("constantsBean");
		for (Integer t : testSelected) {
			testCase = testCaseService.getTestCaseById(t);
		}
		
		
		if (testCase.getProject().equals("Connect")){
			fstreamGlobal = new FileWriter(constant.getREPORT_PATH()
					+ "\\Connect_" + timeStamp + ".html");
			}
		else if(testCase.getProject().equals("RDL3000")){
			fstreamGlobal = new FileWriter(constant.getREPORT_PATH()
					+ "\\RDL3000_" + timeStamp + ".html");
		}
		BufferedWriter out = new BufferedWriter(fstreamGlobal);

		// out.write("<%@ page import=\"java.io.*,java.util.*\" %>");
		// out.newLine();
		out.write("<!DOCTYPE html>");
		out.newLine();
		out.write("<html lang=\"en\">");
		out.newLine();
		out.write("<head>");
		out.newLine();
		out.write("<title>Automated UI Testing </title>");
		out.newLine();
		out.write("<meta charset=\"utf-8\">");
		out.newLine();
		out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		out.newLine();
		out.write("<link href=\"../resources/css/bootstrap.css\" rel=\"stylesheet\">");
		out.newLine();
		out.write("<link href=\"../resources/css/bootstrap.min.css\" rel=\"stylesheet\">");
		out.newLine();
		out.write("<link rel=\"stylesheet\" type=\"text/css\"	href=\"../../resources/css/datatables.css\">");
		out.newLine();
		out.write("</head>");
		out.newLine();
		// out.write("<% response.setIntHeader(\"Refresh\", 10); %>");
		// out.newLine();
		out.write("<body>");
		out.newLine();
		out.write("<br>");
		out.newLine();
		out.write("<div class=\"container\">");
		out.newLine();
		for (Integer t : testSelected) {
			testCase = testCaseService.getTestCaseById(t);
			List<TestStep> testList = testCase.getListSteps();

            ExecuteTestCases.runSuite(testCase, configuration, testList,out);


			// ExecuteTestCases.runSuite(testCase,configuration,testList);

		}
		out.close();
		return "redirect:/configuration";

	}

}
