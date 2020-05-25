package com.system.springbootv1.project.service;

import com.system.springbootv1.utils.StringUtils;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: yy 2020/02/03
 **/
@Service
public class ToolService {

    public String exec(String script) {
        String result = "";
        try {
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.exec(script);
            PyFunction function = interpreter.get("solution", PyFunction.class);
            PyObject pyObject = function.__call__();
            result = pyObject.toString();
        } catch (Exception e) {
            result = StringUtils.substring(e.getMessage(), 0, 2000);
        }
        return result;
    }
}
