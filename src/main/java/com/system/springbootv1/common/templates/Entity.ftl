package ${entityPackage};

import com.system.springbootv1.common.model.BaseModel;
import lombok.Data;

@Data
public class ${entityName} extends BaseModel{
<#list columns as column>
    private ${column.type} ${column.name};
</#list>
}