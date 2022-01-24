package com.ist.educloud.integrationexample.dtos;

public class DisplayObjectDTO {
    private String id;
    private String displayName;
    private securityMarking securityMarking;
    public DisplayObjectDTO() {
    }

    public DisplayObjectDTO.securityMarking getSecurityMarking() {
        return securityMarking;
    }

    public void setSecurityMarking(DisplayObjectDTO.securityMarking securityMarking) {
        this.securityMarking = securityMarking;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public enum securityMarking {
        NONE("Ingen"),
        SECRET("Sekretessmarkering"),
        PROTECTED("Skyddad folkbokföring");

        public final String label;
        securityMarking(String label) {
            this.label = label;
        }

        public String getLabel() {
            return this.label;
        }
    }
}
