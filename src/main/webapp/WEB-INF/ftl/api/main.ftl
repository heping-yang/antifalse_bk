<#assign request_json>
${input}
</#assign>
<#assign request=request_json?eval />
{
<#include "head.ftl" />
<#include "body.ftl" />
}