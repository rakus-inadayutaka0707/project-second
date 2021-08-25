package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Word;
import com.example.repository.WordRepository;

/**
 * WordRepositoryのServiceクラス.
 * 
 * @author inada
 *
 */
@Service
@Transactional
public class WordService {

	@Autowired
	private WordRepository repository;

	/**
	 * 1単語を取得する.
	 * 
	 * @param id 取得したい単語ID
	 * @return 取得した単語情報
	 */
	public Word load(Integer id) {
		return repository.load(id);
	}

	/**
	 * 全ての単語を取得する.
	 * 
	 * @return 取得したすべての単語
	 */
	public List<Word> findAll() {
		return repository.findAll();
	}

	/**
	 * 出力する問題の為の単語検索.
	 * 
	 * @return 単語リスト
	 */
	public List<Word> findTrueQuestionFlag() {
		return repository.findTrueQuestionFlag();
	}

	/**
	 * 単語の登録・更新をする.
	 * 
	 * @param word 登録・更新したい単語情報
	 * @return 登録・更新した単語情報
	 */
	public Word save(Word word) {
		return repository.save(word);
	}

	/**
	 * 登録している単語を削除する.
	 * 
	 * @param id 削除したい単語ID
	 */
	public void delete(Integer id) {
		repository.delete(id);
	}
}
