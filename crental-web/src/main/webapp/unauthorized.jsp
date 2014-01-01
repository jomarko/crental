<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:layout-render name="/layout.jsp" titlekey="unauthorized">
    <s:layout-component name="body">

        <div class="row">
            <h3>
            <f:message key="unauthorized"/>
            </h3>
        </div>
    </s:layout-component>
</s:layout-render>
