package com.yara.odx.domain;

import java.util.Objects;

public class CropClass extends Thing {

    private String groupId;
    private String faoId;
    private String name;

    public CropClass(String source, String className, String id, String groupId, String faoId, String name) {
        super(source, className, id);
        this.groupId = groupId;
        this.faoId = faoId;
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getFaoId() {
        return faoId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CropClass{" +
                "source='" + super.getSource() + '\'' +
                ", className='" + super.getClassName() + '\'' +
                ", id='" + super.getId() + '\'' +
                ", UUid='" + super.getUuId() + '\'' +
                ", groupId='" + groupId + '\'' +
                ", faoId='" + faoId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CropClass cropClass = (CropClass) o;
        return Objects.equals(groupId, cropClass.groupId) &&
                Objects.equals(faoId, cropClass.faoId) &&
                Objects.equals(name, cropClass.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, faoId, name);
    }
}
