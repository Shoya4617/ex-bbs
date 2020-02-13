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

import com.example.domain.Comment;

/**
 * コメントデータを出し入れするためのメソッド.
 * 
 * @author yamaseki
 *
 */
@Repository
@Transactional
public class CommentRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};

	/**
	 * 記事IDを参照し記事を検索するメソッド.
	 * 
	 * @param 記事ID
	 * @return 記事一覧
	 */
	public List<Comment> findByArticleId(Integer articleId) {
		String sql = "select id,name,content,article_id from comments where article_id=:articleId order by id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		List<Comment> articleList = template.query(sql, param, COMMENT_ROW_MAPPER);
		return articleList;
	}

	/**
	 * コメントデータを挿入するためのメソッド.
	 * 
	 * @param コメントクラス
	 */
	public void insertComment(Comment comment) {
		String sql = "Insert into comments(name,content,article_id)values(:name,:content,:articleId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		template.update(sql, param);
	}

	/**
	 * 全てのコメントを削除jするメソッド.
	 */
	public void deleteAll() {
		String sql = "delete from comments";
		SqlParameterSource param = new MapSqlParameterSource();
		template.update(sql, param);
	}
}
