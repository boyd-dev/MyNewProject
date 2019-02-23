package com.foo.myapp.tag;

import java.util.Map;

public class DefaultPaginationManager implements PaginationManager {

	private Map<String, PaginationRenderer> rendererType;

	/**
	 * Set PaginationRenderer 구현 클래스
	 * @param rendererType - PaginationRenderer 구현 클래스 집합 Map.
	 */
	public void setRendererType(Map<String, PaginationRenderer> rendererType) {
		this.rendererType = rendererType;
	}

	/**
	 * @param type - tag의 파라미터 값.
	 */
	public PaginationRenderer getRendererType(String type) {

		return (rendererType != null && rendererType.containsKey(type)) ? (PaginationRenderer) rendererType.get(type) : new DefaultPaginationRenderer();
	}

}
