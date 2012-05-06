<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.role.create.title" /></title>
</head>
<body>

  <h2><g:message code="nimble.view.role.create.heading" /></h2>

  <p>
    <g:message code="nimble.view.role.create.descriptive" />
  </p>

  <n:errors bean="${role}"/>

    <g:form action="save" class="form-horizontal">
        <f:with bean="role">
            <f:field property="name" label="nimble.label.name" />
            <f:field property="description" label="nimble.label.description" />            
        </f:with>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">
                <i class="icon-ok icon-white"></i>
                <g:message code="nimble.link.createrole" />
            </button>
            <g:link action="list" class="btn btn-warning">
                <i class="icon-arrow-left icon-white"></i>
                <g:message code="nimble.link.cancel" />
            </g:link>
        </div>
    </g:form>


   <%-- 
  <g:form action="save" method="post">

    <table>
      <tbody>

      <tr>
        <th><g:message code="nimble.label.name" /></th>
        <td class="value">
		  <n:verifyfield id="name" class="easyinput" name="name" value="${fieldValue(bean: role, field: 'name')}" required="true" controller="role" action="validname" validmsg="valid" invalidmsg="invalid" />
        </td>
      </tr>

      <tr>
        <th><g:message code="nimble.label.description" /></th>
        <td class="value">
          <input type="text" id="description" size="30" name="description" value="${fieldValue(bean: role, field: 'description')}" class="easyinput"/><span class="icon icon_bullet_green">&nbsp;</span>
        </td>
      </tr>

      <tr>
        <td/>
        <td>
          <div class="buttons">
            <button class="button icon icon_group_add" type="submit"><g:message code="nimble.link.createrole" /></button>
            <g:link action="list" class="button icon icon_cancel"><g:message code="nimble.link.cancel" /></g:link>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

  </g:form>
 --%>
</body>
</html>
