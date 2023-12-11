package com.edw.bean;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;
import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;

import java.io.Serializable;

/**
 * <pre>
 *     com.edw.bean.User
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 26 Jun 2023 10:38
 */
@ProtoDoc("@Indexed(index=\"index01\")")
@Indexed
public class User implements Serializable {
    private String name;

    private Integer age;

    private String address;

    public User() {
    }

    public User(String name, Integer age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @ProtoField(number = 1, required = true)
    @ProtoDoc("@Field(index=Index.YES, analyze = Analyze.YES, store = Store.NO)")
    @KeywordField
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ProtoField(number = 2)
    @ProtoDoc("@Field(index=Index.YES, analyze = Analyze.YES, store = Store.NO)")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @ProtoField(number = 3)
    @ProtoDoc("@Field(index=Index.YES, analyze = Analyze.YES, store = Store.NO)")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
