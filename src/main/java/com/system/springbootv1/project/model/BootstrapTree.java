package com.system.springbootv1.project.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 前台bootstrap需要的树
 */
@Data
public class BootstrapTree {
    private String text;//文字
    private String icon;//图标
    private String data;//数据
    private String id;//id
    private String url;//url
    private Integer isBlank;//是否跳转
    private Map<String, Object> state;//选中参数
    private List<BootstrapTree> nodes;//子元素
    private Integer visible;//是否显示

    public BootstrapTree() {
        super();
    }

    public BootstrapTree(String text, String icon, String data, String id, String url, Integer isBlank,
                         List<BootstrapTree> nodes, Integer visible) {
        super();
        this.text = text;
        this.icon = icon;
        this.data = data;
        this.id = id;
        this.url = url;
        this.isBlank = isBlank;
        this.nodes = nodes;
        this.visible = visible;
    }

    public BootstrapTree(String text, String icon, String data, String id, String url, Integer isBlank,
                         Map<String, Object> state, List<BootstrapTree> nodes, Integer visible) {
        super();
        this.text = text;
        this.icon = icon;
        this.data = data;
        this.id = id;
        this.url = url;
        this.isBlank = isBlank;
        this.state = state;
        this.nodes = nodes;
        this.visible = visible;
    }


}
