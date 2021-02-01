/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Solution TF on 15. 8. 17..
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ItemModel <T> {

    @JsonProperty
    String text;

    @JsonProperty
    String id;

    @JsonProperty
    String parent;
    
    @JsonProperty
    String spriteCssClass;

    @JsonProperty
    String objCode;

    @JsonProperty
    String objName;
    
    @JsonProperty
    List<ItemModel<T>> items;

    boolean isRoot = false;

    boolean isGroup = false;

    @JsonProperty
    boolean hasChild = false;

    @JsonProperty
    T item;

    public void addItem(ItemModel<T> item){
        if(items == null) {
            items = new ArrayList<ItemModel<T>>();
        }
        items.add(item);
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<ItemModel<T>> getItems() {
        return items;
    }

    public void setItems(List<ItemModel<T>> items) {
        this.items = items;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setIsRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
    
    public String getSpriteCssClass() {
        return spriteCssClass;
    }
    public void setSpriteCssClass(String spriteCssClass) {
        this.spriteCssClass = spriteCssClass;
    }    
    
    public void setObjCode(String objCode) {
        this.objCode = objCode;
    }
    
    public String getObjCode() {
        return objCode;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }
    
    public String getObjName() {
        return objName;
    }

}
