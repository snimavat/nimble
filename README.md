Nimble grails plugin
--------------------
Nimble is a Grails plugin and provides kickstart authentication and authorization functionality to any Grails application.
Nimble provides beautiful UI and ready to use functionality for login, user registration, password reset, Users, Roles, 
Groups and permission management. Nimble provides administration portal from where all of the above stuff can be managed. 
Its possible to extends nimble administration portal to add other administration functionality for your application.

Nimble is based on Apache shiro security framework

## Features
- Out of the box support for authentication and authorization
- Login
- Registration
- Forgot password
- UI for managing Administrators
- UI for managing users
- UI for managing user groups
- UI for managing roles
- UI for assigning roles and permissions to users and groups 


**This is a fork of [snimavat/nimble](https://github.com/snimavat/nimble)**

Changes in this fork are:

* Added configuration for the administration layout header title. Use nimble.admin.header.title to set the header
* Do not expose the user id in activation emails. Set nimble.exposeUserId = false to remove id
