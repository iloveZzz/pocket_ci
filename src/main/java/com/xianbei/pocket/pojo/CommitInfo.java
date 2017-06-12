package com.xianbei.pocket.pojo;

/**
 * Created by zhudaoming on 2017/6/12.
 */
public class CommitInfo {

    public CommitInfo() {

    }

    public CommitInfo(String actor, String repo_name, String repo_full_name, String path_branch, String message, String compare_html) {
        this.actor = actor;
        this.repo_name = repo_name;
        this.repo_full_name = repo_full_name;
        this.path_branch = path_branch;
        this.message = message;
        this.compare_html = compare_html;
    }

    private String actor;
    private String repo_name;
    private String repo_full_name;
    private String path_branch;
    private String message;
    private String compare_html;

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getRepo_name() {
        return repo_name;
    }

    public void setRepo_name(String repo_name) {
        this.repo_name = repo_name;
    }

    public String getRepo_full_name() {
        return repo_full_name;
    }

    public void setRepo_full_name(String repo_full_name) {
        this.repo_full_name = repo_full_name;
    }

    public String getPath_branch() {
        return path_branch;
    }

    public void setPath_branch(String path_branch) {
        this.path_branch = path_branch;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCompare_html() {
        return compare_html;
    }

    public void setCompare_html(String compare_html) {
        this.compare_html = compare_html;
    }
}
