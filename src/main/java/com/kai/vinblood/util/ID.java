package com.kai.vinblood.util;

import java.util.Objects;

public class ID {
    public int idnum;
    public String idname;

    public ID(int idnum, String idname) {
        this.idnum = idnum;
        this.idname = idname;
    }

    public ID(int idnum) {
        this(idnum, "");
    }

    public ID(String idname) {
        this(-1, idname);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ID id = (ID) o;
        return idnum == id.idnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idnum);
    }
}
