<head>
   <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}" />
   <title>
      <g:message code="nimble.view.user.show.title" args="[user.username?.encodeAsHTML()]" />
   </title>
   <r:script>
      <njs:user user="${user}" />
      <njs:permission parent="${user}" />
      <njs:role parent="${user}" />
      <njs:group parent="${user}" />
   </r:script>
</head>
<body>
   <h3>
      <g:message code="nimble.view.user.show.heading" args="[user.username?.encodeAsHTML()]" />
      <span>
      </span>
   </h3>
   <div class="row-fluid">
      <div class="span5">
         <div class="box-generic">
            <header>
               <h2>
                  <g:message code="nimble.view.user.show.details.heading" />
               </h2>
               <div class="pull-right btn-group">
                  <g:link controller="user" action="edit" id="${user.id}" class="btn btn-success btn-small">
                     <g:message code="nimble.link.edit" />
                  </g:link>
                  <g:if test="${user.external}">
                     <g:link controller="user" action="changelocalpassword" id="${user.id}" class="btn btn-small btn-success">
                        <g:message code="nimble.link.changelocalpassword" />
                     </g:link>
                  </g:if>
                  <g:else>
                     <g:link controller="user" action="changepassword" id="${user.id}" class="btn btn-small btn-success">
                        <g:message code="nimble.link.changepassword" />
                     </g:link>
                  </g:else>
                  <a href="#" class="btn btn-info btn-small dropdown-toggle" data-toggle="dropdown">
                     <g:message code="nimble.link.more" default="More"/>
                     <span class="caret"></span>
                  </a>
                  <g:render template="actions" model="[user:user]"></g:render>
               </div>
            </header>
            <dl>
               <dt>
                  <g:message code="nimble.label.username" />
               </dt>
               <dd>${user.username?.encodeAsHTML()}</dd>
               <dt>
                  <g:message code="nimble.label.fullname" />
               </dt>
               <dd>${user.profile.fullName?.encodeAsHTML() ?: message(code:'nimble.label.none')}</dd>
               <dt>
                  <g:message code="nimble.label.email" />
               </dt>
               <dd>${user.profile.email?.encodeAsHTML() ?: g.message(code:'nimble.label.none')}</dd>
               <dt>
                  <g:message code="nimble.label.created" />
               </dt>
               <dd>
                  <g:formatDate format="E dd/MM/yyyy HH:mm:s:S" date="${user.dateCreated}" />
               </dd>
               <dt>
                  <g:message code="nimble.label.lastupdated" />
               </dt>
               <dd>
                  <g:formatDate format="E dd/MM/yyyy HH:mm:s:S" date="${user.lastUpdated}" />
               </dd>
               <dt>
                  <g:message code="nimble.label.type" />
               </dt>
               <dd>
                  <g:if test="${user.external}">
                     <g:message code="nimble.label.external.managment" />
                  </g:if>
                  <g:else>
                     <g:message code="nimble.label.local.managment" />
                  </g:else>
               </dd>
               <dt>
                  <g:message code="nimble.label.state" />
               </dt>
               <dd>
                  <div id="disableduser">
                     <g:message code="nimble.label.enabled" />
                  </div>
                  <div id="enableduser">
                     <g:message code="nimble.label.disabled" />
                  </div>
               </dd>
               <dt>
                  <g:message code="nimble.label.remoteapi" />
               </dt>
               <dd>
                  <div id="enabledapi">
                     <span class="icon icon_tick">&nbsp;</span>Enabled
                  </div>
                  <div id="disabledapi">
                     <span class="icon icon_cross">&nbsp;</span>Disabled
                  </div>
               </dd>
            </dl>
         </div>
      </div>
   </div>
   <div class="box-generic">
      <div id="tabs" class="tabbable">
         <ul class="nav nav-tabs">
            <li class="active">
               <a href="#tab-extendedinfo" class="icon icon_user" data-toggle="tab">
                  <g:message code="nimble.label.extendedinformation" />
               </a>
            </li>
            <g:if test="${user.federated}">
               <li>
                  <a href="#tab-federation" class="icon icon_world" data-toggle="tab">
                     <g:message code="nimble.label.federatedaccount" />
                  </a>
               </li>
            </g:if>
            <li>
               <a href="#tab-permissions" class="icon icon_lock" data-toggle="tab">
                  <g:message code="nimble.label.permissions" />
               </a>
            </li>
            <li>
               <a href="#tab-roles" class="icon icon_cog" data-toggle="tab">
                  <g:message code="nimble.label.roles" />
               </a>
            </li>
            <li>
               <a href="#tab-groups" class="icon icon_group" data-toggle="tab">
                  <g:message code="nimble.label.groups" />
               </a>
            </li>
            <li>
               <a href="#tab-logins" class="icon icon_key" data-toggle="tab">
                  <g:message code="nimble.label.logins" />
               </a>
            </li>
         </ul>
         <div class="tab-content">
            <div id="tab-extendedinfo" class="tab-pane active">
               <g:render template="/templates/nimble/user/extendedinformation" contextPath="/" model="[user:user]" />
            </div>
            <g:if test="${user.federated}">
               <div id="tab-federation" class="tab-pane">
                  <g:render template="/templates/nimble/user/federationinformation" contextPath="/" model="[user:user]" />
               </div>
            </g:if>
            <div id="tab-permissions" class="tab-pane">
               <g:render template="/templates/admin/permissions" contextPath="${pluginContextPath}" model="[parent:user]" />
            </div>
            <div id="tab-roles" class="tab-pane">
               <g:render template="/templates/admin/roles" contextPath="${pluginContextPath}" model="[parent:user]" />
            </div>
            <div id="tab-groups" class="tab-pane">
               <g:render template="/templates/admin/groups" contextPath="${pluginContextPath}" model="[parent:user]" />
            </div>
            <div id="tab-logins" class="tab-pane">
               <g:render template="/templates/admin/logins" contextPath="${pluginContextPath}" model="[parent:user]" />
            </div>
         </div>
      </div>
   </div>
</body>