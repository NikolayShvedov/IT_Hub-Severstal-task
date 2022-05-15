<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filterTag" class="form-control" value="${filterTag?ifExists}" placeholder="Тэг заметки">
                <button type="submit" class="btn btn-primary ml-2">Найти</button>
            </form>
        </div>
    </div>

    <#include "parts/noteSave.ftlh" />

    <#include "parts/noteList.ftlh" />
</@c.page>
