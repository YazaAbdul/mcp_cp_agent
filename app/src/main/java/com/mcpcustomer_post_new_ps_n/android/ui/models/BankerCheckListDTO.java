package com.mcpcustomer_post_new_ps_n.android.ui.models;

public class BankerCheckListDTO {

    /*checklist_id: "1",
perticular: "Id Proof-Pan card",
document: ""*/

    private String checklist_id;
    private String perticular;
    private String document;

    public String getChecklist_id() {
        return checklist_id;
    }

    public void setChecklist_id(String checklist_id) {
        this.checklist_id = checklist_id;
    }

    public String getPerticular() {
        return perticular;
    }

    public void setPerticular(String perticular) {
        this.perticular = perticular;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
