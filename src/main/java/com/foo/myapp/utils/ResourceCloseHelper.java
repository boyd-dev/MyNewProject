package com.foo.myapp.utils;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class  to support to close resources
 * @author Vincent Han
 * @since 2014.09.18
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자       수정내용
 *  -------       --------    ---------------------------
 *   2014.09.18	표준프레임워크센터	최초 생성
 *
 * </pre>
 */
public class ResourceCloseHelper {

	private static Logger LOGGER = LoggerFactory.getLogger(ResourceCloseHelper.class);

	/**
	 * Resource close 처리.
	 * @param resources
	 */
	public static void close(Closeable  ... resources) {
		for (Closeable resource : resources) {
			if (resource != null) {
				try {
					resource.close();
				} catch (Exception ignore) {
					LOGGER.warn("Occurred Exception to close resource is ignored!!");
				}
			}
		}
	}

	/**
	 * JDBC 관련 resource 객체 close 처리
	 * @param objects
	 */
	public static void closeDBObjects(Wrapper ... objects) {
		for (Object object : objects) {
			if (object != null) {
				if (object instanceof ResultSet) {
					try {
						((ResultSet)object).close();
					} catch (Exception ignore) {
						LOGGER.warn("Occurred Exception to close resource is ignored!!");
					}
				} else if (object instanceof Statement) {
					try {
						((Statement)object).close();
					} catch (Exception ignore) {
						LOGGER.warn("Occurred Exception to close resource is ignored!!");
					}
				} else if (object instanceof Connection) {
					try {
						((Connection)object).close();
					} catch (Exception ignore) {
						LOGGER.warn("Occurred Exception to close resource is ignored!!");
					}
				} else {
					throw new IllegalArgumentException("Wrapper type is not found : " + object.toString());
				}
			}
		}
	}


}