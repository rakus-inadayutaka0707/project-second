package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.domain.Word;

/**
 * WordドメインのRepositoryクラス.
 * 
 * @author inada
 *
 */
@Repository
public class WordRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String TABLENAME = "words";

	private static final RowMapper<Word> WORD_ROW_MAPPER = (rs, i) -> {
		Word word = new Word();
		word.setId(rs.getInt("id"));
		word.setWord(rs.getString("word"));
		word.setMeaning(rs.getString("meaning"));
		word.setCategoryId(rs.getInt("category_id"));
		word.setCount(rs.getInt("count"));
		word.setQuestionFlag(rs.getInt("question_flag"));
		return word;
	};

	/**
	 * 1単語を取得する.
	 * 
	 * @param id 取得したい単語ID
	 * @return 取得した単語情報
	 */
	public Word load(Integer id) {
		String sql = "select id,word,meaning,category_id,count,question_flag from " + TABLENAME + " where id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Word word = template.queryForObject(sql, param, WORD_ROW_MAPPER);
		return word;
	}

	/**
	 * 全ての単語を取得する.
	 * 
	 * @return 取得したすべての単語
	 */
	public List<Word> findAll() {
		String sql = "select id,word,meaning,category_id,count,question_flag from " + TABLENAME
				+ " order by question_flag asc;";
		List<Word> wordList = template.query(sql, WORD_ROW_MAPPER);
		return wordList;
	}

	/**
	 * 出力する問題の為の単語検索.,
	 * 
	 * @return 単語リスト
	 */
	public List<Word> findTrueQuestionFlag() {
		String sql = "select id,word,meaning,category_id,count,question_flag from " + TABLENAME
				+ " where question_flag=1 order by count asc;";
		List<Word> wordList = template.query(sql, WORD_ROW_MAPPER);
		return wordList;
	}

	/**
	 * 単語の登録・更新をする.
	 * 
	 * @param word 登録・更新したい単語情報
	 * @return 登録・更新した単語情報
	 */
	public Word save(Word word) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(word);
		if (word.getId() == null) {
			String sql = "insert into " + TABLENAME
					+ " (word,meaning,category_id,count,question_flag) values (:word,:meaning,:categoryId,:count,:questionFlag);";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			String[] keyColumName = { "id" };
			template.update(sql, param, keyHolder, keyColumName);
			word.setId(keyHolder.getKey().intValue());
			return word;
		}
		String sql = "update " + TABLENAME
				+ " set word=:word,meaning=:meaning,category_id=:categoryId,count=:count,question_flag=:questionFlag where id=:id;";
		template.update(sql, param);
		return word;
	}

	/**
	 * 登録している単語を削除する.
	 * 
	 * @param id 削除したい単語ID
	 */
	public void delete(Integer id) {
		String sql = "delete from " + TABLENAME + " where id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
