package com.athan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @aurhor yyh
 * @description
 * @date 2022/7/27 16:23
 */
@Data
@ApiModel("分页类")
public class BasePage {

    @NotNull(message = "请填写显示页数")
    private Integer pageNum;

    @NotNull(message = "请填写显示条数")
    private Integer pageSize;

    @ApiModelProperty("数据过滤sql")
    private String sqlFilter;

}
