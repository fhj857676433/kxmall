package com.kxmall.market.admin.manager;



import com.kxmall.market.core.annotation.HttpParamType;

import java.util.List;
import java.util.Map;

/**
 *
 * Description:
 * User: admin
 * Date: 2018-09-13
 * Time: 上午9:35
 */
public class ApiDocumentModel {
    private List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public static class Group {
        /**
         * 组名
         */
        private String name;
        /**
         * 方法列表
         */
        private List<Method> methods;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Method> getMethods() {
            return methods;
        }

        public void setMethods(List<Method> methods) {
            this.methods = methods;
        }
    }

    public static class Method {
        private String name;
        private String description;
        private String retType;
        private List<Field> retObj;
        private List<Parameter> parameters;

        private List<Map<String,List<Parameter>>> parametersChild;

        public List<Map<String, List<Parameter>>> getParametersChild() {
            return parametersChild;
        }

        public void setParametersChild(List<Map<String, List<Parameter>>> parametersChild) {
            this.parametersChild = parametersChild;
        }

        public List<Map<String, List<Field>>> getFieldChild() {
            return fieldChild;
        }

        public void setFieldChild(List<Map<String, List<Field>>> fieldChild) {
            this.fieldChild = fieldChild;
        }

        //新增子类参数对象
        private List<Map<String,List<Field>>> fieldChild;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRetType() {
            return retType;
        }

        public void setRetType(String retType) {
            this.retType = retType;
        }

        public List<Field> getRetObj() {
            return retObj;
        }

        public void setRetObj(List<Field> retObj) {
            this.retObj = retObj;
        }

        public List<Parameter> getParameters() {
            return parameters;
        }

        public void setParameters(List<Parameter> parameters) {
            this.parameters = parameters;
        }
    }

    public static class Parameter {
        private String name;
        private String description;
        /**
         * 参数class类型
         */
        private String paramType;
        /**
         * 参数枚举
         */
        private HttpParamType type;

        private Boolean required;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getParamType() {
            return paramType;
        }

        public void setParamType(String paramType) {
            this.paramType = paramType;
        }

        public HttpParamType getType() {
            return type;
        }

        public void setType(HttpParamType type) {
            this.type = type;
        }

        public Boolean getRequired() {
            return required;
        }

        public void setRequired(Boolean required) {
            this.required = required;
        }
    }

    public static class Field {
        private String name;
        private String type;
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
