package com.example.domain;

/**
 * wordsテーブルのドメインクラス.
 * 
 * @author inada
 *
 */
public class Word {
	/** ID */
	private Integer id;
	/** 単語 */
	private String word;
	/** 意味 */
	private String meaning;
	/** 分類 */
	private Integer categoryId;
	/** 正解数 */
	private Integer count;
	/** 出力フラグ */
	private Integer questionFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getQuestionFlag() {
		return questionFlag;
	}

	public void setQuestionFlag(Integer questionFlag) {
		this.questionFlag = questionFlag;
	}

	@Override
	public String toString() {
		return "Word [id=" + id + ", word=" + word + ", meaning=" + meaning + ", categoryId=" + categoryId + ", count="
				+ count + ", questionFlag=" + questionFlag + "]";
	}
}
