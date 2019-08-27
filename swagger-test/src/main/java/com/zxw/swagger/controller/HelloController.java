package com.zxw.swagger.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zxw
 * @date 2019/8/27 19:11
 */
@RestController
//@Api(value = "功能测试1", tags = {"功能测试"})
@ApiModel(value = "com.zxw.swagger.controller",description = "登录参数")
public class HelloController {

    @ApiModelProperty(value = "企业编号", required = true)
    private Long eid;

    @GetMapping("/hello/{hello}")
    @ApiResponses({
            @ApiResponse(code = 200,message = "欢迎使用")
    })
    @ApiOperation(value = "用户登录",notes = "企业用户认证接口，参数为必填项")
    @ApiImplicitParams({@ApiImplicitParam(name="uid",value = "用户ID",required = false,paramType = "query",dataType = "String",defaultValue = "1")})
    public String hello(@ApiParam(value = "登录参数",required = true) @PathVariable("hello") String hello) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        return format + ":" + hello;
    }

    @GetMapping("/world/{world}")
    public String world(@PathVariable("world") String hello) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        return format + ":" + hello;
    }
}
