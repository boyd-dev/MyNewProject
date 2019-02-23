package com.foo.myapp.idgnr.service;

/**
 * ID생성 구현체
 * 숫자를 리턴한다.
 * 전자정부프레임워크의 유일키 생성 클래스를 참조
 *
 */

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class IdGnrServiceImpl implements IdGnrService {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private final Object semaphore = new Object(); //synchronized

	protected DataSource dataSource;
	protected String seqTable;
	protected String seqColumn;
	private TransactionTemplate transactionTemplate;
	private JdbcTemplate jdbcTemplate;

	private int cacheSize = 0;
	private int usedCount;
	private Long firstId;


	public int getCacheSize() {
		return cacheSize;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);

		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);

        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.transactionTemplate.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW"); //새로운 트랜잭션을 시작한다.
        this.transactionTemplate.setIsolationLevelName("ISOLATION_READ_COMMITTED");	//커밋 전에는 다른 사람이 데이터를 볼 수 없다.
	}

	//cacheSize는 채번 테이블에서 한 번에 가져오는 일련번호의 수이다.
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public void setSeqTable(String seqTable) {
		this.seqTable = seqTable;
	}

	public void setSeqColumn(String seqColumn) {
		this.seqColumn = seqColumn;
	}


    /**
     * 번호생성 테이블로부터 일련번호를 가져온다.
     * 번호를 가져온 후 다음 번호로 업데이트하고 커밋을 해야 하므로 트랜잭션을 분리한다.
     *
     */
	@Override
	public Object getNextId() throws Exception {

		//자바 동기화 프로그래밍
		//synchronized 블록을 실행할 수 있는 것은 한 순간에 오직 하나 뿐이다.
		//semaphore 객체는 다른 스레드가 점유하는 순간에는 다른 스레드가 사용할 수 없다(상호배제)
		synchronized (semaphore) {

			if (usedCount >= cacheSize) { //한 번에 가져온 번호를 모두 사용했으면 다시 가져온다.

				try {
					firstId = (Long)getNextLongIdFromSeqTable();
					usedCount = 0;

				} catch (Exception e) {
					usedCount = Integer.MAX_VALUE;
					throw e;
				}
			}

			long id = firstId.longValue() + usedCount;

			if (id < 0) {
				LOGGER.error("Exceed the max sequence");
				throw new Exception();
			}
			usedCount++;

			return new Long(id);
		}
	}


	/**
	 * Programmatic transaction using TransactionTemplate
	 *
	 */
	protected Object getNextLongIdFromSeqTable() {

		try {

			return transactionTemplate.execute(new TransactionCallback<Object>() {

				@Override
				public Object doInTransaction(TransactionStatus status) {

					Object nextSeq = null;
					Object newNextSeq = null;

					//SEQ_NAME은 일련번호를 사용할 테이블 명과 동일하다.
					String selectQuery = "SELECT NEXT_SEQ FROM T_SEQ_MASTER WHERE SEQ_NAME = ?";
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Select Query : {}", selectQuery);
					}

					try {
						nextSeq = jdbcTemplate.queryForObject(selectQuery, new Object[] { seqTable }, Long.class);

					} catch (EmptyResultDataAccessException ee) {
						nextSeq = -1L;
					}

					//최초라면 새로 생성
					if ((Long) nextSeq == -1L) {

						String insertQuery = "INSERT INTO T_SEQ_MASTER (SEQ_NAME, NEXT_SEQ) VALUES (? , ?)";
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Insert Query : {}", insertQuery);
						}
						try {
							jdbcTemplate.update(insertQuery, seqTable, new Long(cacheSize + 1));

						} catch (DataAccessException ee) {
							status.setRollbackOnly(); //rollback
							throw ee;
						}
						return new Long(1); //1부터 시작한다.
					}

					try {
						String updateQuery = "UPDATE T_SEQ_MASTER SET NEXT_SEQ = ? WHERE SEQ_NAME = ?";
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Update Query : {}", updateQuery);
						}
						newNextSeq = new Long(((Long) nextSeq).longValue() + cacheSize);

						jdbcTemplate.update(updateQuery, newNextSeq, seqTable);

					} catch (DataAccessException ee) {
						status.setRollbackOnly();
						throw ee;
					}

					return nextSeq;
				} //doInTransaction end
			});

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void init() {
        this.usedCount = Integer.MAX_VALUE;
    }


	public void destroy() {
        this.dataSource = null;
    }

	@Override
	public String getNextStringId() throws Exception {
		return getUUId();
	}

	private String getUUId() {
		return TimeBasedUUIDGenerator.generateId().toString();
    }



}
