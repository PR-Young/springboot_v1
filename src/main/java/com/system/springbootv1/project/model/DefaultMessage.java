package com.system.springbootv1.project.model;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/06/19
 **/
public class DefaultMessage {
    private String sender;
    private List receiver;
    private Map vars;
    private SysTemplate template;

    public static DefaultMessage.MessageBuilder newInstance() {
        return new DefaultMessage.MessageBuilder();
    }

    protected DefaultMessage(DefaultMessage.MessageBuilder builder) {
        this.sender = builder.sender;
        this.receiver = builder.receiver;
        this.vars = builder.vars;
        this.template = builder.template;
    }

    public static class MessageBuilder {
        private String sender;
        private List receiver;
        private Map vars;
        private SysTemplate template;

        public MessageBuilder() {
        }

        public DefaultMessage.MessageBuilder sender(String value) {
            this.sender = value;
            return this;
        }

        public DefaultMessage.MessageBuilder receivers(List value) {
            this.receiver = value;
            return this;
        }

        public DefaultMessage.MessageBuilder vars(Map value) {
            this.vars = value;
            return this;
        }

        public DefaultMessage.MessageBuilder template(SysTemplate value) {
            this.template = value;
            return this;
        }

        public DefaultMessage build() {
            return new DefaultMessage(this);
        }
    }

    public String getSender() {
        return sender;
    }

    public List getReceiver() {
        return receiver;
    }

    public Map getVars() {
        return vars;
    }

    public SysTemplate getTemplate() {
        return template;
    }
}
