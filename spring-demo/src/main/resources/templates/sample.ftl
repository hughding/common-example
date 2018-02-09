<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>sample</title>
</head>
<body>
<#if (sampleList??)>
    <#list sampleList as sample>
        ${sample!}<br/>
    </#list>
<#else>
    空
</#if>

${nowDate.toString()!}<br/>
年：${nowDate.year!} 月：${nowDate.month!} 日：${nowDate.dayOfMonth!}<br/>

testNull或者name为空，显示空：${(testNull.name)!}<br/>
</body>
</html>