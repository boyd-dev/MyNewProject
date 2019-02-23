package com.foo.myapp.common.file.service;

import java.util.List;

/**
 * 파일정보
 * @author foo
 *
 */
public interface IFileMngService {

    /**
     * 파일에 대한 목록을 조회한다.
     *
     * @param fvo
     * @return
     * @throws Exception
     */
    public List<FileVO> selectFileList(FileVO fvo) throws Exception;

    /**
     * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
     *
     * @param fvo
     * @throws Exception
     */
    public void insertFile(FileVO fvo) throws Exception;


    /**
     *
     * filepond를 사용하는 경우 파일업로드 후 다른 데이터를 저장하므로
     * 이 때 해당 데이터 키를 파일 정보와 연결시켜야 한다.
     *
     * @param fvo
     * @throws Exception
     */
    public String updateFileAttchId(AtchFileVO fvo) throws Exception;


    /**
     * 하나의 파일을 삭제한다.
     *
     * @param fvo
     * @throws Exception
     */
    public void deleteFile(FileVO fvo) throws Exception;

    /**
     * 파일에 대한 상세정보를 조회한다.
     *
     * @param fvo
     * @return
     * @throws Exception
     */
    public FileVO selectFile(FileVO fvo) throws Exception;


}
