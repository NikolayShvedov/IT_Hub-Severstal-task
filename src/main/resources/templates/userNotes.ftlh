<#import "parts/common.ftlh" as c>

<@c.page>
    <#if isCurrentUser>
        <#include "parts/noteEdit.ftlh" />
    </#if>

    <#include "parts/noteList.ftlh" />
</@c.page>
