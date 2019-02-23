package com.foo.myapp.tag;

/**
 * [처음][이전][다음][마지막]을 이미지로 처리
 * @author foo
 *
 */
public class ImagePaginationRenderer extends AbstractPaginationRenderer {

	//경로를 context root에 맞게 수정해야 한다.
	private static final String IMG_PATH = "/myapp/resources/images/pagination/";

	public ImagePaginationRenderer() {

		firstPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\"><img src=\"" + IMG_PATH + "L-end.png\" class=\"img-page\"/></a>&nbsp;";
		previousPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\"><img src=\"" + IMG_PATH + "L.png\" class=\"img-page\"/></a>&nbsp;";
		currentPageLabel = "<strong>{0}</strong>&nbsp;";
		otherPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a>&nbsp;";
		nextPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\"><img src=\"" + IMG_PATH + "R.png\" class=\"img-page\"/></a>&nbsp;";
		lastPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\"><img src=\"" + IMG_PATH + "R-end.png\" class=\"img-page\"/></a>&nbsp;";

	}

}
