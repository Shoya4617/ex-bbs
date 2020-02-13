package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Article;

/**
 * 記事のデータを出し入れするリポジトリ.
 * 
 * @author yamaseki
 *
 */
@Repository
@Transactional
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};

	/**
	 * 記事を全て検索するためのメソッド.
	 * 
	 * @return 記事一覧
	 */
	public List<Article> findAll() {
		String sql = "select id,name,content from articles order by id";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		return articleList;
	}

	/**
	 * 記事データを挿入するためのメソッド.
	 * 
	 * @param 記事クラス
	 */
	public void insert(Article article) {
		String sql = "Insert into articles(name,content)values(:name,:content)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		template.update(sql, param);
	}

	/**
	 * 記事IDを参照し、その記事を削除するためのメソッド.
	 * 
	 * @param 記事ID
	 */
	public void deleteById(Integer id) {
		String sql = "delete from articles where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

}
