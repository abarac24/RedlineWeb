package com.journaldev.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.journaldev.spring.model.TestCase;
import com.journaldev.spring.model.TestStep;
import com.journaldev.spring.service.TestCaseServiceInterface;
import com.journaldev.spring.service.TestStepServiceInterface;

@Controller
public class AddTestCase {
	
	private TestCaseServiceInterface testCaseService;
	
	private TestStepServiceInterface testService;

	
	@Autowired(required=true)
	@Qualifier(value="testCaseService")
	public void setTestService(TestCaseServiceInterface testCaseService) {
		this.testCaseService = testCaseService;
	}

	@RequestMapping(value = "/testcase", method = RequestMethod.GET)
	public String listTestCases(Model model) {
		model.addAttribute("testcase", new TestCase());
		model.addAttribute("listTestCases", this.testCaseService.listTestCases());

		return "testcase";
		
	}

	

	@RequestMapping(value= "/testcase/add", method = RequestMethod.POST)
	public String addTestCase(@ModelAttribute("testcase") TestCase testcase){
		
		if(testcase.getId() == 0){
			//new teststep, add it
			this.testCaseService.addTestCase(testcase);
		}
		
		else{
			//existing teststep, call update
			testcase.setId(testcase.getId());
			this.testCaseService.updateTestCase(testcase);
			
		}
		
		return "redirect:/testcase";
		
	}
	


	@RequestMapping("/removetest/{id}")
    public String removeTestCase(@PathVariable("id") int id){
		
        this.testCaseService.removeTestCase(id);
        return "redirect:/testcase";
    }
	//addd
    @RequestMapping("/teststep/add")
    public String postAdd(@RequestParam("id") Integer testId,@ModelAttribute("stepAttribute") TestStep testStep){
    	
    	this.testService.addStepsToTest(testId, testStep);
        
        return "redirect:/teststep";
    }
	
 
    @RequestMapping("/edittest/{id}")
    public String editTestCase(@PathVariable("id") int id, Model model){
        model.addAttribute("testcase", this.testCaseService.getTestCaseById(id));
        model.addAttribute("listTestCases", this.testCaseService.listTestCases());
        
        
        return "testcase";
    }
    


	
    
    
    
/*    @RequestMapping("/edittest/{id}")
    public String editTestCase(@PathVariable("id") int id,@PathVariable("stepId") int stepId, Model model){
        model.addAttribute("testcase", this.testCaseService.getTestCaseById(id));
        model.addAttribute("listTestCases", this.testCaseService.listTestCases());
        model.addAttribute("teststep", this.testService.getTestStepById(stepId));
        
        this.testCaseService.addTestCaseToSteps(id, stepId);
        return "redirect:/teststep";
    }  */

	
	
}
