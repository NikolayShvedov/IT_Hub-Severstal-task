<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
<div>
    <@l.logout />
</div>
<div>
    <form method="post">
        <input type="text" name="text" placeholder="Текст заметки" />
        <input type="text" name="tag" placeholder="Тэг заметки">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Добавить</button>
    </form>
</div>
<div>Список сообщений</div>
<form method="get" action="/main">
    <input type="text" name="filterTag" value="${filterTag?ifExists}">
    <button type="submit">Найти</button>
</form>
<#list notes as note>
    <div>
        <b>${note.id}</b>
        <span>${note.text}</span>
        <i>${note.tag}</i>
    </div>
<#else>
    No note
</#list>
</@c.page>
