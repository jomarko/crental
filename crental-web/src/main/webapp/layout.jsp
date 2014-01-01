<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:layout-definition>
<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
  <head>
    <title><f:message key="${titlekey}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" media="screen">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.min.css" media="screen">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/crental.css" media="screen">
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/crental.lib.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/crental.${pageContext.request.locale}.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/crental.js"></script>
    <s:layout-component name="header"/>
  </head>
  <body>
    <div class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <s:link class="navbar-brand" href="/index.jsp">Crental</s:link>
          <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
        </div>
        <div class="navbar-collapse collapse" id="navbar-main">
        <shiro:authenticated>
          <ul class="nav navbar-nav">
          <shiro:hasRole name="admin">
            <li>
              <s:link beanclass="cz.muni.fi.pompe.crental.web.EmployeeActionBean"><f:message key="navigation.employees"/></s:link>
            </li>
            <li>
              <s:link beanclass="cz.muni.fi.pompe.crental.web.CarActionBean"><f:message key="navigation.car"/></s:link>
            </li>
           </shiro:hasRole>
            <li>
              <s:link beanclass="cz.muni.fi.pompe.crental.web.RequestActionBean"><f:message key="navigation.request"/></s:link>
            </li>
            <li>
            <s:link beanclass="cz.muni.fi.pompe.crental.web.RentActionBean"><f:message key="navigation.rent"/></s:link>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
              <li class="label-success">
            
            <shiro:principal/>
            
            </li>
            <li>
            <s:link event="logout" beanclass="cz.muni.fi.pompe.crental.web.AuthActionBean"><f:message key="navigation.logout"/></s:link>
            </li>
          </ul>
        </shiro:authenticated>
        <shiro:notAuthenticated>
          <ul class="nav navbar-right">
            <li>
            <s:link event="login" beanclass="cz.muni.fi.pompe.crental.web.AuthActionBean"><f:message key="navigation.login"/></s:link>
            </li>
          </ul>  
        </shiro:notAuthenticated>
        </div>
      </div>
    </div>

    <div class="container">

      <div class="page-header" id="banner">
<!--        <div class="row">
            <div class="col-lg-12">
                <h1><f:message key="${titlekey}"/></h1>
            </div>
        </div>-->
      </div>



      <div class="bs-docs-section">

        <div class="row">
            <s:messages/>
            <s:layout-component name="body"/>
        </div>

      </div>


      <footer>
        <div class="row">
          <div class="col-lg-12">
              <p></p>
<!--            <ul class="list-unstyled">
              <li class="pull-right"><a href="#top">Back to top</a></li>
              <li><a href="https://github.com/pompep/crental">GitHub</a></li>
              <li><a href="../help/#api">API</a></li>
            </ul>

            <p>Made by <a href="http://thomaspark.me">Thomas Park</a>. Contact him at <a href="mailto:hello@thomaspark.me">hello@thomaspark.me</a>.</p>
            <p>Code licensed under the <a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License v2.0</a>.</p>
            <p>Based on <a href="http://getbootstrap.com">Bootstrap</a>. Icons from <a href="http://fortawesome.github.io/Font-Awesome/">Font Awesome</a>. Web fonts from <a href="http://www.google.com/webfonts">Google</a>. Favicon by <a href="https://twitter.com/geraldhiller">Gerald Hiller</a>.</p>-->

          </div>
        </div>

      </footer>


    </div>
  </body>
</html>
</s:layout-definition>