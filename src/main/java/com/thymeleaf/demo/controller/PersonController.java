package com.thymeleaf.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.thymeleaf.demo.model.Person;
import com.thymeleaf.demo.repository.PersonRepository;
import com.thymeleaf.demo.service.PersonService;

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;
    
    
    @Autowired
    PersonRepository personRepository;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("person", personService.createPerson());
        return "index";
    }
    
    @RequestMapping(value = { "list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		List<Person> persons = personService.findAllPersons();
		model.addAttribute("persons", persons);
		model.addAttribute("metaTitle", "All Users");
		return "list";
	}

    @PostMapping("/addContact")
    public String addContact(Person person){
        personService.addContact(person);
        return "index :: contacts"; // returning the updated section
    }

//    @SuppressWarnings("unlikely-arg-type")
	@PostMapping("/removeContact")
    public String removeContact(Person person, @RequestParam("removeContact") Integer contactIndex){
//    	person.getContactList().remove(contactIndex);
        personService.removeContact(person, contactIndex);
        return "index :: contacts"; // returning the updated section
    }

    @PostMapping("/")
    public String save(@Valid Person person, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage", "The submitted data has errors.");
        }else {
            model.addAttribute("person", personService.savePerson(person));
            model.addAttribute("successMessage", "Person saved successfully!");
        }

        return "index";
    }
    
    @RequestMapping(value = "/personDelete/{id}", method = RequestMethod.GET)
	public String notesDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
    	personRepository.deleteById(id);
//			model.addAttribute("listAppUser", appUserService.findAll());
		return "redirect:/list";
	}
}