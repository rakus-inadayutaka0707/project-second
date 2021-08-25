package com.example.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.Word;
import com.example.form.WordForm;
import com.example.service.WordService;

@Controller
@RequestMapping("/")
public class WordController {

	@Autowired
	private HttpSession session;

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
		model.addAttribute("count", wordList.size());
		model.addAttribute("wordList", wordList);
		return "word/wordList";
	}

	/**
	 * 単語登録画面を表示する.
	 * 
	 * @return 単語登録画面へ
	 */
	@RequestMapping("/to-insert")
	public String toInsert() {
		return "word/insert";
	}

	/**
	 * 単語を登録する処理.
	 * 
	 * @param form   登録する単語入力フォーム内容
	 * @param result 入力内容チェック
	 * @param model  単語リスト一覧表示にリダイレクトするために必要
	 * @return 単語リスト一覧表示メソッドへリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(@Validated WordForm form, BindingResult result) {
		if (result.hasErrors()) {
			return toInsert();
		}
		Word word = new Word();
		BeanUtils.copyProperties(form, word);
		word.setCategoryId(1);
		word.setCount(0);
		word.setQuestionFlag(1);
		service.save(word);
		return "redirect:/";
	}

	/**
	 * 登録している単語を削除する.
	 * 
	 * @param id 削除したい単語ID
	 * @return 単語リスト一覧表示メソッドへリダイレクト
	 */
	@RequestMapping("/delete")
	public String delete(Integer id) {
		service.delete(id);
		return "redirect:/";
	}

	/**
	 * 問題出力画面を表示する.
	 * 
	 * @return 問題出力画面へ
	 */
	@RequestMapping("/to-question")
	public String toQuestion() {
		if (session.getAttribute("question") == null) {
			session.setAttribute("question", null);
		}
		return "word/question";
	}

	/**
	 * 出力する問題を選ぶ.
	 * 
	 * @return to-questionへリダイレクト
	 */
	@RequestMapping("/question")
	public String question() {
		List<Word> wordList = service.findTrueQuestionFlag();
		if (wordList.size() > 0) {
			Random random = new Random();
			Word word = new Word();
			Word check = (Word) session.getAttribute("question");
			if (check == null) {
				check = new Word();
			}
			
			int count = 0;
			do {
				word = wordList.get(random.nextInt(wordList.size()));
				count++;
				if(count > 10) {
					break;
				}
			} while (word.getId() == check.getId());
			session.setAttribute("question", word);
			return "redirect:/to-question";
		}
		session.invalidate();
		return "redirect:/";
	}

	/**
	 * 問題出力をするかどうかを更新する.
	 * 
	 * @param output 出力するかどうかの真偽値
	 * @return 出力問題選定へ
	 */
	@RequestMapping("/reQuestion")
	public String reQuestion(Integer output) {
		Word word = (Word) session.getAttribute("question");
		word.setQuestionFlag(output);
		service.save(word);
		return "redirect:/question";
	}

	/**
	 * 問題出力フラグを変更する.
	 * 
	 * @param id 変更したい単語ID
	 * @return 単語一覧画面へ
	 */
	@RequestMapping("/update")
	public String update(Integer id) {
		Word word = service.load(id);
		word.setQuestionFlag(1);
		service.save(word);
		return "redirect:/";
	}

	/**
	 * 入力が回答と合っているかを出力する
	 * 
	 * @param input              入力フォームに入力した文字
	 * @param redirectAttributes リダイレクトするためのスコープ
	 * @return to-questionへリダイレクト
	 */
	@RequestMapping("/answer")
	public String answer(String input, RedirectAttributes redirectAttributes) {
		Word word = (Word) session.getAttribute("question");
		if (input.equals(word.getWord())) {
			redirectAttributes.addFlashAttribute("result", true);
			redirectAttributes.addFlashAttribute("resultText", "正解です");
			word.setCount(word.getCount() + 1);
		} else {
			redirectAttributes.addFlashAttribute("result", false);
			redirectAttributes.addFlashAttribute("resultText", "不正解です");
			word.setCount(0);
		}
		service.save(word);
		redirectAttributes.addFlashAttribute("input", input);
		redirectAttributes.addFlashAttribute("hiddenInput", true);
		return "redirect:to-question";
	}
}
