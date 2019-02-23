package com.foo.myapp.tag;

/**
 * <ui:pagination/>에서 type이 지정되지 않으면 기본 페이지 탭이 표시된다.
 *
 */
public class DefaultPaginationRenderer extends AbstractPaginationRenderer {

	public DefaultPaginationRenderer() {
		firstPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">[처음]</a>&#160;";
		previousPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">[이전]</a>&#160;";
		currentPageLabel = "<strong>{0}</strong>&#160;";
		otherPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a>&#160;";
		nextPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">[다음]</a>&#160;";
		lastPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">[마지막]</a>&#160;";
	}

	@Override
	public String renderPagination(Paging pagingInfo, String jsFunction) {

		return super.renderPagination(pagingInfo, jsFunction);
	}

}
