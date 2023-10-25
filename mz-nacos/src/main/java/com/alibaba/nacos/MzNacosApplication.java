/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos;

import com.alibaba.nacos.console.config.ConfigConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Nacos starter.
 * <p>
 * Use @SpringBootApplication and @ComponentScan at the same time, using CUSTOM type filter to control module enabled.
 * </p>
 *
 * @author nacos
 */
@SpringBootApplication(scanBasePackages = "com.alibaba.nacos")
@ServletComponentScan
@EnableScheduling
public class MzNacosApplication {
    
    public static void main(String[] args) {
        if (initEnv()){
            SpringApplication.run(MzNacosApplication.class, args);
        }
    }

    /**
     * 初始化运行环境
     * -server -Xms2g -Xmx2g -Xmn1g
     */
    private static boolean initEnv() {
        System.setProperty(ConfigConstants.STANDALONE_MODE, "true");
        System.setProperty(ConfigConstants.AUTH_ENABLED, "true");
        System.setProperty(ConfigConstants.LOG_BASEDIR, "logs");
        System.setProperty(ConfigConstants.NACOS_HOME, "./nacos");
        System.setProperty(ConfigConstants.LOG_ENABLED, "false");
        System.setProperty(ConfigConstants.NACOS_CONTEXT_PATH, "/nacos");
        return true;
    }
}

