<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    Страница авторизации
    <@l.login "/login" />
    <a href="/registration">Регистрация</a>
</@c.page>
