package com.example.form;

/**
 * 単語登録時のフォーム.
 * 
 * @author inada
 *
 */
public class WordForm {
	/** 単語 */
	private String word;
	/** 意味 */
	private String meaning;
	/** カテゴリID */
	private String categoryId;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "WordForm [word=" + word + ", meaning=" + meaning + ", categoryId=" + categoryId + "]";
	}
}
