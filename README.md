[添加当前目录的所有文件](添加当前目录的所有文件 git add .)
[备注存档](备注存档 git commit -m "备注名称")
[运行/保存](运行 git st)
[查看情况](查看情况 git status)
[上传文件命令](若git status提示git push则是上传文件命令)
[修改文件了重新上传](可以先用git status查看情况还要git add .若出现红字则输入git commit --amend --no-edit)
[快捷键](alt+鼠标左键向下拉可以多行输入，shift+方向键选中，ctrl+方向键到左边或者右边,ctrl+shift+方向键功能同时使用)

## 脚本
```sql
create table USER
(
    ID           int default auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);
```