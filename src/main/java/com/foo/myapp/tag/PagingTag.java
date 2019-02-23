package com.foo.myapp.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 게시판 하단 페이지 번호를 보여주기 위한 커스텀 태그
 * @author foo
 *
 */
public class PagingTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	//태그 속성
	private Paging pagingInfo;
	private String type;
	private String jsFunction;

	public int doStartTag() throws JspException {
		return SKIP_BODY; //태그 사이의 내용을 처리하지 않는다.
	}


	public int doEndTag() throws JspException {

		try {

			JspWriter out = pageContext.getOut();

			PaginationManager paginationManager;

			//교체 가능한 PaginationManager 클래스를 찾기 위해 Spring 컨텍스트를 참조한다.
			//PaginationManager는  페이지 하단의 페이지 탭을 렌더링한다.
			WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());

			if (ctx.containsBean("paginationManager")) {
				paginationManager = (PaginationManager) ctx.getBean("paginationManager");
			} else {
				paginationManager = new DefaultPaginationManager(); //없으면 이미 구현된 DefaultPaginationManager를 사용한다.
			}

			PaginationRenderer paginationRenderer = paginationManager.getRendererType(type);

			String contents = paginationRenderer.renderPagination(pagingInfo, jsFunction);

			out.println(contents);

			return EVAL_PAGE;//태그 이후 페이지 처리를 계속 진행한다.

		} catch (IOException e) {
			throw new JspException();
		}
	}

	public void setJsFunction(String jsFunction) {
		this.jsFunction = jsFunction;
	}

	public void setPaginationInfo(Paging pagingInfo) {
		this.pagingInfo = pagingInfo;
	}

	public void setType(String type) {
		this.type = type;
	}


}
