package com.system.springbootv1.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: yy 2020/01/29
 **/
public class TestJob {

    private static Logger logger = LoggerFactory.getLogger(TestJob.class);

    public void test() {
        logger.info("test job is running!");
    }
}
