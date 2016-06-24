package com.journaldev.spring;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.journaldev.spring.model.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.journaldev.spring.model.TestStep;
import com.journaldev.spring.service.TestCaseServiceInterface;
import com.journaldev.spring.service.TestStepServiceInterface;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AddTestStep {
	@Resource(name = "testService")
	private TestStepServiceInterface testService;

	@Resource(name = "testCaseService")
	private TestCaseServiceInterface testCaseService;

	@Autowired(required = true)
	@Qualifier(value = "testService")
	public void setTestService(TestStepServiceInterface testService) {
		this.testService = testService;
	}

	@RequestMapping(value = "/teststep", method = RequestMethod.GET)
	public String listTestSteps(Model model) {
		model.addAttribute("teststep", new TestStep());
		model.addAttribute("listTestSteps", this.testService.listTestSteps());
		model.addAttribute("listNameSuite", this.testService.listNameSuite());

		return "teststep";

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String addTest(Model model) {
		// model.addAttribute("test", new TestCase());
		return "test";

	}

	@RequestMapping(value = "/teststep/add", method = RequestMethod.GET)
	public String getAdd(@RequestParam("id") Integer testId, Model model) {
		// logger.debug("Received request to show add page");

		// Prepare model object
		TestStep step = new TestStep();

		// Add to model
		model.addAttribute("testId", testId);
		model.addAttribute("teststep", step);

		// model.addAttribute("listTestSteps",
		// this.testService.listTestSteps());

		model.addAttribute("listTestSteps", this.testService.getAll(testId));

		return "teststep";
	}

    @RequestMapping(value = "/teststep/add",params="add", method = RequestMethod.POST)
    public String addTest(@RequestParam("id") Integer testId,
                          @ModelAttribute("teststep") TestStep testStep,Model model) {

        // model.addAttribute("testcase", this.testCaseService.listTestCases());

        this.testService.addStepsToTest(testId, testStep);
        //this.testService.listTestSteps();


        return "redirect:/teststep/add?id=" + testId;


    }


    @RequestMapping(value = "/teststep/add",params="save", method = RequestMethod.POST)
    public String saveTest(@RequestParam("id") Integer testId,@ModelAttribute("teststep") TestStep teststep, Model model) {

        // model.addAttribute("testcase", this.testCaseService.listTestCases());
        //this.testService.addStepsToTest(testId, testStep);
        //this.testService.listTestSteps();
        //model.addAttribute("listTestSteps", this.testService.listTestSteps());
        //List<TestStep> testList = this.testService.getAll(testId);
        //List<TestStep> t =new ArrayList<TestStep>();
        //t.add(teststep);
        testService.updateTestStep(teststep);

        return "redirect:/teststep/add?id=" + testId;



    }

	@RequestMapping("/delete")
	public String removeTestStep(@RequestParam("tid") Integer testId,@RequestParam("sid") Integer stepId, Model model) {

		this.testService.removeTestStep(stepId);
		return "redirect:/teststep/add?id=" + testId;
	}

    @RequestMapping(value="/edit", method = RequestMethod.GET)
    public String editTestStep(@RequestParam("tid") Integer testId,
                               @RequestParam("sid") Integer stepId, Model model){
        model.addAttribute("testcase", this.testCaseService.getTestCaseById(testId));
        model.addAttribute("teststep", this.testService.getTestStepById(stepId));
        //model.addAttribute("listTestSteps", this.testService.listTestSteps());
        model.addAttribute("listTestSteps", this.testService.getAll(testId));
        return "editstep";
    }

    @RequestMapping(value = "/teststep/edit", method = RequestMethod.POST)
    public String editTest(@RequestParam("tid") Integer testId,
                           @ModelAttribute("teststep") TestStep testStep, Model model) {

        // Delegate to service
        testService.updateTestStep(testStep);



        return  "redirect:/teststep/add?id=" + testId;
    }


    /*@RequestMapping(value = "/teststep/edit", method = RequestMethod.POST)
    public String editTest(@RequestParam("tid") Integer testId,
                           @ModelAttribute("listTestSteps") TestCase testCase, Model model) {

        // Delegate to service

        model.addAttribute("listTestSteps", this.testService.listTestSteps());



        return  "teststep";


    }      */

}
