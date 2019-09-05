package zxw.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zxw
 * @date 2019/9/5 21:08
 */
public interface ServiceAClient {
    @RequestMapping(value = "/search/repositories",method = RequestMethod.GET)
    public String searchRepo(@RequestParam("q") String queryStr);
}
