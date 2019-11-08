package dmacc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Books;
import dmacc.repository.BookRepository;

@Controller
public class WebController {
	@Autowired
	BookRepository repo;

	@GetMapping("/viewAll")
	public String viewAllBooks(Model model) {
		model.addAttribute("books", repo.findAll());
		return "results";
	}

	@GetMapping("/inputBook")
	public String addNewBooks(Model model) {
		Books b = new Books();
		model.addAttribute("newBooks", b);
		return "input";
	}

	@PostMapping("/inputBook")
	public String addNewBooks(@ModelAttribute Books b, Model model) {
		repo.save(b);
		model.addAttribute("books", repo.findAll());
		return "results";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Books b = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

		model.addAttribute("books", b);
		return "update";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid Books b, BindingResult result, Model model) {
		if (result.hasErrors()) {
			b.setId(id);
			return "update";
		}

		repo.save(b);
		model.addAttribute("books", repo.findAll());
		return "results";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		Books b = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		repo.delete(b);
		model.addAttribute("books", repo.findAll());
		return "results";
	}

}
