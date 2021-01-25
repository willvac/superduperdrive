package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int addCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getAllCredentials(int userId);

    @Update("UPDATE CREDENTIALS SET " +
            "url = #{url}, " +
            "username = #{username}, " +
            "password = #{password}, " +
            "key = #{key} WHERE credentialid = #{credentialId}"
    )
    public int updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{userId}")
    int deleteCredential(int credentialId, int userId);

    @Select("SELECT userid FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    int getUserIdOfCredential(Integer credentialId);
}
