package com.mycompany.imageview;
public class Image_DB {

    private int id;
    private String keyword;
    private String link;

    public Image_DB(){}
//wzc why has an empty constructor?

    public Image_DB(String keyword, String link) {
        super();
        this.keyword = keyword;
        this.link=link;
    }
    //getters & setters

    @Override
    public String toString() {
        //wzc id auto give???
        return "Image [id=" + id + ", keyword=" + keyword + ", link=" + link
                + "]";
    }
    public String getkeyword(){
        return this.keyword;
    }
    public String getLink(){
        return this.link;
    }
    public Integer getid(){
        return this.id;
    }
    public void set_id(int id){
        this.id = id;
    }
    public void set_keyword(String keyword){
        this.keyword = keyword;
    }
    public void set_link(String link){
        this.link = link;
    }
}

