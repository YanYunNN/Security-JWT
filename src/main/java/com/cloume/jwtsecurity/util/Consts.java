package com.cloume.jwtsecurity.util;

public class Consts {

    /**
     * 下发门禁操作命令的动作
     */
    public static class Action {
        public static final String DELETE = "delete";
        public static final String ADD = "add";
    }

    /**
     * 定义预约的几种状态
     */
    public static class States {
        public static final String WAITING = "待审核";
        public static final String PASSED = "未开始";
        public static final String USING = "进行中";
        public static final String OVERED = "已完成";
    }

    /**
     * 定义时间
     */
    public static class Times {
        public static final String EVERYDAY = "everyDay";
    }
}
