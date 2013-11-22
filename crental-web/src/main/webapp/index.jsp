<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:layout-render name="/layout.jsp" titlekey="index.title">
    <s:layout-component name="body">

        <div class="row">
            <h3><f:message key="guidelines"/></h3>
        </div>
        <div class="row">
            <ul>
                <li>
                    <f:message key="guidelines.employee"/>
                </li>
                <li>
                    <f:message key="guidelines.car"/>
                </li>
                <li>
                    <f:message key="guidelines.request"/>
                </li>
                <li>
                    <f:message key="guidelines.rent"/>
                </li>
            </ul>
        </div>

    </s:layout-component>
</s:layout-render>
