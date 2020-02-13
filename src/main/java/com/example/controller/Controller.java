package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

/**
 * 掲示板アプリケーションコントローラー.
 * 
 * @author yamaseki
 *
 */
@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {

	@Autowired
	private ArticleRepository ARepository;

	@Autowired
	private CommentRepository CRepository;

	@Autowired
	private ServletContext application;

	/**
	 * メイン画面へ遷移するためのメソッド.
	 * 
	 * @return メイン画面へ遷移する
	 */
	@RequestMapping("")
	public String index() {
		if (ARepository.findAll() != null) {
			List<Article> articleList = ARepository.findAll();
			List<Comment> commentList = new ArrayList<>();

			for (Article article : articleList) {
				commentList = CRepository.findByArticleId(article.getId());
				article.setCommentList(commentList);
			}
			application.setAttribute("articleList", articleList);
		}
		return "main";
	}

	/**
	 * 記事を投稿するためのメソッド.
	 * 
	 * @param 記事投稿フォームから受け取ったリクエストデータ
	 * @return indexメソッドへリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(ArticleForm form) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		ARepository.insert(article);
		return "redirect:/";
	}

	/**
	 * コメントを投稿するメソッド.
	 * 
	 * @param コメントフォームから受け取ったリクエストデータ
	 * @return indexメソッドへリダイレクト
	 */
	@RequestMapping("/insertComment")
	public String insertComment(CommentForm form) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		Integer intArticleId = Integer.parseInt(form.getArticleId());
		comment.setArticleId(intArticleId);
		CRepository.insertComment(comment);
		return "redirect:/";
	}

	/**
	 * 記事とコメントを削除するメソッド.
	 * 
	 * @param 記事のID
	 * @return メインメソッドへリダイレクト
	 */
	@RequestMapping("/deleteById")
	public String deleteById(Integer id) {
		CRepository.deleteAll();
		ARepository.deleteById(id);
		return "redirect:/";
	}

}
