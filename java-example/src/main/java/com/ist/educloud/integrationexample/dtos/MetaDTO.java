package com.ist.educloud.integrationexample.dtos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MetaDTO {
    private String created;
    private String modified;

    public MetaDTO(String created, String modified) throws ParseException {
        this.created = created;
        this.modified = modified;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
