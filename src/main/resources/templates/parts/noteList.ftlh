<#include "security.ftlh">

<div class="card-columns">
    <#list notes as note>
        <div class="card my-3">
            <#if note.filename??>
                <img src="/img/${note.filename}" class="card-img-top">
            </#if>
            <br/>
            <span class="m-2">${note.text}</span>
            <div class="card-footer text-muted m-2">
                <i>Тэг: ${note.tag}</i>
                <a style="margin-left: 20px" class="btn btn-primary" href="/main/${note.author.id}?note=${note.id}">
                    Изменить
                </a>
            </div>
        </div>
    <#else>
        Нет заметок
    </#list>
</div>
