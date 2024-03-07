package com.linln.admin.system.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 岳天一
 * @date 2024/01/12
 */
@Data
public class AccessValid implements Serializable {
    @NotEmpty(message = "标题不能为空")
    private String title;
}