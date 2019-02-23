package com.foo.myapp.tag;
/*
 * Copyright 2008-2009 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.text.MessageFormat;

/**
 * 이 클래스는 추상 클래스이므로(abstract 선언된 메소드가 없어도 가능) 자체적으로 객체가 될 수 없다.
 * 상속한 클래스의 생성자에서 페이지 탭에 필요한 정보를 전달한다.
 *
 *
 */
public abstract class AbstractPaginationRenderer implements PaginationRenderer {

	protected String firstPageLabel;
	protected String previousPageLabel;
	protected String currentPageLabel;
	protected String otherPageLabel;
	protected String nextPageLabel;
	protected String lastPageLabel;

	public String renderPagination(Paging pagingInfo, String jsFunction) {

		StringBuffer strBuff = new StringBuffer();

		int firstPageNo = pagingInfo.getFirstPageNo();
		int firstPageNoOnPageList = pagingInfo.getFirstPageNoOnPageList();
		int totalPageCount = pagingInfo.getTotalPageCount();
		int pageSize = pagingInfo.getPageSize();
		int lastPageNoOnPageList = pagingInfo.getLastPageNoOnPageList();
		int currentPageNo = pagingInfo.getCurrentPageNo();
		int lastPageNo = pagingInfo.getLastPageNo();

		if (totalPageCount > pageSize) {
			if (firstPageNoOnPageList > pageSize) {
				strBuff.append(MessageFormat.format(firstPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNo) }));
				strBuff.append(MessageFormat.format(previousPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNoOnPageList - 1) }));
			} else {
				strBuff.append(MessageFormat.format(firstPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNo) }));
				strBuff.append(MessageFormat.format(previousPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNo) }));
			}
		}

		for (int i = firstPageNoOnPageList; i <= lastPageNoOnPageList; i++) {
			if (i == currentPageNo) {
				strBuff.append(MessageFormat.format(currentPageLabel, new Object[] { Integer.toString(i) }));
			} else {
				strBuff.append(MessageFormat.format(otherPageLabel, new Object[] { jsFunction, Integer.toString(i), Integer.toString(i) }));
			}
		}

		if (totalPageCount > pageSize) {
			if (lastPageNoOnPageList < totalPageCount) {
				strBuff.append(MessageFormat.format(nextPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNoOnPageList + pageSize) }));
				strBuff.append(MessageFormat.format(lastPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
			} else {
				strBuff.append(MessageFormat.format(nextPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
				strBuff.append(MessageFormat.format(lastPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
			}
		}
		return strBuff.toString();
	}
}
