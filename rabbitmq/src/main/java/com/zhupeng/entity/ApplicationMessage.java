package com.zhupeng.entity;

import org.springframework.context.ApplicationEvent;

import javax.validation.constraints.NotNull;


public abstract class ApplicationMessage extends ApplicationEvent {

    @NotNull
    private Enum messageType;

    public Enum getMessageType() {
        return messageType;
    }

    public void setMessageType(Enum messageType) {
        this.messageType = messageType;
    }

    @NotNull
    private String aa;

    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }

    public ApplicationMessage(Object source) {
        super(source);
    }

    public enum messageType{

        TEST(1 , "test" , "测试系统消息");

        /**
         * 类型
         */
        private final String type;
        /**
         * 描述
         */
        private final String description;

        private final Integer code;

        public String getType() {
            return type;
        }

        public String getDescription() {
            return description;
        }

        public Integer getCode() {
            return code;
        }

        messageType(Integer code , String type, String description) {
            this.type = type;
            this.description = description;
            this.code = code;
        }
    }
}
