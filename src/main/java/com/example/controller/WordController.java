package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Word;
import com.example.form.WordForm;
import com.example.service.WordService;

@Controller
@RequestMapping("/")
public class WordController {

	@Autowired
	private WordService service;
	
	@ModelAttribute
	private WordForm setUpWordForm() {
		return new WordForm();
	}

	/**
	 * 単語リストを一覧表示する.
	 * 
	 * @param model 表示する単語リスト
	 * @return 単語リスト表示画面
	 */
	@RequestMapping("/")
	public String wordList(Model model) {
		List<Word> wordList = service.findAll();
		model.addAttribute("wordList", wordList);
		return "word/wordList";
	}
	
	@RequestMapping("/to-insert")
	public String toInsert() {
		return "word/insert";
	}
	
	@RequestMapping("/insert")
	public String insert(@Validated WordForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return toInsert();
		}
		Word word = new Word();
		BeanUtils.copyProperties(form, word);
		word.setCategoryId(1);
		word.setCount(0);
		service.save(word);
		return "redirect:/";
	}
}
