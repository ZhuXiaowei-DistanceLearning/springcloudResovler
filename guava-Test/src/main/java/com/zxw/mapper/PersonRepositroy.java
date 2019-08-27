package com.zxw.mapper;

import com.zxw.pojo.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * @author zxw
 * @date 2019/8/27 20:31
 */
public interface PersonRepositroy extends CrudRepository<Person, String> {
}
